package com.github.patsukaa.library.service;

import com.github.patsukaa.library.entity.Book;

import java.util.List;


public interface BookService {

    Book createdBook(String title, String author, String genre);

    List<Book> findAll();

    int deleteById(int bookId);

    int updateTitle(int bookId, String title);
}
