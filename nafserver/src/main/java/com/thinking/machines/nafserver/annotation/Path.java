package com.thinking.machine.nafserver.annotation;
import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Path
{
public String value();
}
