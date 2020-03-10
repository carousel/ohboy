package com.miro.ohboy;

import com.miro.ohboy.config.Location;
import com.miro.ohboy.services.LocationResolverImp;

public class Boot {
    public static void main(String[] args) {
        run(Boot.class);
    }

    public static void run(Class<Boot> startupClass) {
        Location location = new LocationResolverImp().resolveLocation(startupClass);
        System.out.println("Location type is: " + location.getLocationType());
    }
}

//static analysis
//Boot (main class)
//    new LocationResolver (service/interface)
//        returns Location (data model)
//            LocationType (enum)

//service returns model
