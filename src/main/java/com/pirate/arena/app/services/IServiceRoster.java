package com.pirate.arena.app.services;


import java.util.List;
import java.util.Optional;

import com.pirate.arena.app.models.Character;
import com.pirate.arena.app.request.RequestCreateCharacter;
import com.pirate.arena.app.request.RequestGetCharacterById;
import com.pirate.arena.app.request.RequestGetRoster;
import com.pirate.arena.app.request.RequestGetTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServiceRoster {

    String createCharacter(RequestCreateCharacter requestCreateCharacter);


    Character getCharacterByID(RequestGetCharacterById request);

    List<Character> getCharacterTeam(RequestGetTeam team);

    Page<Character> getAllCharactersByPage(RequestGetRoster requestGetRoster);

    String convertToJson(Optional<?> object);

    String validateImage(String url);
}
