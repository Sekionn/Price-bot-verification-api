package com.juuls_trinkets.price_bot_verification_api.models;

import java.util.UUID;
import jakarta.persistence.Id;

public class SimpleShop {
    @Id
    public UUID id;
    public String name;
    public String owner;
    public boolean inUse;
    public String email;

    public SimpleShop(UUID id, String name, String owner, boolean inUse, String email){
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.inUse = inUse;
        this.email = email;
    }

    public SimpleShop(Shop shop){
        this.id = shop.Id;
        this.name = shop.Name;
        this.owner = shop.Owner;
        this.inUse = shop.InUse;
        this.email = shop.Email;
    }
}
