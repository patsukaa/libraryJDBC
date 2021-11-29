package com.github.patsukaa.library.service;

import com.github.patsukaa.library.dao.AuthorDao;
import com.github.patsukaa.library.dao.BookDao;
import com.github.patsukaa.library.dao.GenreDao;
import com.github.patsukaa.library.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;


    public Book createdBook(String title, String author, String genre) {

        if (genreDao.isExistGenre(genre) && authorDao.isExistAuthor(author)) {
            Book createdBook = bookDao.create(title, author, genre);
            System.out.println("Created book with id=" + createdBook.getId());
            return createdBook;
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public int deleteById(int bookId) {
        return bookDao.deleteById(bookId);
    }

    @Override
    public int updateTitle(int bookId, String title) {
        return bookDao.updateTitle(bookId, title);
    }
}
