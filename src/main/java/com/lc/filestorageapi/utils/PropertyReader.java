package com.lc.filestorageapi.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final String CONFIG_FILE_PATH = "config.properties";

    public Properties initializeProperties()  {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            Properties properties = new Properties();
            if (input == null) {
                throw new FileNotFoundException( "Sorry, unable to find " + CONFIG_FILE_PATH);
            }
            properties.load(input);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
