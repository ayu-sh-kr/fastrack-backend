package dev.arhimedes.global.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FastrackServiceException extends RuntimeException {
    public FastrackServiceException(String message) {
        super(message);
    }
}
