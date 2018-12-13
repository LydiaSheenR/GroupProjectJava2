package cscc.edu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TinyWS a simplistic Tiny Web Server
 * @author Lydiasheen Rhymond & Visalakshi(Vidya) Rajesh
 */
public class TinyWS {

    private static int port;
    private static String defaultFolder;
    private static String defaultPage;

    public static void main(String[] args) throws IOException {
        TinyWS tiny = new TinyWS();
        tiny.listen();
    }

    public static void setPort(int port) {
        TinyWS.port = port;
    }

    public static void setDefaultFolder(String defaultFolder) {
        TinyWS.defaultFolder = defaultFolder;
    }

    public static void setDefaultPage(String defaultPage) {
        TinyWS.defaultPage = defaultPage;
    }

    public TinyWS() throws IOException {

        Config conf = new Config();
        port = Integer.parseInt(conf.getProperty(Config.PORT));
        defaultFolder = conf.getProperty(Config.DEFAULTFOLDER);
        defaultPage = conf.getProperty(Config.DEFAULTPAGE);
    }

    public void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);


        log(serverSocket.getInetAddress() + " connected to server.\n");

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                try {

                    InetAddress client = clientSocket.getInetAddress();
                    log(client.getHostName() + " connected to client.\n");
                    RequestHandler requesthandler = new RequestHandler(clientSocket);
                    HTTPRequest httprequest = new HTTPRequest(requesthandler.processRequest());
                    log(httprequest.toString());
                    log(httprequest.getPath());
                    ResponseHandler reqh = new ResponseHandler(httprequest);

                    reqh.sendResponse(clientSocket);


                } finally {
                    clientSocket.close();
                }
            }
        } finally {
            serverSocket.close();
        }

    }

    /**
     * Log web server requests
     * @param s - message to log
     */
    public static void log(String s) {
        System.out.println(s);
    }

    /**
     * Handle fatal error - print info and die
     */
    public static void fatalError(String s) {
        handleError(s, null, true);
    }

    /**
     * Handle fatal error - print info and die
     */
    public static void fatalError(Exception e) {
        handleError(null, e, true);
    }

    /**
     * Handle fatal / non-fatal errors
     */
    public static void handleError(String s, Exception e, boolean isFatal) {
        if (s != null) {
            System.out.println(s);
        }
        if (e != null) {
            e.printStackTrace();
        }
        if (isFatal) System.exit(-1);
    }

    /**
     * Get port configuration value
     * @return port number
     */
    public static int getPort() {
        return port;
    }

    /**
     * Get default HTML folder
     * @return folder
     */
    public static String getDefaultFolder() {
        return defaultFolder;
    }

    /**
     * Get name of default web page
     * @return default page
     */
    public static String getDefaultPage() {
        return defaultPage;
    }
}
