package com.pirate.arena.app.request;

import lombok.Builder;

@Builder
public record RequestGetCharacterById(String id) {
}
