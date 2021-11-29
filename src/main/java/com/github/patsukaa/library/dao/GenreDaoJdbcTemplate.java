package com.github.patsukaa.library.dao;

import com.github.patsukaa.library.entity.Genre;
import com.github.patsukaa.library.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenreDaoJdbcTemplate implements GenreDao {

    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Optional<Genre> findByGenre(String genre) {
        String query = "Select id, genre from genres where genre = '" + genre + "'";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("genre", genre);

        List<Genre> res = jdbcTemplate.query(query, sqlParameterSource, new GenreMapper());

        return res
                .stream()
                .findFirst();
    }


    public boolean isExistGenre(String genre) {
        return findByGenre(genre)
                .isPresent();
    }

}


