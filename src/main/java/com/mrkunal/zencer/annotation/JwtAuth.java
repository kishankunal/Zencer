package com.mrkunal.zencer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // This annotation will be used on methods
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtAuth {
    String[] roles() default {};
}
