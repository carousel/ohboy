package com.miro.ohboy;

import com.miro.ohboy.config.OhboyConfig;
import com.miro.ohboy.enums.LocationType;
import com.miro.ohboy.model.Location;
import com.miro.ohboy.model.ServiceDetails;
import com.miro.ohboy.services.*;

import java.util.List;
import java.util.Set;

//main class
public class Boot {
    public static void main(String[] args) throws InstantiationException {
        run(Boot.class, new OhboyConfig());
//        run(new OhboyConfig());
    }

    public static void run(OhboyConfig ohboyConfig) throws NoSuchMethodException {
        System.out.println(ohboyConfig.getClass().getConstructor());
    }

    public static void run(Class<Boot> startupClass, OhboyConfig ohboyConfig) throws InstantiationException {
        //search for executable location
        Location location = new LocationResolverImpl().resolveLocation(startupClass);

        //JAR or directory?
        ClassLocator classLocator = new DirectoryLocator();
        if (location.getLocationType().equals(LocationType.JAR)) {
            classLocator = new JarLocator();
        }
        //class locator is service
        Set<Class<?>> locatedClasses = classLocator.locateClasses(location.getLocationName());

        //service scanner is service
        ServiceScanner serviceScanner = new ServiceScannerImp(ohboyConfig.annotations());
        Set<ServiceDetails<?>> mappedServices = serviceScanner.mapServices(locatedClasses);

        //instantiation process
        ObjectInstantiationService objectInstantiationService = new ObjectInstantiationServiceImpl();
        ServicesInstantiationService instantiationService = new ServicesInstantiationServiceImpl(
                ohboyConfig.instantiationConfig(),
                objectInstantiationService);
        List<ServiceDetails<?>> serviceDetails = instantiationService.instantiateServicesAndBeans(mappedServices);

    }
}






