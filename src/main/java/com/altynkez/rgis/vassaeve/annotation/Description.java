package com.altynkez.rgis.vassaeve.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vassaeve
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.FIELD})
public @interface Description {

    String value() default "";

    boolean PK() default false;
}
