package com.juuls_trinkets.price_bot_verification_api.Services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.juuls_trinkets.price_bot_verification_api.Repository.ShopDaoImpl;
import com.juuls_trinkets.price_bot_verification_api.models.Shop;
import com.juuls_trinkets.price_bot_verification_api.models.ShopCreationDTO;
import com.juuls_trinkets.price_bot_verification_api.models.SimpleShop;
import com.juuls_trinkets.price_bot_verification_api.models.UpdateInUse;
import com.juuls_trinkets.price_bot_verification_api.models.VerificationInformation;

@Service
public class ShopService {

    @Autowired
    ShopDaoImpl shopRepository;

	public boolean ShopIsVerified(VerificationInformation info) {
		Optional<Shop> optionalShop = shopRepository.findByIdExtended(info.id);
		if (optionalShop.isEmpty()) {
			throw new RuntimeException("No shop was found by this id");
		}
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		Shop shop = optionalShop.get();
		String saltedId = info.key.toString() + shop.Salt;
		
		return passwordEncoder.matches(saltedId, shop.VerificationId);
	}

	public boolean IsShopInUse(UUID id) {
		Optional<SimpleShop> shop = shopRepository.findById(id);
		if (shop.isEmpty()) {
			throw new RuntimeException("No shop was found by this id");
		}

		return shop.get().inUse;
	}

	public VerificationInformation createShop(ShopCreationDTO shopDto) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UUID key = UUID.randomUUID();
		StringKeyGenerator saltGenerator = KeyGenerators.string();
		String salt = saltGenerator.generateKey();
		String saltedId = key.toString() + salt;
		String encodedId = passwordEncoder.encode(saltedId);
		
		Shop shop = shopDto.createShop(salt, encodedId);
		shopRepository.insertShop(shop);

        return new VerificationInformation(shop.Id, key);
	}

	public int updateInUse(UpdateInUse update) {
		
        return shopRepository.updateInUse(update);
	}
}