package com.moviedb.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, 
                          Constructor testConstructor, Method testMethod) {
        // Automatically sets the retry analyzer for every test method found
        annotation.setRetryAnalyzer(MyRetryAnalyzer.class);
    }
}
