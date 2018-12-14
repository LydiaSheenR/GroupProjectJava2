package cscc.edu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Process webserver configuration
 * @author Lydiasheen Rhymond
 * @author Visalakshi(Vidya) Rajesh
 */
public class Config {
    public static final String PORT = "port";
    public static final String DEFAULTPAGE = "defaultPage";
    public static final String DEFAULTFOLDER = "defaultFolder";
    private static final String CONFIG_FILE = "./TinyWS.xml";
    private static Properties properties;

    /**
     * Instantiating the config class
     * @throws IOException - throws IO exception
     */
    public Config() throws IOException {
        readProperties();
        dumpProperties();
    }

    /**
     * method to read properties
     * @throws IOException - throws IO exception
     */
    public void readProperties() throws IOException {
        try {
            File file = new File(CONFIG_FILE);
            FileInputStream fileInput = new FileInputStream(file);
            properties = new Properties();
            properties.loadFromXML(fileInput);
            fileInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * method to return property of key
     * @param key -key is passed to the method
     * @return - the value for the key that was passed is returned
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * method to write or dump key and values
     */
    public void dumpProperties() {
        Enumeration enuKeys = properties.keys();
        while (enuKeys.hasMoreElements()) {
            String key = (String) enuKeys.nextElement();
            String value = properties.getProperty(key);
            System.out.println(key + ": " + value);
        }
    }


}
