package cscc.edu;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for HTTPRequest
 *
 * @author Lydiasheen Rhymond
 * @author Visalakshi(Vidya) Rajesh
 */
public class HTTPRequestTest {

    @Test
    public void isValidRequest1positive() {
        HTTPRequest httpRequest = new HTTPRequest("GET /path/filename ");
        boolean actual = httpRequest.isValidRequest();
        assertTrue(actual);
    }

    @Test
    public void isValidRequest2positive() {
        HTTPRequest httpRequest = new HTTPRequest("GET /index.html");
        boolean actual = httpRequest.isValidRequest();
        assertTrue(actual);
    }

    @Test
    public void isValidRequest3positive() {
        HTTPRequest httpRequest = new HTTPRequest("GET /images/somepix.jpg");
        boolean actual = httpRequest.isValidRequest();
        assertTrue(actual);
    }

    @Test
    public void isValidRequest4positive() {
        HTTPRequest httpRequest = new HTTPRequest("GET /\t");
        boolean actual = httpRequest.isValidRequest();
        assertTrue(actual);
    }

    @Test
    public void isValidRequest5positive() {
        HTTPRequest httpRequest = new HTTPRequest("GET /index.html?name1=value1&name2=value2");
        boolean actual = httpRequest.isValidRequest();
        assertTrue(actual);
    }

    @Test
    public void isValidRequest1negative() {
        HTTPRequest httpRequest = new HTTPRequest("html/index.html");
        boolean actual = httpRequest.isValidRequest();
        assertFalse(actual);
    }

    @Test
    public void getPath() {
        HTTPRequest httpRequest = new HTTPRequest("GET /index.html");
        String actual = httpRequest.getPath();
        assertEquals("/index.html", actual);
    }
}