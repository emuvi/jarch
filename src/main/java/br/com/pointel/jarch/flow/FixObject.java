package br.com.pointel.jarch.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FixObject {
    String value();
    Class<?> type();
}
