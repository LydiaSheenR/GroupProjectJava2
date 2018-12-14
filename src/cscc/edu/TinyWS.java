package cscc.edu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TinyWS a simplistic Tiny Web Server
 * @author Lydiasheen Rhymond
 * @author Visalakshi(Vidya) Rajesh
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

    /**
     * Constructor
     *
     * @throws IOException - throws IO exception
     */
    public TinyWS() throws IOException {
        Config config = new Config();
        port = Integer.parseInt(config.getProperty(Config.PORT));
        defaultFolder = config.getProperty(Config.DEFAULTFOLDER);
        defaultPage = config.getProperty(Config.DEFAULTPAGE);
    }

    /**
     * Listen Method
     * @throws IOException - throws IO exception
     */
    public void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(0);
        log(serverSocket.getInetAddress() + " connected to server.\n");
        while (true) {
            try {
                Socket connection = serverSocket.accept();
                log(connection.getInetAddress().getCanonicalHostName());
                RequestHandler requesthandler = new RequestHandler(connection);
                requesthandler.processRequest();
                connection.close();

            } catch (IOException e) {
                throw new RuntimeException("Some Error" + port, e);
            }
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
     * @param s - message to log
     */
    public static void fatalError(String s) {
        handleError(s, null, true);
    }

    /**
     * Handle fatal error - print info and die
     * @param e - message to log exception
     */
    public static void fatalError(Exception e) {
        handleError(null, e, true);
    }

    /**
     * Handle fatal / non-fatal errors
     * @param e - message to log exception
     * @param s - message to log
     * @param isFatal - message to log fatal error
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
