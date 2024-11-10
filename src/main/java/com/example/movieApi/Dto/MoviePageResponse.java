package com.example.movieApi.Dto;

import java.util.List;

public record MoviePageResponse(
        List<MovieDto> movieDtos,
        Integer pageNumber,
        Integer pageSize,
        long totalElements,
        int totalPages,
        boolean isLast
) {
}
