package com.juuls_trinkets.price_bot_verification_api.models;

import java.util.UUID;

public class VerificationInformation {
    public String email;
    public UUID key;

    public VerificationInformation(String email, UUID key){
        this.email = email;
        this.key = key;
    }
}
