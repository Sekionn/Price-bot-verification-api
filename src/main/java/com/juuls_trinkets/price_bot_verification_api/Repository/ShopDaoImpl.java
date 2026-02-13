package com.juuls_trinkets.price_bot_verification_api.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.juuls_trinkets.price_bot_verification_api.Mappers.ShopMapper;
import com.juuls_trinkets.price_bot_verification_api.Mappers.SimpleShopMapper;
import com.juuls_trinkets.price_bot_verification_api.models.Shop;
import com.juuls_trinkets.price_bot_verification_api.models.SimpleShop;
import com.juuls_trinkets.price_bot_verification_api.models.UpdateInUse;

@Repository
public class ShopDaoImpl implements ShopDao{

    public ShopDaoImpl(NamedParameterJdbcTemplate template) {  
        this.template = template;  
    }  
    NamedParameterJdbcTemplate template; 

    @Override
    public Optional<SimpleShop> findById(UUID id) {
        SimpleShopMapper mapper = new SimpleShopMapper();
        String sql = "SELECT id, name, owner, in_use FROM users WHERE id = :id";
        try {
            SimpleShop shop = template.queryForObject(
                sql,
                Collections.singletonMap("id", id),
                (rs, rowNum) -> mapper.mapRow(rs, 1)
            );
            return Optional.of(shop);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        }
    }

    @Override
    public Optional<Shop> findByIdExtended(UUID id) {
        ShopMapper mapper = new ShopMapper();
        String sql = "SELECT * FROM users WHERE id = :id";
        try {
            Shop shop = template.queryForObject(
                sql,
                Collections.singletonMap("id", id),
                (rs, rowNum) -> mapper.mapRow(rs, 1)
            );
            return Optional.of(shop);
        } catch (EmptyResultDataAccessException e) {
            throw e;
        }
    }

    @Override
    public List<Shop> findByEmail(String email) {

        return template.query("select * from users", new ShopMapper());

        // ShopMapper mapper = new ShopMapper();
        // String sql = "SELECT * FROM users WHERE email = :email";
        // try {
        //     Shop shop = template.query(
        //         sql,
        //         Collections.singletonMap("email", email),
        //         (rs, rowNum) -> mapper.mapRow(rs, 1)
        //     );
        //     return Optional.of(shop);
        // } catch (EmptyResultDataAccessException e) {
        //     throw e;
        // }
    }

	@Override
	public int insertShop(Shop shop) {
		final String sql = "insert into users(id, name, owner, salt, verification_id, in_use, email) values(:Id,:Name,:Owner,:Salt,:VerificationId,:InUse,:email)";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("Id", shop.Id)
            .addValue("Name", shop.Name)
            .addValue("Owner", shop.Owner)
            .addValue("Salt", shop.Salt)
            .addValue("VerificationId", shop.VerificationId)
            .addValue("InUse", shop.InUse)
            .addValue("email", shop.Email);
        return template.update(sql,param, holder);
	}

	@Override
	public int updateInUse(UpdateInUse update) {
		final String sql = "update users set in_use=:in_use where id=:id";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("id", update.id)
            .addValue("in_use", update.inUse);

        return template.update(sql,param, holder);
	}
}