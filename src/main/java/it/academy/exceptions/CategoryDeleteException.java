package it.academy.exceptions;

public class CategoryDeleteException extends RuntimeException {
    public CategoryDeleteException(String message) {
        super(message);
    }
}
