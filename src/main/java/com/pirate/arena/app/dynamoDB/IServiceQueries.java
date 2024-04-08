package com.pirate.arena.app.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.pirate.arena.app.models.Character;


import java.util.List;
import java.util.Optional;

public interface IServiceQueries {

    //Users
    Optional<Item> getUserByEmail(String email);

    Optional<List<Item>> getUserByUsername(String username);

    //Codes
    Optional<Item> getCharacterById(String id);

    List<Character> getAllCharacters();

    void addCharacter(Item item);
}
