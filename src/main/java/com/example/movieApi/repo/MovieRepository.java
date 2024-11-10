package com.example.movieApi.repo;

import com.example.movieApi.Dto.MovieDto;
import com.example.movieApi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
