package com.juuls_trinkets.price_bot_verification_api.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.juuls_trinkets.price_bot_verification_api.models.Shop;

public class ShopMapper implements RowMapper<Shop> {

    @Override
    public Shop mapRow(ResultSet result, int arg1) throws SQLException {
        Shop Shop = new Shop(
            result.getObject("id", java.util.UUID.class),
            result.getString("name"),
            result.getString("owner"),
            result.getString("salt"),
            result.getString("verification_id"),
            result.getBoolean("in_use"),
            result.getString("email")
        );

        return Shop;
    }
}