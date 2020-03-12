package com.miro.ohboy.services;

import com.miro.ohboy.exception.ServiceInstantiationException;
import com.miro.ohboy.exception.PreDestroyException;
import com.miro.ohboy.model.ServiceBeanDetails;
import com.miro.ohboy.model.ServiceDetails;

public interface ObjectInstantiationService {
    void createInstance(ServiceDetails<?> serviceDetails, Object... constructorParams) throws ServiceInstantiationException;

    void createBeanInstance(ServiceBeanDetails<?> serviceBeanDetails) throws ServiceInstantiationException;

    void destroyInstance(ServiceDetails<?> serviceDetails) throws PreDestroyException;
}
