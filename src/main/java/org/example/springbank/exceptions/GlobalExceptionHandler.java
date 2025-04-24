package org.example.springbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(RuntimeException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleClientNotFound(ClientNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

//
//import org.springframework.http.HttpStatus;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ClientNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleClientNotFound(ClientNotFoundException ex, Model model) {
//        model.addAttribute("errorMessage", ex.getMessage());
//        return "error/404";
//    }
//
//    @ExceptionHandler(AccountNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleAccountNotFound(AccountNotFoundException ex, Model model) {
//        model.addAttribute("errorMessage", ex.getMessage());
//        return "error/404";
//    }
//
//    @ExceptionHandler(TransactionNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleTransactionNotFound(TransactionNotFoundException ex, Model model) {
//        model.addAttribute("errorMessage", ex.getMessage());
//        return "error/404";
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleGeneralException(Exception ex, Model model) {
//        model.addAttribute("errorMessage", "Произошла непредвиденная ошибка: " + ex.getMessage());
//        return "error/500";
//    }
//}
