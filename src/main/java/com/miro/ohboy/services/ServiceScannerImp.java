package com.miro.ohboy.services;

import com.miro.ohboy.annotation.*;
import com.miro.ohboy.config.configs.CustomAnnotationsConfig;
import com.miro.ohboy.model.ServiceDetails;
import com.miro.ohboy.util.ServiceDetailsConstructorComparator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceScannerImp implements ServiceScanner {
    private final CustomAnnotationsConfig customAnnotationsConfig;

    public ServiceScannerImp(CustomAnnotationsConfig customAnnotationsConfig) {
        this.customAnnotationsConfig = customAnnotationsConfig;
        init();
    }

    @Override
    public Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses) {
        final Set<ServiceDetails<?>> serviceDetailsStorage = new HashSet<>();
        final Set<Class<? extends Annotation>> customServiceAnnotations = customAnnotationsConfig.getCustomServiceAnnotations();
        for (Class<?> locatedClass : locatedClasses) {
            if (locatedClass.isInterface()) {
                continue;
            }
            for (Annotation annotation : locatedClass.getAnnotations()) {
                if (customServiceAnnotations.contains(annotation.annotationType())) {
                    ServiceDetails<?> serviceDetails = new ServiceDetails(
                            locatedClass,
                            annotation,
                            findSuitableConstructor(locatedClass),
                            findVoidMethodWithNoAnnotations(PostConstruct.class, locatedClass),
                            findVoidMethodWithNoAnnotations(PreDestroy.class, locatedClass),
                            findBeans(locatedClass)
                    );
                    serviceDetailsStorage.add(serviceDetails);
                    break;
                }

            }
        }
        return serviceDetailsStorage
                .stream()
                .sorted(new ServiceDetailsConstructorComparator())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    private Constructor<?> findSuitableConstructor(Class<?> className) {
        for (Constructor<?> declaredConstructor : className.getDeclaredConstructors()) {
            if (declaredConstructor.isAnnotationPresent(Autowired.class)) {
                declaredConstructor.setAccessible(true);
                return declaredConstructor;
            }
        }
        return className.getDeclaredConstructors()[0];
    }

    private Method findVoidMethodWithNoAnnotations(Class<? extends Annotation> annotation, Class<?> className) {
        for (Method declaredMethod : className.getDeclaredMethods()) {
            if (declaredMethod.getParameterCount() != 0
                    || declaredMethod.getReturnType() != void.class
                    && declaredMethod.getReturnType() != Void.class && declaredMethod.isAnnotationPresent(annotation)) {
                continue;
            }
            declaredMethod.setAccessible(true);
            return declaredMethod;
        }
        return null;
    }

    private Method[] findBeans(Class<?> className) {
        final Set<Class<? extends Annotation>> customBeanAnnotations = customAnnotationsConfig.getCustomBeanAnnotations();
        final Set<Method> beanMethods = new HashSet<>();
        for (Method declaredMethod : className.getDeclaredMethods()) {
            if (declaredMethod.getParameterCount() != 0
                    || declaredMethod.getReturnType() != void.class
                    && declaredMethod.getReturnType() != Void.class) {
                continue;
            }
            for (Class<? extends Annotation> customBeanAnnotation : customBeanAnnotations) {
                if (declaredMethod.isAnnotationPresent(customBeanAnnotation)) {
                    declaredMethod.setAccessible(true);
                    beanMethods.add(declaredMethod);
                    break;
                }
            }
        }
        return beanMethods.toArray(Method[]::new);
    }

    private void init() {
        customAnnotationsConfig.getCustomBeanAnnotations().add(Bean.class);
        customAnnotationsConfig.getCustomServiceAnnotations().add(Service.class);
    }
}
