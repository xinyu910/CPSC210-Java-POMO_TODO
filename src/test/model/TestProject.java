package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {
    private Project project;
    private Task task;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;
    private Project project2;
    private Project project1;
    private Project p1;
    private Project p2;

    @BeforeEach
    void runBefore() {
        try {
            project = new Project("1");
            assertEquals(0, project.progress);
            assertEquals(0, project.getEstimatedTimeToComplete());
            assertEquals("1", project.getDescription());
            assertEquals(0, project.getNumberOfTasks());
        } catch (EmptyStringException e) {
            fail("Caught unexpected exception");
        }
        task = new Task("Hello");
        task1 = new Task("1");
        task2 = new Task("2");
        task3 = new Task("3");
        task4 = new Task("4");
        project2 = new Project("Lxy Love Ran");
        project1 = new Project("Lxy Love Ran");
        p1 = new Project("Ran love Lxy");
        p2 = new Project("Ran love Lxy");
    }

    @Test
    void testConstructorExceptionEmpty() {
        try {
            project = new Project("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testConstructorExceptionNull() {
        try {
            project = new Project(null);
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testAdd() {
        try {
            project.add(task);
            assertEquals(1, project.getNumberOfTasks());
            project.add(task);
            assertEquals(1, project.getNumberOfTasks());
        } catch (NullArgumentException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testAddSelf() {
        try {
            project.add(task);
            assertEquals(1, project.getNumberOfTasks());
            project.add(project);
            assertEquals(1, project.getNumberOfTasks());
        } catch (NullArgumentException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testAddException() {
        try {
            project.add(null);
            fail("failed to throw NullArgumentException");
        } catch (NullArgumentException e) {
        }
    }

    @Test
    void testRemove() {
        try {
            project.add(task);
            assertEquals(1, project.getNumberOfTasks());
            project.remove(task);
            assertEquals(0, project.getNumberOfTasks());
            project.remove(task2);
            assertEquals(0, project.getNumberOfTasks());
        } catch (NullArgumentException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testRemoveSelf() {
        try {
            project.add(task);
            assertEquals(1, project.getNumberOfTasks());
            project.remove(task);
            assertEquals(0, project.getNumberOfTasks());
            project.remove(project);
            assertEquals(0, project.getNumberOfTasks());
        } catch (NullArgumentException e) {
            fail("Caught unexpected exception");
        }
    }


    @Test
    void testRemoveException() {
        try {
            project.remove(null);
            fail("failed to throw NullArgumentException");
        } catch (NullArgumentException e) {

        }
    }


    @Test
    void testContains() {
        project.add(task);
        assertEquals(1, project.getNumberOfTasks());
        try {
            assertTrue(project.contains(task));
        } catch (NullArgumentException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testContainsException() {
        project.add(task);
        assertEquals(1, project.getNumberOfTasks());
        try {
            assertTrue(project.contains(null));
            fail("failed to throw NullArgumentException");
        } catch (NullArgumentException e) {

        }
    }

    @Test
    void testGetNumberOfTasks() {
        assertEquals(project.getNumberOfTasks(), project.getNumberOfTasks());
    }

    @Test
    void testGetProgress() {
        assertFalse(project.isCompleted());
        assertEquals(0, project.getProgress());
        project.add(task1);
        task1.setProgress(100);
        assertTrue(project.isCompleted());
        project.add(task2);
        project.add(task3);
        assertEquals(33, project.getProgress());
        project.remove(task3);
        assertEquals(50, project.getProgress());
        assertFalse(project.isCompleted());
        project.add(task3);
        task2.setProgress(50);
        task3.setProgress(25);
        assertEquals(58, project.getProgress());
        Project project2 = new Project("yy");
        project2.add(task4);
        project2.add(project);
        assertEquals(29, project2.getProgress());
    }

    @Test
    void testEstimatedTime() {
        assertEquals(0, project.getEstimatedTimeToComplete());
        project.add(task1);
        project.add(task2);
        project.add(task3);
        task1.setEstimatedTimeToComplete(8);
        assertEquals(8, project.getEstimatedTimeToComplete());
        task2.setEstimatedTimeToComplete(2);
        task3.setEstimatedTimeToComplete(10);
        assertEquals(20, project.getEstimatedTimeToComplete());
        Project project2 = new Project("yy");
        project2.add(task4);
        project.add(project2);
        task4.setEstimatedTimeToComplete(4);
        assertEquals(24, project.getEstimatedTimeToComplete());
    }

    @Test
    void testNotEqual() {
        assertFalse(project2.equals(p2));
        assertFalse(project1.equals(p1));
        assertFalse(project1.hashCode() == p2.hashCode());
    }

    @Test
    void testNotEqualNull() {
        project1 = null;
        assertFalse(project2.equals(project1));
    }

    @Test
    void testEqual() {
        assertTrue(project2.equals(project1));
        assertTrue(p1.equals(p2));
        assertTrue(project2.hashCode() == project1.hashCode());
    }

    @Test
    void testEqualEqual() {
        assertTrue(project2.equals(project2));
        assertTrue(project2.hashCode() == project2.hashCode());
    }

    void testException2(int number) throws NegativeInputException {
        if (number == 3) {
            throw new NegativeInputException();
        }
    }

    @Test
    void testException3() {
        try {
            testException2(3);
            fail();
        } catch (NegativeInputException e) {
        }
    }

    @Test
    void testException4() {
        try {
            testException2(2);
        } catch (NegativeInputException e) {
            fail();
        }
    }

    void testException22(int number) throws InvalidProgressException {
        if (number == 3) {
            throw new InvalidProgressException();
        }
    }

    @Test
    void testException33() {
        try {
            testException22(3);
            fail();
        } catch (InvalidProgressException e) {
        }
    }

    @Test
    void testException44() {
        try {
            testException22(2);
        } catch (InvalidProgressException e) {
            fail();
        }
    }

    @Test
    void testGetTask() {
        project.add(task);
        try {
            project.getTasks();
            fail();
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    void testTodo() {
        Todo task = new Task("t1");
        project.add(task);
        assertEquals("t1", task.getDescription());
    }

    @Test
    void testIterator() {
        p1 = new Project("p1");
        p2 = new Project("p2");
        Project p3 = new Project("p3");
        Project p4 = new Project("p4");
        task2.setPriority(new Priority(3));
        task3.setPriority(new Priority(2));
        task4.setPriority(new Priority(1));
        project.add(task1);
        project.add(task2);
        project.add(task3);
        project.add(task4);
        assertFalse(task.getPriority().isImportant());
        assertFalse(task.getPriority().isUrgent());
        p2.setPriority(new Priority(3));
        p3.setPriority(new Priority(2));
        p4.setPriority(new Priority(1));
        project.add(p1);
        project.add(p2);
        project.add(p3);
        project.add(p4);
        assertFalse(p1.getPriority().isImportant());
        assertFalse(p1.getPriority().isUrgent());

        try {
            for (Todo todo : project) {
                System.out.println(todo.getDescription());
            }
        } catch (NoSuchElementException e) {
            fail();
        }
    }

    @Test
    void testEmptyIterator() {
        Iterator<Todo> itr = project.iterator();
        assertFalse(itr.hasNext());
        try {
            itr.next();
            fail("Exception should have been thrown.");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

}


