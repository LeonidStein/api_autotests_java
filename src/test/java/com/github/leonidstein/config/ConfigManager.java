package com.github.leonidstein.config;

import org.aeonbits.owner.ConfigCache;

public final class ConfigManager {

    public static Configuration config() {

        return ConfigCache.getOrCreate(Configuration.class);
    }
}
