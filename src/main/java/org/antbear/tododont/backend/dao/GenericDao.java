package org.antbear.tododont.backend.dao;

import com.google.common.collect.ImmutableMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class GenericDao<T extends DomainObject<PK>, PK> extends AbstractDao {

    @NotNull
    private final String pkColumnName;

    @NotNull
    private final String tableName;

    protected GenericDao(@NotNull final String pkColumnName, @NotNull final String tableName) {
        this.pkColumnName = pkColumnName;
        this.tableName = tableName;
    }

    @NotNull
    protected String getPKColumnName() {
        return this.pkColumnName;
    }

    @NotNull
    protected String getTableName() {
        return this.tableName;
    }

    @NotNull
    protected abstract RowMapper<T> newRowMapper();

    @NotNull
    @Transactional
    public T find(@NotNull final PK pk) {
        return getJdbcTemplate().queryForObject("SELECT * FROM " + getTableName()
                + " WHERE " + getPKColumnName() + " = :pk",
                ImmutableMap.of("pk", pk),
                newRowMapper());
    }

    @NotNull
    @Transactional
    public List<T> findAll() {
        return getJdbcTemplate().query("SELECT * FROM " + getTableName(),
                new MapSqlParameterSource(),
                newRowMapper());
    }

    @Transactional
    public void delete(@NotNull final PK pk) {
        getJdbcTemplate().update("DELETE FROM " + getTableName() + " WHERE " + getPKColumnName() + " = :pk",
                new MapSqlParameterSource("pk", pk));
    }

    @Transactional
    public List<T> findAllPaged(@Min(value = 1) final int offset, @Min(value = 1) final int limit) {
        return getJdbcTemplate().query("SELECT * FROM " + getTableName() + " LIMIT :limit OFFSET :offset",
                ImmutableMap.of("limit", limit, "offset", offset),
                newRowMapper());
    }
}
