package com.juuls_trinkets.price_bot_verification_api.Services;

import java.util.List;
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

	public UUID ShopIsVerified(VerificationInformation info) {
		List<Shop> shops = shopRepository.findByEmail(info.email);
		if (shops.isEmpty()) {
			throw new RuntimeException("No shop was found by this email");
		}
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		for (Shop shop : shops) {
			String saltedId = info.key.toString() + shop.Salt;
			if (passwordEncoder.matches(saltedId, shop.VerificationId)) {
				return shop.Id;
			}
		}
		
		throw new RuntimeException("No shop was found by this email");
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

        return new VerificationInformation(shop.Email, key);
	}

	public int updateInUse(UpdateInUse update) {
		
        return shopRepository.updateInUse(update);
	}
}