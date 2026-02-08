package com.juuls_trinkets.price_bot_verification_api.models;

import java.util.UUID;

public class VerificationInformation {
    public UUID id;
    public UUID key;

    public VerificationInformation(UUID id, UUID key){
        this.id = id;
        this.key = key;
    }
}
