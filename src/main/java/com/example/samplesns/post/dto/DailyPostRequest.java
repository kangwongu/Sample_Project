package com.example.samplesns.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class DailyPostRequest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate firstDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lastDate;

}
