package vng.toanhda.utils;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileConfigLoader {
  /**
   * Load configuration from file
   *
   * @param fileName File path in YAML format
   * @throws FileNotFoundException in case file not exist
   */
  public static <T> T load(String fileName, Class<T> tClass) throws FileNotFoundException {
    InputStream input = new FileInputStream(new File(fileName));

    Yaml yaml = new Yaml(new Constructor(tClass));
    return yaml.load(input);
  }

  public static <T> T load(Class<T> tClass) throws FileNotFoundException {
    String fileName = System.getProperty("server.conf");
    return load(fileName, tClass);
  }
}
