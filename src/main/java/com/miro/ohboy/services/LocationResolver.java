package com.miro.ohboy.services;

import com.miro.ohboy.model.Location;

public interface LocationResolver {
    Location resolveLocation(Class<?> startup);
}
