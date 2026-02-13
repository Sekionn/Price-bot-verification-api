package com.juuls_trinkets.price_bot_verification_api.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.juuls_trinkets.price_bot_verification_api.models.SimpleShop;

public class SimpleShopMapper implements RowMapper<SimpleShop> {

    @Override
    public SimpleShop mapRow(ResultSet result, int arg1) throws SQLException {
        SimpleShop Shop = new SimpleShop(
            result.getObject("id", java.util.UUID.class),
            result.getString("name"),
            result.getString("owner"),
            result.getBoolean("in_use"),
            result.getString("email")
        );

        return Shop;
    }
}