package dev.arhimedes.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class ApiResponse<T>{

    private String message;
    private String urlPath;
    private LocalDateTime date;
    private T body;

}
