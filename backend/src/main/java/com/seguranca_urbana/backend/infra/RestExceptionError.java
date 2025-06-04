package com.seguranca_urbana.backend.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RestExceptionError {

    private int status;           // Código HTTP (ex: 400, 404)
    private String error;         // Nome do erro (ex: "Bad Request")
    private String message;       // Mensagem detalhada
    private String path;          // Endpoint que causou o erro
    private LocalDateTime timestamp; // Momento da exceção
}