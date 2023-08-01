package com.example.calculatrice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IllegalBusinessLogiqueException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = LoggerFactory.getLogger(IllegalBusinessLogiqueException.class);

    public IllegalBusinessLogiqueException() {
        super();
    }

    public IllegalBusinessLogiqueException(final String message, final Throwable cause) {
        super(message, cause);
        LOG.error(message, new Throwable(cause));
    }

    public IllegalBusinessLogiqueException(final String message) {
        super(message);
        LOG.error(message);
    }

//    public IllegalBusinessLogiqueException(final Throwable cause) {
//        super(cause);
//    }
//
//    public IllegalBusinessLogiqueException(final String message, final Throwable cause, boolean enableSuppression,
//            boolean writableStackTrace) {
//         super(message, cause,enableSuppression,writableStackTrace);
//    }
}
