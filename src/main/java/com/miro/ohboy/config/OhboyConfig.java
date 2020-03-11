package com.miro.ohboy.config;

import com.miro.ohboy.config.configs.CustomAnnotationsConfig;

public class OhboyConfig {

    private final CustomAnnotationsConfig annotations;

    public OhboyConfig() {
        annotations = new CustomAnnotationsConfig(this);
    }
    public CustomAnnotationsConfig annotations() {
        return annotations;
    }

    public OhboyConfig build() {
        return this;
    }
}
