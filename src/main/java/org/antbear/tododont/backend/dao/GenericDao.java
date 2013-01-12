package org.antbear.tododont.backend.dao;

import com.google.common.collect.Maps;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

abstract class GenericDao<T, PK> extends AbstractDao {

    @NotNull
    protected abstract String getTableName();

    @NotNull
    protected abstract RowMapper<T> newRowMapper();

    @NotNull
    @Transactional
    public List<T> findAll() {
        return getJdbcTemplate().query("SELECT * FROM " + getTableName(),
                new MapSqlParameterSource(),
                newRowMapper());
    }

    @NotNull
    @Transactional
    public T findById(@NotNull final PK pk) {
        return getJdbcTemplate().queryForObject("SELECT * FROM " + getTableName() + " WHERE id = :id",
                new MapSqlParameterSource("id", pk),
                newRowMapper());
    }

    @Transactional
    public void delete(@NotNull final PK pk) {
        getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE id = :id",
                new MapSqlParameterSource("id", pk));
    }

    @Transactional
    public List<T> findAllPaged(@Min(value = 1) final int offset, @Min(value = 1) final int limit) {
        final Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("limit", limit);
        parameters.put("offset", offset);

        return getJdbcTemplate().query("SELECT * FROM " + getTableName() + " LIMIT :limit OFFSET :offset",
                new MapSqlParameterSource(parameters),
                newRowMapper());
    }
}
