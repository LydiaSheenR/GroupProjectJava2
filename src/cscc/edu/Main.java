package cscc.edu;

public class Main {

    public static void main(String[] args) {

        try {
            Config config = new Config();
            config.readProperties();
            config.dumpProperties();
            //System.out.println(config.getProperty("port"));
        } catch (Exception e){
            System.out.println("trace: " + e.getStackTrace());
            System.out.println("cause: " + e.getCause());
            System.out.println("message: " + e.getMessage());
        }
    }
}
