package cscc.edu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HTTPRequestTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isValidRequest1positive() {
        HTTPRequest httpRequest = new HTTPRequest("GET qwerty");
        boolean actual = httpRequest.isValidRequest();
        assertEquals(true, actual);
    }

    @Test
    public void getPath() {
    }
}