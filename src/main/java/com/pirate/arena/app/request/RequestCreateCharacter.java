package com.pirate.arena.app.request;

import java.util.List;

public record RequestCreateCharacter(String id, String name, String description, List<String> images) {
}
