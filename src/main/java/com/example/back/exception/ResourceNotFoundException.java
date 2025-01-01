package com.example.back.exception;

/**
 * Lancée lorsqu'une ressource n'est pas trouvée dans la base de
 * données
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
