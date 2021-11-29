package com.github.patsukaa.library.dao;

import com.github.patsukaa.library.entity.Book;
import com.github.patsukaa.library.exception.DeletingNonExistRecordException;
import com.github.patsukaa.library.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookDaoJdbcTemplate implements BookDao {


    private final NamedParameterJdbcOperations jdbcTemplate;


    @Override
    public Optional<Book> findById(int id) {
        String sqlQuery = "Select b.id as bookId, b.title, a.id as authorId, a.author, g.id as genreId, g.genre from books b " +
                "INNER JOIN authors a ON b.authorId = a.id " +
                "INNER JOIN genres g ON b.genreId = g.id " +
                "where b.id =  :id";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

        List<Book> result = jdbcTemplate.query(sqlQuery, sqlParameterSource, new BookMapper());
        return result
                .stream()
                .findFirst();
    }


    @Override
    public List<Book> findAll() {
        String selectQuery = "Select b.id as bookId, b.title, a.id as authorId, a.author, g.id as genreId, g.genre from books b " +
                "INNER JOIN authors a ON b.authorId = a.id " +
                "INNER JOIN genres g ON b.genreId = g.id";

        return jdbcTemplate.query(selectQuery, new BookMapper());
    }

    @Override
    public Book create(String title, String author, String genre) {
        String insert = "INSERT INTO books(title, authorId, genreId) VALUES (\n" +
                ":title, " +
                "select id from authors where author = :author, " +
                "select id from genres where genre = :genre)";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                Map.of(
                        "title", title,
                        "author", author,
                        "genre", genre
                )
        );

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insert, sqlParameterSource, generatedKeyHolder);

        return findById(Objects.requireNonNull(generatedKeyHolder.getKey()).intValue())
                .orElse(null);
    }

    @Override
    public int updateTitle(int id, String title) {

        String updateQuery = "update books set title = :title where id = :id";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                Map.of(
                        "id", id,
                        "title", title
                )
        );

        return jdbcTemplate.update(updateQuery, sqlParameterSource);
    }

    @Override
    public int deleteById(int id) {

        if (!doesRecordExist(id)) {
            throw new DeletingNonExistRecordException("Record with id {" + id + "} does not exist");
        }

        String deleteQuery = "delete from books where id = :id";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

        return jdbcTemplate.update(deleteQuery, sqlParameterSource);
    }


    private boolean doesRecordExist(int id) {
        String selectById = "select count(*) from books where id = :id";
        SqlParameterSource selectByIdParameterSource = new MapSqlParameterSource("id", id);
        return jdbcTemplate
                .queryForObject(selectById, selectByIdParameterSource, Integer.class) != 0;
    }


}
