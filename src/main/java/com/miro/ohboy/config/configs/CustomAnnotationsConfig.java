package com.miro.ohboy.config.configs;

import com.miro.ohboy.config.BaseConfig;
import com.miro.ohboy.config.OhboyConfig;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomAnnotationsConfig extends BaseConfig {
    private final Set<Class<? extends Annotation>> customServiceAnnotations;
    private final Set<Class<? extends Annotation>> customBeanAnnotations;

    public CustomAnnotationsConfig(OhboyConfig parentConfiguration) {
        super(parentConfiguration);
        customServiceAnnotations = new HashSet<>();
        customBeanAnnotations = new HashSet<>();
    }

    public CustomAnnotationsConfig addCustomServiceAnnotation(Class<? extends Annotation> annotation) {
        customServiceAnnotations.add(annotation);
        return this;
    };
    public CustomAnnotationsConfig addCustomServiceAnnotation(Class<? extends Annotation>... annotations) {
        customServiceAnnotations.addAll(Arrays.asList(annotations));
        return this;
    };
    public CustomAnnotationsConfig addCustomBeanAnnotation(Class<? extends Annotation> annotation) {
        customBeanAnnotations.add(annotation);
        return this;
    };
    public CustomAnnotationsConfig addCustomBeanAnnotation(Class<? extends Annotation>... annotations) {
        customBeanAnnotations.addAll(Arrays.asList(annotations));
        return this;
    };

    public Set<Class<? extends Annotation>> getCustomServiceAnnotations() {
        return customServiceAnnotations;
    }

    public Set<Class<? extends Annotation>> getCustomBeanAnnotations() {
        return customBeanAnnotations;
    }
}
