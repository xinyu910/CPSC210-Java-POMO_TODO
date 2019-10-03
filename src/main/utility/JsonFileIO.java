package utility;


import model.Task;
import org.json.simple.parser.ParseException;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.util.List;
import org.json.simple.parser.JSONParser;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");


    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    /*public static List<Task> read() {
        TaskParser tp = new TaskParser();
        String content = null;
        try {
            Scanner sc = new Scanner(jsonDataFile);
            sc.useDelimiter("\\Z");
            content = sc.next();
        } catch (IOException e) {
            System.out.println("error");
        }
        return (tp.parse(content));
    }*/

    public static List<Task> read() {
        TaskParser tp = new TaskParser();
        JSONParser jsonParser = new JSONParser();
        String input = null;

        try {
            FileReader reader = new FileReader(jsonDataFile);
            Object obj = jsonParser.parse(reader);
            input = obj.toString();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (tp.parse(input));
    }


    // EFFECTS: saves the tasks to jsonDataFile
   /* public static void write(List<Task> tasks) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        helperWrite(fw, bw, tasks);
    }

    private static void helperWrite(FileWriter fw, BufferedWriter bw, List<Task> tasks) {
        try {
            fw = new FileWriter(jsonDataFile, false);
            bw = new BufferedWriter(fw);
            bw.write(Jsonifier.taskListToJson(tasks).toString());
        } catch (IOException e) {
            System.out.println("error");
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
*/
    public static void write(List<Task> tasks) {
        try (FileWriter file = new FileWriter(jsonDataFile)) {

            file.write(Jsonifier.taskListToJson(tasks).toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


