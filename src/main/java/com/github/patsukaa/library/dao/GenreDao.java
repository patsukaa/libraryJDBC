package com.github.patsukaa.library.dao;

import com.github.patsukaa.library.entity.Genre;

import java.util.Optional;

public interface GenreDao{

    Optional<Genre> findByGenre(String genre);

    boolean isExistGenre(String genre);

}
