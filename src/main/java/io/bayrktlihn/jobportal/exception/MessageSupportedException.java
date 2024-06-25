package io.bayrktlihn.jobportal.exception;

public class MessageSupportedException extends RuntimeException {

    private final String message;

    public MessageSupportedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
