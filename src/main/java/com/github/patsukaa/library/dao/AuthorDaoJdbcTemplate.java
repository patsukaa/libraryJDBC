package com.github.patsukaa.library.dao;

import com.github.patsukaa.library.entity.Author;
import com.github.patsukaa.library.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbcTemplate implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Optional<Author> findByAuthor(String author) {
        String query = "Select id, author from authors where author = '" + author + "'";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("author", author);

        List<Author> res = jdbcTemplate.query(query, sqlParameterSource, new AuthorMapper());

        return res
                .stream()
                .findFirst();
    }

    @Override
    public boolean isExistAuthor(String author) {
        return findByAuthor((author))
                .isPresent();
    }

}
