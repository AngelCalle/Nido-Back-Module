package com.nido.common.api.exception;

public final class ConflictException extends RuntimeException {

	public ConflictException() {
        super();
    }

    public ConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ConflictException(final String message) {
        super(message);
    }

    public ConflictException(final Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 2833807842555437570L;

}