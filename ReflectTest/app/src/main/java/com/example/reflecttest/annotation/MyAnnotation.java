package com.example.reflecttest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用 @interface 定义注解时，意味着它实现了 java.lang.annotation.Annotation 接口，即该注解就是一个 Annotation。
 * 使用 @Retention 定义注解时，RUNTIME：编译器将Annotation存储于class文件中，并且可由JVM读取
 * 使用 @Target定义注解时，Type：类、接口、枚举类型
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotation {
    String[] value() default "unknown";
}
