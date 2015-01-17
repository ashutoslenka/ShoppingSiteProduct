package main.java.shoppingsiteservices.service.resources;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import javax.ws.rs.HttpMethod;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("SECURE")
public @interface SECURE {
}
