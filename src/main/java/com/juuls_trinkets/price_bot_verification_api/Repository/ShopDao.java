package com.juuls_trinkets.price_bot_verification_api.Repository;

import com.juuls_trinkets.price_bot_verification_api.models.Shop;
import com.juuls_trinkets.price_bot_verification_api.models.SimpleShop;
import com.juuls_trinkets.price_bot_verification_api.models.UpdateInUse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShopDao {

    Optional<SimpleShop> findById(UUID id);

    Optional<Shop> findByIdExtended(UUID id);

    List<Shop> findByEmail(String email);

    int insertShop(Shop shop);

    int updateInUse(UpdateInUse update);
}
