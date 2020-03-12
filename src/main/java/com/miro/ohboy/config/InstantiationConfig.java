package com.miro.ohboy.config;

import com.miro.ohboy.constants.Constants;

public class InstantiationConfig extends BaseConfig {
    private int maximumAllowedIterations;

    protected InstantiationConfig(OhboyConfig parentConfiguration) {
        super(parentConfiguration);
        this.maximumAllowedIterations = Constants.MAX_ALLOWED_ITERATIONS;
    }

    public InstantiationConfig setMaximumNumberOfIterations(int numberOfIterations) {
        this.maximumAllowedIterations = numberOfIterations;
        return this;
    }

    public int getMaximumAllowedIterations() {
        return maximumAllowedIterations;
    }
}
