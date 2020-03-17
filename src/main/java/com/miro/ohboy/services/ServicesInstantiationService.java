package com.miro.ohboy.services;

import com.miro.ohboy.model.ServiceDetails;

import java.util.List;
import java.util.Set;

public interface ServicesInstantiationService {
    List<ServiceDetails<?>> instantiateServicesAndBeans(Set<ServiceDetails<?>> mappedService) throws InstantiationException;
}
