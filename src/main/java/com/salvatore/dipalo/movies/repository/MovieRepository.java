package com.salvatore.dipalo.movies.repository;

import com.salvatore.dipalo.movies.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
