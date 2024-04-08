package com.pirate.arena.app.dynamoDB;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.gson.Gson;
import com.pirate.arena.app.exceptions.BadRequestException;
import com.pirate.arena.app.models.Character;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceQueries implements IServiceQueries {

    private final ServiceDynamoDB serviceDynamoDB;

    @Override
    public Optional<Item> getUserByEmail(String email) {
        return Optional.ofNullable(serviceDynamoDB.getItemByKey("users", "email", email));
    }

    @Override
    public Optional<List<Item>> getUserByUsername(String username) {
        return Optional.ofNullable(serviceDynamoDB.getItemByIndex("users", "username", username, "username-index"));
    }

    @Override
    public Optional<Item> getCharacterById(String id) {
        return Optional.ofNullable(serviceDynamoDB.getItemByKey("roster", "id", id));
    }

    @Override
    public List<Character> getAllCharacters() {
        DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder()
                .withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement("roster")).build();
        DynamoDBMapper mapper = new DynamoDBMapper(serviceDynamoDB.getClient(), mapperConfig);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Character> list = mapper.scan(Character.class, scanExpression);
        return list;
    }

    //roster
    @Override
    public void addCharacter(Item item) {
        serviceDynamoDB.putItem("roster", item);
    }


}
