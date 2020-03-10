package com.miro.ohboy.enums;

public enum LocationType {
    DIRECTORY("directory"), JAR("jar");

    public String getLocationType() {
        return locationType;
    }

    private String locationType;

    LocationType(String locationType) {
        this.locationType = locationType;
    }
}
