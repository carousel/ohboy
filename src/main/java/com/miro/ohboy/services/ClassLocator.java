package com.miro.ohboy.services;

import com.miro.ohboy.exception.ClassLocationException;

import java.util.Set;

public interface ClassLocator {
    Set<Class<?>> locateClasses(String location) throws ClassLocationException;
}
