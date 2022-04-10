package com.salvatore.dipalo.movies.model.db;

import com.salvatore.dipalo.movies.util.LocalizedId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "localized_movies")
@Getter
@Setter
@NoArgsConstructor
public class LocalizedMovie {

    @EmbeddedId
    private LocalizedId localizedId;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    private Movie movie;

    private String title;

    private String description;

}
