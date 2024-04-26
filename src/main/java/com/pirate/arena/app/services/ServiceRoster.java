package com.pirate.arena.app.services;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.pirate.arena.app.dynamoDB.ServiceQueries;
import com.pirate.arena.app.exceptions.BadRequestException;
import com.pirate.arena.app.models.Character;
import com.pirate.arena.app.request.RequestCreateCharacter;
import com.pirate.arena.app.request.RequestGetCharacterById;
import com.pirate.arena.app.request.RequestGetRoster;
import com.pirate.arena.app.request.RequestGetTeam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceRoster extends ServiceValidateRequest implements IServiceRoster {
    private final ServiceQueries serviceQueries;

    @Override
    public String createCharacter(RequestCreateCharacter requestCreateCharacter) {
        validateInputs(Optional.ofNullable(requestCreateCharacter));
        if (serviceQueries.getCharacterById(requestCreateCharacter.id()).isPresent())
            throw new BadRequestException("[Character roster] character already exists. ["
                    .concat(requestCreateCharacter.id().concat("]")));
        Item character = new Item()
                .withPrimaryKey("id", requestCreateCharacter.id())
                .withString("name", requestCreateCharacter.name())
                .withString("description", requestCreateCharacter.description())
                .withList("images", new ArrayList<>(requestCreateCharacter.images()))
                .withString("slot", String.valueOf(serviceQueries.getAllCharacters().size()))
                .withString("creationDate", LocalDateTime.now().toString())
                .withBoolean("isUnlocked", false);
        serviceQueries.addCharacter(character);
        return "success";
    }

    @Override
    public Character getCharacterByID(RequestGetCharacterById request) {
        validateInputs(Optional.ofNullable(request));
        Optional<Item> character = serviceQueries.getCharacterById(request.id());
        if (character.isEmpty())
            throw new BadRequestException("[Character roster] character not found. ["
                    .concat(request.id().concat("]")));
        return Character.builder()
                .id((String) character.get().get("id"))
                .name((String) character.get().get("name"))
                .description((String) character.get().get("description"))
                .images((List<String>) character.get().get("images"))
                .slot((String) character.get().get("slot"))
                .build();
    }

    @Override
    public List<Character> getCharacterTeam(RequestGetTeam team) {
        List<Character> userTeam = new ArrayList<>();
        if (team.list() == null || team.list().isEmpty() || team.list().size() < 3)
            throw new BadRequestException("[Character roster] team must have at least 3 players");
        team.list().forEach(item -> userTeam.add(getCharacterByID(RequestGetCharacterById.builder()
                .id(item)
                .build())));
        return userTeam;
    }

    @Override
    public Page<Character> getAllCharactersByPage(RequestGetRoster request) {
        validateInputs(Optional.ofNullable(request));
        List<Character> list = serviceQueries.getAllCharacters();
        return new PageImpl<>(list, PageRequest.of(request.page(), request.size()), list.size());
    }

    @Override
    public String convertToJson(Optional<?> object) {
        Gson gson = new Gson();
        return gson.toJson(object.get());
    }

    @Override
    public String validateImage(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            throw new BadRequestException("[Error creating player] Avatar not valid [".concat(url).concat("]"));
        return url;
    }

}
