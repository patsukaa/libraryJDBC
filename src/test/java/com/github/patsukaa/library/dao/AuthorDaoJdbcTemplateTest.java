package com.github.patsukaa.library.dao;

import com.github.patsukaa.library.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthorDaoJdbcTemplateTest {

    @Autowired
    private AuthorDaoJdbcTemplate authorDaoJdbcTemplate;

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void findAuthorByAuthor() {

        Optional<Author> expected = Optional.of(
                Author.builder()
                        .id(1)
                        .author("author1")
                        .build());


        assertThat(authorDaoJdbcTemplate.findByAuthor("author1"))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
