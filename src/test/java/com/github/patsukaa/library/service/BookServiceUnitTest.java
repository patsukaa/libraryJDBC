package com.github.patsukaa.library.service;

import com.github.patsukaa.library.dao.AuthorDao;
import com.github.patsukaa.library.dao.BookDao;
import com.github.patsukaa.library.dao.GenreDao;
import com.github.patsukaa.library.entity.Author;
import com.github.patsukaa.library.entity.Book;
import com.github.patsukaa.library.entity.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        BookServiceImpl.class,
        BookDao.class,
        AuthorDao.class,
        GenreDao.class
})
public class BookServiceUnitTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookDao bookDao;
    @MockBean
    private AuthorDao authorDao;
    @MockBean
    private GenreDao genreDao;

    @Test
    public void createNewBook() {

        Book expected = Book.builder()
                .id(4)
                .title("title1")
                .author(Author.builder()
                        .id(1)
                        .author("author1")
                        .build())
                .genre(Genre.builder()
                        .id(1)
                        .genre("genre1")
                        .build())
                .build();

        when(genreDao.isExistGenre(anyString())).thenReturn(true);
        when(authorDao.isExistAuthor(anyString())).thenReturn(true);
        when(bookDao.create(anyString(), anyString(), anyString())).thenReturn(expected);

        assertThat(bookService.createdBook("title1", "author1", "genre1"))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);

        verify(bookDao, times(1)).create(anyString(), anyString(), anyString());
    }

    @Test
    public void shouldNotCreateNewBookIfGenreDoesNotExist() {

        when(genreDao.isExistGenre(anyString())).thenReturn(false);
        when(authorDao.isExistAuthor(anyString())).thenReturn(true);

        verify(bookDao, times(0)).create(anyString(), anyString(), anyString());
    }

    @Test
    public void shouldNotCreateNewBookIfAuthorDoesNotExist() {

        when(genreDao.isExistGenre(anyString())).thenReturn(true);
        when(authorDao.isExistAuthor(anyString())).thenReturn(false);

        verify(bookDao, times(0)).create(anyString(), anyString(), anyString());
    }
}
