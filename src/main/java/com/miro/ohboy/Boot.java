package com.miro.ohboy;

import com.miro.ohboy.enums.LocationType;
import com.miro.ohboy.model.Location;
import com.miro.ohboy.services.ClassLocator;
import com.miro.ohboy.services.DirectoryLocator;
import com.miro.ohboy.services.JarLocator;
import com.miro.ohboy.services.LocationResolverImp;

import java.util.Set;

//main class
public class Boot {
    public static void main(String[] args) {
        run(Boot.class);
    }

    public static void run(Class<Boot> startupClass) {
        Location location = new LocationResolverImp().resolveLocation(startupClass);
        ClassLocator classLocator = new DirectoryLocator();
        if (location.getLocationType().equals(LocationType.JAR)) {
            classLocator = new JarLocator();
        }
        Set<Class<?>> classes = classLocator.locateClasses(location.getLocationName());
        System.out.println(classes);

//        System.out.println("Location type is: " + location.getLocationType());
//        shortcut
//        String locationName =  startupClass.getProtectionDomain().getCodeSource().getLocation().getFile();
//        File file = new File(locationName);
//        if (!file.isDirectory() && locationName.endsWith(".jar")) {
//            System.out.println("Directory type is: JAR");
//        } else {
//            System.out.println("Directory type is: DIRECTORY");
//        }
    }
}

//static analysis
//Boot (main class)
//    new LocationResolver (service/interface)
//        returns Location (data model)
//            LocationType (enum)

//service returns model
