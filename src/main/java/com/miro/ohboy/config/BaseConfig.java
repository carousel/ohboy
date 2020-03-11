package com.miro.ohboy.config;

public abstract class BaseConfig {
    private final OhboyConfig parentConfiguration;

    protected BaseConfig(OhboyConfig parentConfiguration) {
        this.parentConfiguration = parentConfiguration;
    }

    public OhboyConfig and() {
        return this.parentConfiguration;
    }
}
