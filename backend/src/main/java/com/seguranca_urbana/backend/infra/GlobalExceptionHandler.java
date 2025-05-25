package com.seguranca_urbana.backend.infra;

public class GlobalExceptionHandler {


    /*
    * EXEMPLO:
    *
    *  @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<RestExceptionError> handleDoctorNotFoundException(DoctorNotFoundException exception) {
        RestExceptionError threatError = new RestExceptionError(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatError);
    }
    *
    * */
}
