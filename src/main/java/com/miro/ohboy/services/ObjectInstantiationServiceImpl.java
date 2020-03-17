package com.miro.ohboy.services;

import com.miro.ohboy.annotation.Service;
import com.miro.ohboy.exception.PostCostructException;
import com.miro.ohboy.exception.ServiceInstantiationException;
import com.miro.ohboy.exception.PreDestroyException;
import com.miro.ohboy.model.ServiceBeanDetails;
import com.miro.ohboy.model.ServiceDetails;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//service
public class ObjectInstantiationServiceImpl implements ObjectInstantiationService {
    private static final String INVALID_PARAMETERS_COUNT = "Invalid paramteters count for '%'";

    @Override
    public void createInstance(ServiceDetails<?> serviceDetails, Object... constructorParams) throws ServiceInstantiationException {
        Constructor<?> targetConstructor = serviceDetails.getTargetConstructor();
        if (targetConstructor.getParameterCount() != constructorParams.length) {
            throw new ServiceInstantiationException(String.format(INVALID_PARAMETERS_COUNT, serviceDetails.getServiceType().getName()));
        }
        try {
            Object instance = targetConstructor.newInstance(constructorParams);
            serviceDetails.setInstance(instance);
            invokePostConstruct(serviceDetails);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ServiceInstantiationException(e.getMessage(), e);
        }
    }

    private void invokePostConstruct(ServiceDetails<?> serviceDetails) throws PostCostructException{
        if (serviceDetails.getPostConstructMethod() == null) {
            return;
        }
        try {
            serviceDetails.getPostConstructMethod().invoke(serviceDetails.getInstance());
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new PostCostructException(e.getMessage(), e);
        }
    }

    @Override
    public void createBeanInstance(ServiceBeanDetails<?> serviceBeanDetails) throws ServiceInstantiationException {
        Method originMethod = serviceBeanDetails.getOriginMethod();
        Object rootInstance = serviceBeanDetails.getRootServices().getInstance();
        try {
            Object instance = originMethod.invoke(rootInstance);
            serviceBeanDetails.setInstance(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ServiceInstantiationException(e.getMessage(), e);
        }
    }

    @Override
    public void destroyInstance(ServiceDetails<?> serviceDetails) throws PreDestroyException {
        if (serviceDetails.getPreDestroyMethod() != null) {
            try {
                serviceDetails.getPreDestroyMethod().invoke(serviceDetails.getInstance());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new PreDestroyException(e.getMessage(), e);
            }
        }
        serviceDetails.setInstance(null);
    }
}
