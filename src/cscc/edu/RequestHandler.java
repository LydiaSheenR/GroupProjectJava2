package cscc.edu;

import java.io.*;
import java.net.Socket;

/**
 * Request handler method
 *
 * @author Lydiasheen Rhymond
 * @author Visalakshi(Vidya) Rajesh
 */
public class RequestHandler {
    private Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void processRequest() throws IOException {
        try {
            System.out.println("Got a request");
            String request = readRequest();
            HTTPRequest httprequest = new HTTPRequest(request);
            ResponseHandler responseHandler = new ResponseHandler(httprequest);
            responseHandler.sendResponse(connection);
        } finally {
            connection.close();
        }
    }

    private String readRequest() throws IOException {

        connection.setSoTimeout(500);
        int recbufsize = connection.getReceiveBufferSize();
        InputStream in = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String request = bufferedReader.readLine();
        return request;
    }

}
