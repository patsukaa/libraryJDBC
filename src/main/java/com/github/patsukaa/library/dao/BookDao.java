package com.github.patsukaa.library.dao;

import com.github.patsukaa.library.entity.Book;

import java.util.List;
import java.util.Optional;


public interface BookDao {

    Optional<Book> findById(int id);

    List<Book> findAll();

    Book create(String title, String author, String genre);

    int updateTitle(int id, String title);

    int deleteById(int id);



}
