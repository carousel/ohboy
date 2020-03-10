package com.miro.ohboy.services;

import com.miro.ohboy.config.Location;

public interface LocationResolver {
    Location resolveLocation(Class<?> startup);
}
