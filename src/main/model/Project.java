package model;

import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo>, Observer {
    private String description;
    private List<Todo> tasks;
    HashMap<Todo, Integer> toDos;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        this.description = description;
        tasks = new ArrayList<>();
        toDos = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (task == null) {
            throw new NullArgumentException("");
        }
        if (!contains(task) && task != this) {
            tasks.add(task);
            task.addObserver(this);
            setChanged();
            task.notifyObservers(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (task == null) {
            throw new NullArgumentException("");
        }
        if (contains(task)) {
            tasks.remove(task);
            task.deleteObserver(this);
            toDos.remove(task);
            task.notifyObservers(task);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        return etcHours;
    }


    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
//     the percentage of completion (rounded down to the nearest integer).
//     the value returned is the average of the percentage of completion of
//     all the tasks and sub-projects in this project.
    public int getProgress() {
        if (tasks.size() == 0) {
            return 0;
        }
        return helper();
    }

    private int helper() {
        int sum = 0;
        int taskCount = 0;
        int project = 1;
        int subProject = 0;
        for (Todo todo : tasks) {
            if (todo instanceof Task) {
                sum = sum + todo.getProgress();
                taskCount++;
            } else {
                project++;
                subProject = todo.getProgress();
            }
        }
        return (int) Math.floor((((int) Math.floor(sum / taskCount) + subProject) / project));
    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new TodoIterator();
    }

    @Override
    public void update(Observable o, Object arg) {
        int sum = 0;
        Todo todo = (Todo) o;
        toDos.put(todo, todo.getEstimatedTimeToComplete());
        for (Map.Entry<Todo, Integer> entry : toDos.entrySet()) {
            sum += entry.getValue();
        }
        etcHours = sum;
        setChanged();
        notifyObservers(o);
    }

    private class TodoIterator implements Iterator<Todo> {
        int index = 0;
        Priority priority = new Priority(1);
        int num = 1;
        int element = 0;


        @Override
        public boolean hasNext() {
            return (element < tasks.size() && num < 5 && index < tasks.size());
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Todo next = getTodo();
            index++;
            element++;
            if (!hasNext()) {
                num++;
                index = 0;
            }
            priority();
            return next;
        }

        public void priority() {
            if (num <= 4) {
                priority = new Priority(num);
            }
        }

        private Todo getTodo() {
            while (hasNext()) {
                if (tasks.get(index).getPriority().equals(priority)) {
                    return tasks.get(index);
                } else {
                    index++;
                }
            }
            num++;
            index = 0;
            priority();
            return getTodo();
        }
    }
}
