
package com.example.nivel20.requests;

public class CreateCharacterRequest {
    private Long userId;
    private String name;
    private String character_type;

    public Long getUserId() {return userId; }
    public String getName() {
        return name;
    }

    public String getCharacter_type() {
        return character_type;
    }
}
