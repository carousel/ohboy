package com.miro.ohboy.services;

import com.miro.ohboy.model.ServiceDetails;

import java.util.Set;

public interface ServiceScanner {
    Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses);
}
