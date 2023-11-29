package org.example.util.yml;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public final class YmlReader {

    public static String getCacheType() {
        try (InputStream inputStream = YmlReader.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return String.valueOf(data.get("cache"));
        } catch (IOException e) {
            throw new RuntimeException("Error reading application.yml", e);
        }
    }

    public static String getCacheCapacity() {
        try (InputStream inputStream = YmlReader.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return String.valueOf(data.get("capacity"));
        } catch (IOException e) {
            throw new RuntimeException("Error reading application.yml", e);
        }
    }

}

