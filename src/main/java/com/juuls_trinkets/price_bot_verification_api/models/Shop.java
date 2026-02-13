package com.juuls_trinkets.price_bot_verification_api.models;

import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
public class Shop {
    @Id
    public UUID Id;
    public String Name;
    public String Owner;
    public String Salt;
    public String VerificationId;
    public boolean InUse;
    public String Email;

    public Shop(UUID id, String name, String owner, String verificationId, boolean inUse, String email)
    {
        Id = id;
        Name = name;
        Owner = owner;
        VerificationId = verificationId;
        InUse = inUse;
        Email = email;
    }

    public Shop(UUID id, String name, String owner, String salt, String verificationId, boolean inUse, String email)
    {
        Id = id;
        Name = name;
        Owner = owner;
        Salt = salt;
        VerificationId = verificationId;
        InUse = inUse;
        Email = email;
    }
}