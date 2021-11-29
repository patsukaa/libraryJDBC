package com.github.patsukaa.library.mapper;

import com.github.patsukaa.library.entity.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Author.builder()
                .id(resultSet.getInt("id"))
                .author(resultSet.getString("author"))
                .build();
    }
}
