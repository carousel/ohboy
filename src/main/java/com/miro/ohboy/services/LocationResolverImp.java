package com.miro.ohboy.services;

import com.miro.ohboy.config.Location;
import com.miro.ohboy.enums.LocationType;

import java.io.File;

//service
public class LocationResolverImp implements LocationResolver {

    private static final String JAR_EXTENSION = ".jar";

    @Override
    public Location resolveLocation(Class<?> startupClass) {
        String locationName = getLocation(startupClass);
        return new Location(locationName, getLocationType(locationName));
    }

    public String getLocation(Class<?> startupClass) {
        return startupClass
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getFile();
    }

    public LocationType getLocationType(String locatioName) {
        File file = new File(locatioName);
        if (!file.isDirectory() && locatioName.endsWith(JAR_EXTENSION)) {
            return LocationType.JAR;
        } else {
            return LocationType.DIRECTORY;
        }
    }
}
