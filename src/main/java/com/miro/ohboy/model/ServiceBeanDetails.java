package com.miro.ohboy.model;

import java.lang.reflect.Method;

public class ServiceBeanDetails<T> extends ServiceDetails<T> {
    private final Method originMethod;
    private final ServiceDetails<?> rootServices;

    public ServiceBeanDetails(Class<T> beanType, Method originMethod, ServiceDetails<?> rootServices) {
        this.setServiceType(beanType);
        this.originMethod = originMethod;
        this.setBeans(new Method[0]);
        this.rootServices = rootServices;
    }

    public Method getOriginMethod() {
        return originMethod;
    }

    public ServiceDetails<?> getRootServices() {
        return rootServices;
    }
}
