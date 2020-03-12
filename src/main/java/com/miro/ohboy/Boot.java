package com.miro.ohboy;

import com.miro.ohboy.config.OhboyConfig;
import com.miro.ohboy.enums.LocationType;
import com.miro.ohboy.exception.ClassLocationException;
import com.miro.ohboy.model.Location;
import com.miro.ohboy.model.ServiceDetails;
import com.miro.ohboy.services.*;

import java.util.Set;

//main class
public class Boot {
    public static void main(String[] args) throws NoSuchMethodException {
        run(Boot.class, new OhboyConfig());
//        run(new OhboyConfig());
    }

    public static void run(OhboyConfig ohboyConfig) throws NoSuchMethodException {
        System.out.println(ohboyConfig.getClass().getConstructor());
    }

    public static void run(Class<Boot> startupClass, OhboyConfig ohboyConfig) {
        Location location = new LocationResolverImp().resolveLocation(startupClass);
        ClassLocator classLocator = new DirectoryLocator();
        if (location.getLocationType().equals(LocationType.JAR)) {
            classLocator = new JarLocator();
        }
        Set<Class<?>> locatedClasses = classLocator.locateClasses(location.getLocationName());
        ServiceScanner serviceScanner = new ServiceScannerImp(ohboyConfig.annotations());
        Set<ServiceDetails<?>> serviceDetails = serviceScanner.mapServices(locatedClasses);
        System.out.println(serviceDetails);
    }
}






