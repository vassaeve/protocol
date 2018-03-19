package com.altynkez.rgis.vassaeve.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * синглтон
 *
 * @author vassaeve
 *
 */
public final class Preferences {

    private static Preferences instance;

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }
        return instance;
    }

    private HashMap<String, String> settings;

    private Preferences() {

        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("conf.conf"));
            for (Enumeration en = prop.keys(); en.hasMoreElements();) {
                String key1 = (String) en.nextElement();
                String value1 = prop.getProperty(key1);
                settings.put(key1, value1);
            }
        } catch (IOException ex) {
            try {
                new File("conf.conf").createNewFile();
            } catch (IOException ex1) {
            }
        }
    }

    public String getSetting(String key) {
        return settings.get(key);
    }

}
