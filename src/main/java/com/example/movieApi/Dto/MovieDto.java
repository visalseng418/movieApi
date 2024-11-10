package com.example.movieApi.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please enter the movie's title.")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Please enter the director's name.")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Please enter the studio's name.")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Please provide movie's poster!")
    private String poster;

    @Column(nullable = false)
    @NotBlank(message = "Please provide movie's poster url!")
    private String posterUrl;


}
