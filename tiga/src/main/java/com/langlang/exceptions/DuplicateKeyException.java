package com.langlang.exceptions;

public class DuplicateKeyException extends RuntimeException {

    public DuplicateKeyException() {
        super();
    }


    public DuplicateKeyException(String s) {
        super(s);
    }


    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }


    public DuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
