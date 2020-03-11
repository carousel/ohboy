package com.miro.ohboy.model;

import com.miro.ohboy.enums.LocationType;

//model
public class Location {
    private final String locationName;
    private final LocationType locationType;

    public Location(String locationName, LocationType locationType) {
        this.locationName = locationName;
        this.locationType = locationType;
    }


    public LocationType getLocationType() {
        return locationType;
    }

}
