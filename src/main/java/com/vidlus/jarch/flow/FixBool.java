package com.vidlus.jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A runtime annotation used to inject or enforce a fixed boolean value
 * on a field or parameter during mapping or configuration.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FixBool {
    /**
     * @return the fixed boolean value
     */
    boolean value();
}
