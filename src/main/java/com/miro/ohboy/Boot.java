package com.miro.ohboy;

import com.miro.ohboy.config.OhboyConfig;
import com.miro.ohboy.enums.LocationType;
import com.miro.ohboy.exception.ClassLocationException;
import com.miro.ohboy.model.Location;
import com.miro.ohboy.services.ClassLocator;
import com.miro.ohboy.services.DirectoryLocator;
import com.miro.ohboy.services.JarLocator;
import com.miro.ohboy.services.LocationResolverImp;

import java.util.Set;

//main class
public class Boot {
    public static void main(String[] args) throws NoSuchMethodException {
        run(Boot.class, new OhboyConfig());
        run(new OhboyConfig());
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
        Set<Class<?>> classes = classLocator.locateClasses(location.getLocationName());
        System.out.println("---------------------------------------------");
        System.out.println("Number of classes found: " + classes.size());
        System.out.println("---------------------------------------------");
        System.out.println("Number of service annotations: " + ohboyConfig.annotations().getCustomServiceAnnotations().size());
        System.out.println("---------------------------------------------");
        System.out.println("Number of bean annotations: " + ohboyConfig.annotations().getCustomBeanAnnotations().size());
        System.out.println("---------------------------------------------");
//        for (Class<?> aClass : classes) {
//            System.out.println(aClass.getConstructor());
//        }
    }
}






