package com.miro.ohboy.services;

import com.miro.ohboy.config.InstantiationConfig;
import com.miro.ohboy.model.EnqueuedServiceDetails;
import com.miro.ohboy.model.ServiceBeanDetails;
import com.miro.ohboy.model.ServiceDetails;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

//service
public class ServicesInstantiationServiceImpl implements ServicesInstantiationService {
    private final static String MAXIMUM_NUMBER_OF_ALLOWED_ITERATIONS_REACHED = "Maximum number of allowed iterations reached";
    private final String COULD_NOT_FIND_CONSTRUCTOR_PARAM_MESSAGE = "Could not create instance of '%'  parameter  '%' implementation was not found";
    private final InstantiationConfig instantiationConfig;
    private final ObjectInstantiationService instantiationService;
    private final LinkedList<EnqueuedServiceDetails> enqueuedServiceDetails;
    private final List<Class<?>> allAvailableClasses;
    private List<ServiceDetails<?>> instantiatedServices;

    public ServicesInstantiationServiceImpl(InstantiationConfig instantiationConfig, ObjectInstantiationService instantiationService) {
        this.instantiationConfig = instantiationConfig;
        this.instantiationService = instantiationService;
        this.enqueuedServiceDetails = new LinkedList<>();
        this.allAvailableClasses = new ArrayList<>();
        this.instantiatedServices = new ArrayList<>();
    }

    public void checkForMissingServices(Set<ServiceDetails<?>> mappedServices) throws InstantiationException {
        for (ServiceDetails<?> serviceDetail : mappedServices) {
            for (Class<?> parameterType : serviceDetail.getTargetConstructor().getParameterTypes()) {
                if (!this.isAssignableTypePresent(parameterType)) {
                    throw new InstantiationException(String.format(COULD_NOT_FIND_CONSTRUCTOR_PARAM_MESSAGE,
                            serviceDetail.getServiceType().getName(),
                            parameterType.getName()));
                }
            }
        }
    }

    private boolean isAssignableTypePresent(Class<?> className) {
        for (Class<?> serviceType : this.allAvailableClasses) {
            if (className.isAssignableFrom(serviceType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ServiceDetails<?>> instantiateServicesAndBeans(Set<ServiceDetails<?>> mappedServices) throws InstantiationException {
        this.init(mappedServices);
        this.checkForMissingServices(mappedServices);
        int maximumNumberOfIterationsAllowed = this.instantiationConfig.getMaximumAllowedIterations();
        int counter = 0;
        while (!this.enqueuedServiceDetails.isEmpty()) {
            if (counter > maximumNumberOfIterationsAllowed) {
                throw new InstantiationException(String.format(MAXIMUM_NUMBER_OF_ALLOWED_ITERATIONS_REACHED, maximumNumberOfIterationsAllowed));
            }
            EnqueuedServiceDetails enqueuedServiceDetails = this.enqueuedServiceDetails.removeFirst();
            if (enqueuedServiceDetails.isResolved()) {
                ServiceDetails<?> serviceDetails = enqueuedServiceDetails.getServiceDetails();
                Object[] dependencyInstances = enqueuedServiceDetails.getDependencyInstances();
                this.instantiationService.createInstance(serviceDetails, dependencyInstances);
                this.registerInstantiatedServices(serviceDetails);
                this.registerBeans(serviceDetails);
            } else {
                this.enqueuedServiceDetails.addLast(enqueuedServiceDetails);
                counter++;
            }

        }
        return this.instantiatedServices;
    }

    private void registerBeans(ServiceDetails<?> serviceDetails) {
        for (Method beanMethod : serviceDetails.getBeans()) {
            ServiceBeanDetails<?> serviceBeanDetails = new ServiceBeanDetails<>(beanMethod.getReturnType(), beanMethod, serviceDetails);
            this.instantiationService.createBeanInstance(serviceBeanDetails);
            this.registerInstantiatedServices(serviceBeanDetails);
        }
    }

    private void registerInstantiatedServices(ServiceDetails<?> serviceDetails) {
        if (!(serviceDetails instanceof ServiceBeanDetails)) {
            this.updateDependentServices(serviceDetails);
        }
        this.instantiatedServices.add(serviceDetails);
        for (EnqueuedServiceDetails enqueuedServiceDetail : this.enqueuedServiceDetails) {
            if (enqueuedServiceDetail.isDependencyRequired(serviceDetails.getServiceType())) {
                enqueuedServiceDetail.addDependencyInstance(serviceDetails.getInstance());
            }
        }
    }

    private void updateDependentServices(ServiceDetails<?> newService) {
        for (Class<?> parameterType : newService.getTargetConstructor().getParameterTypes()) {
            for (ServiceDetails<?> instantiatedService : instantiatedServices) {
                if (parameterType.isAssignableFrom(instantiatedService.getServiceType())) {
                    instantiatedService.setDependentService(newService);
                }
            }
        }
    }

    private void init(Set<ServiceDetails<?>> mappedServices) {
        this.enqueuedServiceDetails.clear();
        this.allAvailableClasses.clear();
        this.instantiatedServices.clear();

        for (ServiceDetails<?> serviceDetails : mappedServices) {
            this.enqueuedServiceDetails.add(new EnqueuedServiceDetails(serviceDetails));
            this.allAvailableClasses.add(serviceDetails.getServiceType());
            this.allAvailableClasses.addAll(Arrays.stream(serviceDetails.getBeans())
                    .map(Method::getReturnType)
                    .collect(Collectors.toList())
            );
       }
    }
}
