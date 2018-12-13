package cscc.edu;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;

public class ConfigTest {

    @Test
    public void getPropertyDefaultPage() throws IOException {
        Config config = new Config();
        String actual = config.getProperty("defaultPage");
        assertEquals("index.html", actual);
    }

    @Test
    public void getPropertyPort() throws IOException {
        Config config = new Config();
        String actual = config.getProperty("port");
        assertEquals("80", actual);
    }

    @Test
    public void getPropertyDefaultFolder() throws IOException {
        Config config = new Config();
        String actual = config.getProperty("defaultFolder");
        assertEquals("./html", actual);
    }
}