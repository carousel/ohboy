package com.miro.ohboy.config;

import com.miro.ohboy.config.configs.CustomAnnotationsConfig;

public class OhboyConfig {

    private final CustomAnnotationsConfig customAnnotationsConfig;

    private final InstantiationConfig instantiationConfig;

    public InstantiationConfig instantiationConfig() {
        return this.instantiationConfig;
    }

    public OhboyConfig() {
        customAnnotationsConfig = new CustomAnnotationsConfig(this);
        instantiationConfig = new InstantiationConfig(this);
    }
    public CustomAnnotationsConfig annotations() {
        return customAnnotationsConfig;
    }

    public OhboyConfig build() {
        return this;
    }
}
