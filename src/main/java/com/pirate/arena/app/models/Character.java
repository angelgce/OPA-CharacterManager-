package com.pirate.arena.app.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@DynamoDBTable(tableName = "roster")
public class Character {
    @DynamoDBHashKey
    String id;
    @DynamoDBAttribute
    String name;
    @DynamoDBAttribute
    String description;

    @DynamoDBAttribute
    String slot;
    @DynamoDBAttribute
    List<String> images;

    public Character() {
    }

    public Character(String id, String name, String description, String slot, List<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slot = slot;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}

