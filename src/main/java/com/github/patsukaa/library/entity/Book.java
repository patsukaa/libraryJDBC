package com.github.patsukaa.library.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

    private int id;
    private String title;
    private Author author;
    private Genre genre;
}
