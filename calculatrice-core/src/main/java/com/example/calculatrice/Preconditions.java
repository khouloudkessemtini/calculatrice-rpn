package com.example.calculatrice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Simple static methods to be called at the start of your own methods to verify
 * correct arguments and state. If the Precondition fails, an {@link HttpStatus}
 * code is thrown
 */
public final class Preconditions {

    private final static Logger LOG = LoggerFactory.getLogger(Preconditions.class);

    private Preconditions() {
        throw new AssertionError();
    }

    // API
    /**
     * Check if some value was found, otherwise throw exception.
     *
     * @param expression has value true if found, otherwise false
     * @param message
     * @throws IllegalBusinessLogiqueException if expression is false, means value
     * not found.
     */
    public static void checkBusinessLogique(final boolean expression, String message) {
        if (!expression) {
            LOG.error(message, new Throwable(message));
            throw new IllegalBusinessLogiqueException(message);

        }
    }


}
