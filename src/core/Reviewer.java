package core;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaoz on 2017/5/1 0001.
 */
public class Reviewer {
    //get today's reveiw content & display
    public static void main(String [] args){
        Properties properties = EventAdd.loadProperties();
        String dir = properties.getProperty("path");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String filename = format.format(Calendar.getInstance().getTime());
        filename = dir + File.separator + filename+".log";
        File file = new File(filename);
        if(file.exists()){
            try {
                System.out.println("TODAY'S REVIEW TASKS: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String output;
                List<String> tasks = new ArrayList<String>();
                int cnt = 0;
                while((output = reader.readLine()) != null){
                    System.out.println(output);
                    //skip the split line
                    if((cnt & 1) == 0)
                        tasks.add(output);
                    cnt++;
                }
                Scanner sc = new Scanner(System.in);
                cnt = 0;
                while(true) {
                    if(cnt == tasks.size())
                        break;
                    String answer = sc.nextLine();
                    if(answer.equalsIgnoreCase("ok")){
                        System.out.println("Congrats! Finish TASK: "+cnt+". "+tasks.get(cnt));
                        cnt++;
                    }else{
                        System.out.println("Come on, it doesn't take very long: "+tasks.get(cnt));
                    }
                }

                System.out.println("YOU'RE AMAZING!!! TODAY'S REVIEW TASKS DONE:)");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("NOTHING TO REVIEW, TAKE A BREAK:)");
        }
    }
}
