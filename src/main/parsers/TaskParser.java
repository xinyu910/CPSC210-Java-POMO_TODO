package parsers;

import model.DueDate;
import model.Priority;
import model.Status;
import model.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        JSONArray array = new JSONArray(input);
        List<Task> tasks = new ArrayList<>();
        for (Object object : array) {
            JSONObject taskJson = (JSONObject) object;
            try {
                Task task = setUp(taskJson);
                tasks.add(task);
            } catch (JSONException e) {
                continue;
            }
        }
        return tasks;
    }

    private Task setUp(JSONObject taskJson) {
        Task task = new Task("t1");
        description(taskJson, task);
        status(taskJson, task);
        tag(taskJson, task);
        priority(taskJson, task);
        dueDate(taskJson, task);
        return task;
    }


    private void description(JSONObject taskJson, Task task) {
        task.setDescription(taskJson.getString("description"));
    }

    private void status(JSONObject taskJson, Task task) {
        if (taskJson.getString("status").equals("TODO")) {
            task.setStatus(Status.TODO);
        } else if (taskJson.getString("status").equals("UP_NEXT")) {
            task.setStatus(Status.UP_NEXT);
        } else if (taskJson.getString("status").equals("IN_PROGRESS")) {
            task.setStatus(Status.IN_PROGRESS);
        } else {
            task.setStatus(Status.DONE);
        }
    }

    private void tag(JSONObject taskJson, Task task) {
        JSONArray tags = taskJson.getJSONArray("tags");
        for (int i = 0; i < tags.length(); i++) {
            task.addTag(tags.getJSONObject(i).getString("name"));
        }

    }

    private void dueDate(JSONObject taskJson, Task task) {
        if (taskJson.get("due-date") == JSONObject.NULL) {
            task.setDueDate(null);
        } else {
            JSONObject dueDate = taskJson.getJSONObject("due-date");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, dueDate.getInt("year"));
            calendar.set(Calendar.MONTH, dueDate.getInt("month"));
            calendar.set(Calendar.DAY_OF_MONTH, dueDate.getInt("day"));
            calendar.set(Calendar.HOUR_OF_DAY, dueDate.getInt("hour"));
            calendar.set(Calendar.MINUTE, dueDate.getInt("minute"));
            DueDate d1 = new DueDate(calendar.getTime());
            task.setDueDate(d1);
        }
    }


    private void priority(JSONObject taskJson, Task task) {
        JSONObject priority = taskJson.getJSONObject("priority");
        if (priority.getBoolean("important") && priority.getBoolean("urgent")) {
            task.setPriority(new Priority(1));
        } else if (priority.getBoolean("important")) {
            task.setPriority(new Priority(2));
        } else if (priority.getBoolean("urgent")) {
            task.setPriority(new Priority(3));
        } else {
            task.setPriority(new Priority(4));
        }
    }

}


    
    

