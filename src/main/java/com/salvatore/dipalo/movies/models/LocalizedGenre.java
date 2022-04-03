package com.salvatore.dipalo.movies.models;

import com.salvatore.dipalo.movies.LocalizedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "localized_genres")
@Getter
@Setter
@NoArgsConstructor
public class LocalizedGenre {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    private Genre genre;

    private String name;

}
