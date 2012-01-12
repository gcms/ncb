package cvm.ncb.adapters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Method {
    String name();
    String[] parameters() default {};
}
