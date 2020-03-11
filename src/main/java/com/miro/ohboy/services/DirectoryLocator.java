package com.miro.ohboy.services;

import com.miro.ohboy.constants.Constants;
import com.miro.ohboy.exception.ClassLocationException;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DirectoryLocator implements ClassLocator {
    private static final String INVALID_DIRECTORY_PATH = "Invalid directory path";
    private final Set<Class<?>> locatedClasses;

    public DirectoryLocator() {
        this.locatedClasses = new HashSet<>();
    }

    @Override
    public Set<Class<?>> locateClasses(String location) throws ClassLocationException {
        this.locatedClasses.clear();
        File file = new File(location);
        if (!file.isDirectory()) {
            throw new ClassLocationException(String.format(INVALID_DIRECTORY_PATH, location));
        }
        for (File innerFile : file.listFiles()) {
            try {
                scanDirectory(innerFile, "");
            } catch (ClassNotFoundException e) {
                throw new ClassLocationException(e.getMessage(), e);
            }
        }
        return this.locatedClasses;
    }

    private void scanDirectory(File file, String packageName) throws ClassNotFoundException {
        if (file.isDirectory()) {
            packageName += file.getName() + ".";
            for (File innerFile : file.listFiles()) {
                this.scanDirectory(innerFile, packageName);
            }
        } else {
            if (!file.getName().endsWith(Constants.JAVA_BINARY_EXTENSION)) {
                return;
            }
            final String className = packageName + file.getName().replace(Constants.JAVA_BINARY_EXTENSION, "");
            this.locatedClasses.add(Class.forName(className));
        }

    }
}
