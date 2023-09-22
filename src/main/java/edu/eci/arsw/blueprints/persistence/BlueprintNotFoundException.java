package edu.eci.arsw.blueprints.persistence;

import org.springframework.stereotype.Component;

@Component
public class BlueprintNotFoundException extends Exception {

    public BlueprintNotFoundException() {
        super();
    }

    public BlueprintNotFoundException(String message) {
        super(message);
    }

    public BlueprintNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}