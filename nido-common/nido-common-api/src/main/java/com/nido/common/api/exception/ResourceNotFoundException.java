package com.nido.common.api.exception;

public final class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = -787552142784618956L;

}