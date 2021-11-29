package com.github.patsukaa.library.dao;

import com.github.patsukaa.library.entity.Author;

import java.util.Optional;

public interface AuthorDao {

    Optional<Author> findByAuthor(String author);

    boolean isExistAuthor(String author);
}
