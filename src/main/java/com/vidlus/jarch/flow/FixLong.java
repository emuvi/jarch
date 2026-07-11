package com.vidlus.jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A runtime annotation used to inject or enforce a fixed long value
 * on a field or parameter during mapping or configuration.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FixLong {
    /**
     * @return the fixed long value
     */
    long value();
}
