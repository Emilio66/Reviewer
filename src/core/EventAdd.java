package core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaoz on 2017/5/1 0001.
 */
public class EventAdd {

    public static void main(String args[]){
        //load configuration file
        Properties properties = loadProperties();

        //add event until 'OK' is typed in
        add(properties);

    }

    public static Properties loadProperties(){
        InputStream is = new EventAdd().getClass().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            System.err.println("IOException: "+e.getMessage()+", when load file: "+"config.properties");
        }

        return properties;
    }

    private static void add(Properties properties) {
        String eventDir = properties.getProperty("path");
        if(eventDir != null){
            File file = new File(eventDir);
            if(!file.exists()){
                if(!file.mkdirs())
                    System.err.println("Couldn't Create Directory: "+eventDir);
            }

            Scanner sc = new Scanner(System.in);
            StringBuilder content = new StringBuilder();
            //take in event until terminal symbol occur
            while(true){
                String input = sc.nextLine();
                if(!input.equalsIgnoreCase("q") && !input.equalsIgnoreCase("OK")){
                    content.append(input+"\r\n-------------------------------------\r\n");
                }else
                    break;
            }

            if(content.length() < 2) {
                System.out.println("NOTHING ADD");
                return;
            }

            //writing to file
            String delta = properties.getProperty("delta");
            if(delta != null){
                String[] periods = delta.split(",");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                for(String period : periods){
                    Calendar calendar = Calendar.getInstance();
                    Integer day = Integer.parseInt(period);
                    calendar.add(Calendar.DATE, day);   //add delta
                    String filename = format.format(calendar.getTime());
                    filename = eventDir + File.separator + filename+".log";
                    FileWriter writer = null;
                    try {
                        File eventFile = new File(filename);
                        //true stands for appending
                        writer = new FileWriter(eventFile, true);
                        writer.write(content.toString());

                    } catch (IOException e) {
                        System.err.println("IOException: " + e.getMessage() + ", when Writing file: " + filename);
                    }finally {
                        try {
                            if(writer !=null)
                                writer.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                    }
                }

                System.out.println("-------------------------------------\n"+content.toString()+"\nWriting Events Complete!");

            }else{
                System.err.println("No REVIEW DELTA defined in configuration file!");
            }

        }else{
            System.err.println("No PATH defined in configuration file!");
        }
    }
}
