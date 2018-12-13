package cscc.edu;

import java.io.*;
import java.net.*;

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
    private byte[] content = null;
    private String mimeType = null;

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

    // Find requested file, assume Document Root is in html folder in project directory
    private byte[] getFile(String path) throws IOException {
        String mypath = null;
        File fileobject;
        if (path.equals("/")) {
            mypath = TinyWS.getDefaultFolder() + "/" + TinyWS.getDefaultPage();
        } else {
            mypath = TinyWS.getDefaultFolder() + path;
        }
        //System.out.println(mypath);
        TinyWS.log(mypath);
        this.mimeType = getMimeType(mypath);
        fileobject = new File(mypath);

        return readFile(fileobject);
    }

    // Read file, return byte array (null if error)
    private byte[] readFile(File f) throws IOException {
        byte[] bFile = null;
        try {
            if (f.exists() && f.isFile()) {

                //fileContent = Files.readAllBytes(f.toPath());
                //System.out.println(fileContent.toString());

                FileInputStream fileInputStream = null;
                bFile = new byte[(int) f.length()];
                fileInputStream = new FileInputStream(f);
                fileInputStream.read(bFile);
                fileInputStream.close();
                //for (int i = 0; i < bFile.length; i++)
                //{
                // System.out.print((char) bFile[i]);
                //}
            }
        } catch (FileNotFoundException e) {
            TinyWS.fatalError(e);

        } catch (IOException e) {
            TinyWS.fatalError(e);
        }
        return bFile;
    }

    // Return mimetype based on file suffix (or null if error)
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
