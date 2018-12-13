package cscc.edu;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void processRequest() throws IOException {
        String request = readRequest();
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

        String request = bufferedReader.readLine().toString();


        return request.toString();

    }
}
