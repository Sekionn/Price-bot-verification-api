package com.juuls_trinkets.price_bot_verification_api.models;

import java.util.UUID;

public class ShopCreationDTO {
    public String name;
    public String owner;
    public String email;


    public Shop createShop(String salt, String verificationId){
        return new Shop(UUID.randomUUID(), name, owner, salt, verificationId, false, email);
    }
}
