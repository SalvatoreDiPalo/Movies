package com.salvatore.dipalo.movies.repository;

import com.salvatore.dipalo.movies.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
