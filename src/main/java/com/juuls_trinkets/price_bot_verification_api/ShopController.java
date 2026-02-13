package com.juuls_trinkets.price_bot_verification_api;

import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import com.juuls_trinkets.price_bot_verification_api.Services.ShopService;
import com.juuls_trinkets.price_bot_verification_api.models.ShopCreationDTO;
import com.juuls_trinkets.price_bot_verification_api.models.UpdateInUse;
import com.juuls_trinkets.price_bot_verification_api.models.VerificationInformation;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    ShopService ShopService;

    @PostMapping("/verified")
    UUID ShopIsVerified(@RequestBody VerificationInformation info){

        return ShopService.ShopIsVerified(info);
    }

    @GetMapping("/active/{id}")
    boolean isShopInUse(@PathVariable UUID id){

        return ShopService.IsShopInUse(id);
    }

    @PostMapping
    VerificationInformation createShop(@RequestBody ShopCreationDTO shopDto){
        return ShopService.createShop(shopDto);
    }

    @PatchMapping("/active")
    int createShop(@RequestBody UpdateInUse update){
        return ShopService.updateInUse(update);
    }
}