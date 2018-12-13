package cscc.edu;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void processRequest() throws IOException {
        System.out.println("Got a request");
        String request = readRequest();
        HTTPRequest httprequest = new HTTPRequest(request);
        ResponseHandler responseHandler = new ResponseHandler(httprequest);
    }

    private String readRequest() throws IOException {
        // Set socket timeout to 500 milliseconds
        connection.setSoTimeout(500);
        int recbufsize = connection.getReceiveBufferSize();
        InputStream in = connection.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        //String inputline;
//        StringBuilder request = new StringBuilder();
//        while ((inputline = bufferedReader.readLine()) != null) {
//            request.append(inputline);
//        }
//        bufferedReader.close();

        String request = bufferedReader.readLine();


        return request;

    }

    public static void log(String s) {
        System.out.println(s);
    }

    public static void fatalError(String s) {
        handleError(s, null, true);
    }

    public static void fatalError(Exception e) {
        handleError(null, e, true);
    }

    public static void handleError(String s, Exception e, boolean isFatal) {
        if (s != null) {
            System.out.println(s);
        }
        if (e != null) {
            e.printStackTrace();
        }
        if (isFatal) System.exit(-1);
    }

}
