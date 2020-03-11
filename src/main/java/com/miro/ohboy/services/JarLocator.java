package com.miro.ohboy.services;

import com.miro.ohboy.constants.Constants;
import com.miro.ohboy.exception.ClassLocationException;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarLocator implements ClassLocator {
    @Override
    public Set<Class<?>> locateClasses(String location) throws ClassLocationException {
        final Set<Class<?>> locatedClasses = new HashSet<>();
        try {
            JarFile jarFile = new JarFile(new File(location));
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (!jarEntry.getName().endsWith(Constants.JAVA_BINARY_EXTENSION)) {
                    continue;
                }
                final String className = jarEntry.getName().replace(Constants.JAVA_BINARY_EXTENSION,"")
                        .replaceAll("\\\\",".")
                        .replaceAll("/",".");
                locatedClasses.add(Class.forName(className));
            }
        } catch (Exception e) {
            throw new ClassLocationException(e.getMessage(), e);
        }

        return locatedClasses;
    }
}
