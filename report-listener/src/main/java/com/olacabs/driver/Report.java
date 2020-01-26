package com.olacabs.driver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Report {

    boolean enabled() default false;

    public String testcaseId() default "";

    public String testcaseName() default "";

    public String testcaseDescription() default "";

    public String priority() default "";

    public String ownerName() default "";

    public String groups() default "";


}