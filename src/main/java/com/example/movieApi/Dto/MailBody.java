package com.example.movieApi.Dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
