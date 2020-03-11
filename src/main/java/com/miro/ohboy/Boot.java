package com.miro.ohboy;

import com.miro.ohboy.model.Location;
import com.miro.ohboy.services.LocationResolverImp;

//main class
public class Boot {
    public static void main(String[] args) {
        run(Boot.class);
    }

    public static void run(Class<Boot> startupClass) {
        Location location = new LocationResolverImp().resolveLocation(startupClass);
        System.out.println("Location type is: " + location.getLocationType());
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
