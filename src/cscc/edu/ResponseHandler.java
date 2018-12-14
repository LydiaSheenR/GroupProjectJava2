package cscc.edu;

import java.io.*;
import java.net.*;

/**
 * response handler method
 *
 * @author Lydiasheen Rhymond
 * @author Visalakshi(Vidya) Rajesh
 */
public class ResponseHandler {
    private static final String NOT_FOUND_RESPONSE =
            "HTTP/1.0 404 NotFound\n" +
                    "Server: TinyWS\n" +
                    "Content-type: text/plain\n\n" +
                    "Requested file not found.";

    private static final String FORBIDDEN_RESPONSE =
            "HTTP/1.0 403 Forbidden\n" +
                    "Server: TinyWS\n" +
                    "Content-type: text/plain\n\n" +
                    "Requested action is forbidden.  So there!";

    private static final String HTTP_OK_HEADER =
            "HTTP/1.0 200 OK\n" +
                    "Server: TinyWS\n" +
                    "Content-type: ";

    private HTTPRequest request;
    private int responseCode;
    private byte[] content;
    private String mimeType = null;

    /**
     * constructor
     * @param request - HTTP request
     * @throws IOException - throws IO exception
     */
    public ResponseHandler(HTTPRequest request) throws IOException {
        this.request = request;
        responseCode = 404;
        this.content = this.getFile(request.getPath());
        if (request.isValidRequest()) {
            if (this.content != null) {
                this.responseCode = 200;
            } else {
                this.responseCode = 404;
            }

        } else {
            this.responseCode = 403;
        }
    }

    /**
     * Send HTTP Response
     * @param connection - socket connection
     * @throws IOException - throws IO exception
     */
    public void sendResponse(Socket connection) throws IOException {
        byte[] response = null;
        int sendbufsize = connection.getSendBufferSize();
        BufferedOutputStream out = new BufferedOutputStream(
                connection.getOutputStream(), sendbufsize);
        if (this.responseCode == 200) {

            //HTTP_OK_HEADER.concat(this.getMimeType(request.getPath())) ;
            String h = HTTP_OK_HEADER + this.mimeType;
            response = h.getBytes();
            TinyWS.log(h);
            out.write(response);
            for (int i = 0; i < content.length; i++) {
                //System.out.print((char) content[i]);
                out.write(content[i]);
            }

        } else if (this.responseCode == 404) {
            response = NOT_FOUND_RESPONSE.getBytes();
            out.write(response);
        } else if (this.responseCode == 403) {
            response = FORBIDDEN_RESPONSE.getBytes();
            out.write(response);

        }

        out.flush();
        out.close();
    }

    /**
     * Find requested file
     * @param path - path from request
     * @return - the file object
     * @throws IOException throws IO exception
     */
    private byte[] getFile(String path) throws IOException {
        String mypath = null;
        File fileobject;
        if (path.equals("/")) {
            mypath = TinyWS.getDefaultFolder() + "/" + TinyWS.getDefaultPage();
        } else {
            mypath = TinyWS.getDefaultFolder() + path;
        }

        TinyWS.log(mypath);
        this.mimeType = getMimeType(mypath);
        fileobject = new File(mypath);

        return readFile(fileobject);
    }

    /**
     * @param f -Read file
     * @return - return byte array
     * @throws IOException throws IO exception
     */
    private byte[] readFile(File f) throws IOException {
        byte[] bFile = null;
        try {
            if (f.exists() && f.isFile()) {

                FileInputStream fileInputStream = null;
                bFile = new byte[(int) f.length()];
                fileInputStream = new FileInputStream(f);
                fileInputStream.read(bFile);
                fileInputStream.close();
            }
        } catch (IOException e) {
            TinyWS.fatalError(e);
        }
        return bFile;
    }

    /**
     * @param path - the path from the request
     * @return - Return mimetype based on file suffix (or null if error)
     */
    private String getMimeType(String path) {

        String mimeType = null;
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i >= 0) {
            extension = path.substring(i + 1);
        }
        TinyWS.log("File extention: " + extension);
        if (extension.equals("html")) {
            mimeType = "text/html\n\n";
        } else if (extension.equals("txt")) {
            mimeType = "text/plain\n\n";
        } else if (extension.equals("java")) {
            mimeType = "text/plain\n\n";
        } else if (extension.equals("gif")) {
            mimeType = "image/gif\n\n";
        } else if (extension.equals("jpg")) {
            mimeType = "image/jpeg\n\n";
        } else if (extension.equals("ico")) {
            mimeType = "image/png\n\n";
        }
        return mimeType;
    }

}
