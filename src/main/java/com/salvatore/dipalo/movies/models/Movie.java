package com.salvatore.dipalo.movies.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "movies")
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate releaseDate;

    @OneToMany(mappedBy = "movie", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @MapKey(name = "localizedId.locale")
    private Map<String, LocalizedMovie> localizations = new HashMap<>();

    public String getTitle(String locale) {
        return localizations.get(locale).getTitle();
    }

    public String getDescription(String locale) {
        return localizations.get(locale).getDescription();
    }

}
