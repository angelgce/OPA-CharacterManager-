package com.pirate.arena.app.functions;

import com.pirate.arena.app.request.RequestCreateCharacter;
import com.pirate.arena.app.request.RequestGetCharacterById;
import com.pirate.arena.app.request.RequestGetRoster;
import com.pirate.arena.app.request.RequestGetTeam;
import com.pirate.arena.app.services.ServiceRoster;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.pirate.arena.app.models.Character;

@Configuration
@RequiredArgsConstructor
public class LambdaFunction {
    private final ServiceRoster serviceCharacter;

    @Bean
    public Function<RequestCreateCharacter, ResponseEntity<Map<String, String>>> createCharacter() {
        return value -> ResponseEntity.ok()
                .body(Collections.singletonMap("data", serviceCharacter.createCharacter(value)));
    }

    @Bean
    public Function<RequestGetCharacterById, ResponseEntity<Map<String, Character>>> getCharacterById() {
        return value -> ResponseEntity.ok()
                .body(Collections.singletonMap("data", serviceCharacter.getCharacterByID(value)));
    }

    @Bean
    public Function<RequestGetTeam, ResponseEntity<Map<String, List<Character>>>> getTeam() {
        return value -> ResponseEntity.ok()
                .body(Collections.singletonMap("data", serviceCharacter.getCharacterTeam(value)));
    }

    @Bean
    public Function<RequestGetRoster, ResponseEntity<Map<String, Page<Character>>>> getRoster() {
        return value -> ResponseEntity.ok()
                .body(Collections.singletonMap("data", serviceCharacter.getAllCharactersByPage(value)));
    }
}
