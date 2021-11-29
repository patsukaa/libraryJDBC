package com.github.patsukaa.library.dao;

import com.github.patsukaa.library.entity.Author;
import com.github.patsukaa.library.entity.Book;
import com.github.patsukaa.library.entity.Genre;
import com.github.patsukaa.library.exception.DeletingNonExistRecordException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
public class BookDaoJdbcTemplateTest {

    @Autowired
    private BookDaoJdbcTemplate bookDaoJdbcTemplate;


    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void getByIdTest() {

        Optional<Book> expected = Optional.of(
                Book.builder()
                        .id(1)
                        .title("title1")
                        .author(Author.builder()
                                .id(1)
                                .author("author1")
                                .build())
                        .genre(Genre.builder()
                                .id(1)
                                .genre("genre1")
                                .build())
                        .build()
        );

        assertThat(bookDaoJdbcTemplate.findById(1))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void updateTitleTest() {

        final String updatedName = "newTitle";

        Book book = Book.builder()
                .id(1)
                .title("title2")
                .author(Author.builder()
                        .id(2)
                        .author("author2")
                        .build())
                .genre(Genre.builder()
                        .id(2)
                        .genre("genre2")
                        .build())
                .build();

        int updatedRows = bookDaoJdbcTemplate.updateTitle(book.getId(), updatedName);


        assertThat(updatedRows)
                .isEqualTo(1);

        assertThat(bookDaoJdbcTemplate.findById(book.getId()))
                .isNotEmpty()
                .get()
                .extracting("id", "title")
                .isEqualTo(List.of(1, updatedName));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldDeleteExistedBook() {

        int deletedRows = bookDaoJdbcTemplate.deleteById(1);

        assertThat(deletedRows)
                .isEqualTo(1);

        assertThat(bookDaoJdbcTemplate.findById(1))
                .isEmpty();

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldThrowExceptionIfDeleteBookThatDoesNotExist() {

        assertThatThrownBy(() -> bookDaoJdbcTemplate.deleteById(1))
                .isInstanceOf(DeletingNonExistRecordException.class)
                .hasMessage("Record with id {" + 1 + "} does not exist");

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldReturnEmptyListIfTableIsEmpty() {
        assertThat(bookDaoJdbcTemplate.findAll())
                .isEmpty();

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void findAll() {

        Book book1 = Book.builder()
                .id(1)
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


        Book book2 = Book.builder()
                .id(2)
                .title("title2")
                .author(Author.builder()
                        .id(2)
                        .author("author2")
                        .build())
                .genre(Genre.builder()
                        .id(2)
                        .genre("genre2")
                        .build())
                .build();

        Book book3 = Book.builder()
                .id(3)
                .title("title3")
                .author(Author.builder()
                        .id(3)
                        .author("author3")
                        .build())
                .genre(Genre.builder()
                        .id(3)
                        .genre("genre3")
                        .build())
                .build();

        List<Book> allBooks = bookDaoJdbcTemplate.findAll();
        assertThat(allBooks)
                .hasSize(3)
                .containsExactlyElementsOf(List.of(
                        book1,
                        book2,
                        book3
                ));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldCreateBook() {

        Book createdBook = bookDaoJdbcTemplate.create("newBook", "author1", "genre1");

        assertThat(createdBook)
                .isNotNull()
                .extracting("title")
                .isEqualTo("newBook");
    }

}


