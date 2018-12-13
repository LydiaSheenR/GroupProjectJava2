package cscc.edu;

/**
 * class to handle the request path
 */
public class HTTPRequest {
    private String request;         // request string
    private String path;            // path to file
    private boolean validRequest;   // is request valid?

    /**
     * instantiating the HTTPRequest class
     * @param request
     */
    public HTTPRequest(String request) {
       this.request = request;
       this.validRequest = parse(request);
       this.path = getPath();

    }

    /**
     * checks if the request is valid
     * @return validRequest
     */
    public boolean isValidRequest() {
        return (validRequest);
    }

    /**
     * returns just the path from the request
     * @return path
     */
    public String getPath() {
        return (path);
    }

    /**
     * parse the request string and set the path and checks if valid
     * @param r
     * @return true or false
     */
    private boolean parse(String r) {
        String arrayR[] = r.split("[ \t\n?]");
        //String arrayR[] = r.split(" ");
        // int tokens = arrayR.length;
        if ((arrayR[0].equalsIgnoreCase("GET")) && (arrayR[1] != null)) {
            this.path = arrayR[1];
            //validRequest = true;
            return true;
        }
        //validRequest = false;
        return false;
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
