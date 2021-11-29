package com.github.patsukaa.library.commands;

import com.github.patsukaa.library.entity.Book;
import com.github.patsukaa.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {


    private final BookService bookService;

    @ShellMethod(value = "Find", key = {"f"})
    public String findAllBooks() {
        List<Book> allBooks = bookService.findAll();
        return String.format("Total count of book equal %s", allBooks.size());
    }

    @ShellMethod(value = "Delete", key = {"d"})
    public String deleteBook(@ShellOption int bookId) {
        int deleteBook = bookService.deleteById(bookId);

        return String.format("Count deleted books %s", deleteBook);
    }

    @ShellMethod(value = "Update", key = {"u"})
    public String updateTitleBook(@ShellOption int bookId, @ShellOption String title) {
        int updateBook = bookService.updateTitle(bookId, title);

        return String.format("Count updated books %s", updateBook);
    }

    @ShellMethod(value = "Create", key = {"c"})
    public String createdBook(@ShellOption String title, @ShellOption String author, @ShellOption String genre) {
        Book newBook = bookService.createdBook(title, author, genre);
        if (newBook != null)
            return String.format("New book had id %s", newBook.getId());
        else
            return "New book is not created!";
    }


}
