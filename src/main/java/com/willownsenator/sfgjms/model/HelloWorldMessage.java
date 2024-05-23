package com.willownsenator.sfgjms.model;


import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public record HelloWorldMessage(UUID id, String message) implements Serializable {
    @Serial
    private static final long serialVersionUID = 7387545769438182191L;
}
