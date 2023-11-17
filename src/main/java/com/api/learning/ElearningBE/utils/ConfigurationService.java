package com.api.learning.ElearningBE.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ConfigurationService extends PropertiesConfiguration implements ConfigurationListener {
    private static final String PROPERTIES_RESOURCE = "config.properties";
    private static ConfigurationService instance = null;

    public static ConfigurationService getInstance(){
        if (instance == null){
            instance = new ConfigurationService();
            instance.loadProperties();
        }
        return instance;
    }

    private void loadProperties(){
        try {
            File file = new File(PROPERTIES_RESOURCE);
            loadProperties(file);
        }catch (IOException e){
            log.warn("Can not load properties: ",e);
        }
    }
    private void loadProperties(File file) throws IOException {
        try {
            instance.setFile(file);
            instance.setReloadingStrategy(new FileChangedReloadingStrategy());
            instance.setAutoSave(true);
            instance.load();
            log.info("Loaded properties from: " + PROPERTIES_RESOURCE);
        }catch (ConfigurationException e){
            throw new IOException("Unable to load properties from: " + PROPERTIES_RESOURCE);
        }
    }
    @Override
    public void configurationChanged(ConfigurationEvent configurationEvent) {

    }
}
