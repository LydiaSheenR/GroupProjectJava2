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
     * @param r
     */
    public HTTPRequest(String r) {
       this.request = r;
       this.validRequest = isValidRequest();
       this.path = getPath();
    }

    /**
     * checks if the request is valid
     * @return
     */
    public boolean isValidRequest() {
        return (validRequest);
    }

    /**
     * returns just the path from the request
     * @return
     */
    public String getPath() {
        return (path);
    }

    /**
     * parse the request string and set the path and checks if valid
     * @param r
     * @return
     */
    private boolean parse(String r) {
        String arrayR[] = r.split("[\\t\\n]");
        if ((arrayR[0].equals("GET")) && (arrayR[1] != null)){
            this.path = arrayR[1];
            validRequest = true;
            return true;
        }
        validRequest = false;
        return false;
    }
}
