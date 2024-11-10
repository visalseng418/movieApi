package com.example.movieApi.controllers;

import com.example.movieApi.Dto.MovieDto;
import com.example.movieApi.Dto.MoviePageResponse;
import com.example.movieApi.Exception.EmptyFileException;
import com.example.movieApi.Utils.AppConstant;
import com.example.movieApi.services.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;

    }

    @PostMapping("/create")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDto) throws IOException {

        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty! Please send another file!");
        }
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }


    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId) {
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMoviesHandler() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }


    @PutMapping("/update/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId, @RequestPart String movieDtoObj, @RequestPart MultipartFile file) throws IOException{
         if(file.isEmpty()) file = null;
         MovieDto movieDto = convertToMovieDto(movieDtoObj);
        return ResponseEntity.ok(movieService.updateMovie(movieId, movieDto, file));
    }


    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    @GetMapping("/allMoviesPage")
    public ResponseEntity<MoviePageResponse> getMovieWithPagination(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumeber,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize
    ) {
        return ResponseEntity.ok(movieService.getAllMovieWithPagination(pageNumeber,pageSize));
    }

    @GetMapping("/allMoviesPageSort")
    public ResponseEntity<MoviePageResponse> getMovieWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumeber,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstant.SORT_DIR, required = false) String sortDir
    ) {
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumeber,pageSize,sortBy,sortDir));
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }





}
