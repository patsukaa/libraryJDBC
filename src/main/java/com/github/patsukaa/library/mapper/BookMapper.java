package com.github.patsukaa.library.mapper;

import com.github.patsukaa.library.entity.Author;
import com.github.patsukaa.library.entity.Book;
import com.github.patsukaa.library.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Book.builder()
                .id(resultSet.getInt("bookId"))
                .title(resultSet.getString("title"))
                .genre(Genre.builder()
                        .id(resultSet.getInt("genreId"))
                        .genre(resultSet.getString("genre"))
                        .build())
                .author(Author.builder()
                        .id(resultSet.getInt("authorId"))
                        .author(resultSet.getString("author"))
                        .build())
                .build();
    }

}
