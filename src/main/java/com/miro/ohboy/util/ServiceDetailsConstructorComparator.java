package com.miro.ohboy.util;

import com.miro.ohboy.model.ServiceDetails;

import java.util.Comparator;

public class ServiceDetailsConstructorComparator implements Comparator<ServiceDetails> {
    @Override
    public int compare(ServiceDetails serviceDetails, ServiceDetails serviceDetails2) {
        return Integer.compare(
                serviceDetails.getTargetConstructor().getParameterCount(),
                serviceDetails2.getTargetConstructor().getParameterCount()
        );
    }
}
