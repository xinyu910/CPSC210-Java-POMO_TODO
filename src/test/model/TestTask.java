package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.Parser;
import parsers.TagParser;
import parsers.exceptions.ParsingException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {

    private Task task;
    private HashSet tags;
    private Priority priority;
    private Task t1;
    private Task t2;
    private Tag tag = new Tag("tag");


    @BeforeEach
    void testRunBefore() {
        t1 = new Task("t1");
        assertEquals(0, t1.getProgress());
        assertEquals(0, t1.getEstimatedTimeToComplete());
        assertEquals("t1",
                t1.getDescription());
        t1.setStatus(Status.DONE);
        t1.setPriority(new Priority(4));
        t1.setDueDate(new DueDate());
        try {
            task = new Task("Read collaboration policy of the term project");
            assertEquals("Read collaboration policy of the term project",
                    task.getDescription());
            assertEquals(null, task.getDueDate());
            assertEquals(Status.TODO, task.getStatus());
            assertFalse(task.getPriority().isUrgent());
            assertFalse(task.getPriority().isImportant());
        } catch (EmptyStringException e) {
            fail("Caught unexpected exception");
        }
        tags = new HashSet<>();
        priority = new Priority(1);

    }

    @Test
    void testConstructorExceptionEmpty() {
        try {
            task = new Task("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testConstructorExceptionNull() {
        try {
            task = new Task(null);
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
        }
    }


    @Test
    void testAddTag() {
        try {
            task.addTag("Hi");
            assertTrue(task.containsTag("Hi"));
            assertEquals(1, task.getTags().size());
            task.addTag("Hi");
            assertTrue(task.containsTag("Hi"));
            assertEquals(1, task.getTags().size());
        } catch (EmptyStringException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testAddTagExceptionEmpty() {
        try {
            task.addTag("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
        }
    }


    @Test
    void testAddMultipleTag() {
        try {
            task.addTag("Hi");
            assertTrue(task.containsTag("Hi"));
            assertEquals(1, task.getTags().size());
            task.addTag("Hey");
            assertTrue(task.containsTag("Hey"));
            assertEquals(2, task.getTags().size());
        } catch (EmptyStringException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testGetTags() {
        Tag tag = new Tag("Hi");
        Set<Tag> tags = new HashSet<>();
        task.addTag(tag);
        tags.add(tag);
        assertEquals(task.getTags(), tags);
    }

    @Test
    void testSetPriority() {
        try {
            task.setPriority(priority);
            assertEquals(priority, task.getPriority());
        } catch (NullArgumentException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testSetPriorityException() {
        try {
            task.setPriority(null);
            fail("Failed to throw EmptyStringException");
        } catch (NullArgumentException e) {

        }
    }

    @Test
    void testGetDueDate() {
        DueDate dueDate = new DueDate();
        task.setDueDate(dueDate);
        assertEquals(dueDate, task.getDueDate());
    }

    @Test
    void testRemoveTag() {
        task.addTag("Hi");
        assertTrue(task.containsTag("Hi"));
        assertEquals(1, task.getTags().size());
        task.addTag("Hey");
        assertTrue(task.containsTag("Hey"));
        assertEquals(2, task.getTags().size());
        task.removeTag("Hi");
        assertFalse(task.containsTag("Hi"));
        assertTrue(task.containsTag("Hey"));
        assertEquals(1, task.getTags().size());
    }

    @Test
    void testRemoveTagExceptionEmpty() {
        try {
            task.removeTag("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
        }
    }


    @Test
    void testSetStatus() {
        Status status = Status.TODO;
        try {
            task.setStatus(status);
            assertEquals(Status.TODO, task.getStatus());
        } catch (NullArgumentException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testSetStatusException() {
        try {
            task.setStatus(null);
            fail("failed to throw EmptyStringException");
        } catch (NullArgumentException e) {

        }
    }

    @Test
    void testToString() {
        try {
            task.setDescription("Read collaboration policy of the term project");
            DueDate dueDate = null;
            task.setDueDate(dueDate);
            Priority priority = new Priority(1);
            task.setPriority(priority);
            task.addTag("Hi");
            task.addTag("Hey");
            StringBuffer output = new StringBuffer();
            output.append("\n{");
            output.append("\n\tDescription: Read collaboration policy of the term project");
            output.append("\n\tDue date: ");
            output.append("\n\tStatus: TODO");
            output.append("\n\tPriority: IMPORTANT & URGENT");
            output.append("\n\tTags: #Hi, #Hey");
            output.append("\n}");
            assertEquals(output.toString(), task.toString());
        } catch (EmptyStringException e) {
            fail("Caught unexpected exception");
        }
    }

    @Test
    void testToString2() {
        try {
            task.setDescription("Read collaboration policy of the term project");
            DueDate dueDate = new DueDate();
            task.setDueDate(dueDate);
            Priority priority = new Priority(1);
            task.setPriority(priority);
            StringBuffer output = new StringBuffer();
            output.append("\n{");
            output.append("\n\tDescription: Read collaboration policy of the term project");
            output.append("\n\tDue date: " + dueDate);
            output.append("\n\tStatus: TODO");
            output.append("\n\tPriority: IMPORTANT & URGENT");
            output.append("\n\tTags:  ");
            output.append("\n}");
            assertEquals(output.toString(), task.toString());
        } catch (EmptyStringException e) {
            fail("Caught unexpected exception");
        }
    }


    @Test
    void testSetDescription() {
        try {
            task.setDescription("hi");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testSetDescription2() {
        try {
            task.setDescription("hi ## 33");
        } catch (EmptyStringException e) {
            fail();
        }
    }


    @Test
    void testSetDescriptionExceptionEmpty() {
        try {
            task.setDescription("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testSetDescriptionExceptionNull() {
        try {
            task.setDescription(null);
            fail("failed to throw EmptyStringException");
        } catch (
                EmptyStringException e) {
        }

    }

    @Test
    void testEqual() {
        t2 = new Task("t1");
        t2.setStatus(Status.DONE);
        t2.setPriority(new Priority(4));
        t2.setDueDate(new DueDate());
        assertTrue(t1.equals(t2));
        assertTrue(t1.hashCode() == t2.hashCode());
    }

    @Test
    void testEqualEqual() {
        assertTrue(t1.equals(t1));
        assertTrue(t1.hashCode() == t1.hashCode());
    }


    @Test
    void testNotEqualDescription() {
        t2 = new Task("t2");
        t2.setStatus(Status.DONE);
        t2.setPriority(new Priority(4));
        t2.setDueDate(new DueDate());
        assertFalse(t1.equals(t2));
        assertFalse(t1.hashCode() == t2.hashCode());
    }

    @Test
    void testNotEqualDate() {
        t2 = new Task("t1");
        t2.setStatus(Status.DONE);
        t2.setPriority(new Priority(4));
        DueDate dueDate = new DueDate();
        dueDate.postponeOneDay();
        t2.setDueDate(dueDate);
        assertFalse(t1.equals(t2));
        assertFalse(t1.hashCode() == t2.hashCode());
    }

    @Test
    void testNotEqualStatus() {
        t2 = new Task("t1");
        t2.setStatus(Status.UP_NEXT);
        t2.setPriority(new Priority(4));
        t2.setDueDate(new DueDate());
        assertFalse(t1.equals(t2));
        assertFalse(t1.hashCode() == t2.hashCode());
    }

    @Test
    void testNotEqualPriority() {
        t2 = new Task("t1");
        t2.setStatus(Status.UP_NEXT);
        t2.setPriority(new Priority(3));
        t2.setDueDate(new DueDate());
        assertFalse(t1.equals(t2));
        assertFalse(t1.hashCode() == t2.hashCode());
    }

    @Test
    void testNotEqualNull() {
        t2 = null;
        assertFalse(t1.equals(t2));
    }

    @Test
    void testAddTagException() {
        try {
            tag = null;
            t1.addTag(tag);
            fail();
        } catch (NullArgumentException e) {
        }
    }

    @Test
    void testAddTagNoException() {
        try {
            t1.addTag(tag);
            assertTrue(t1.containsTag(tag));
            assertTrue(t1.containsTag("tag"));
            assertTrue(tag.containsTask(t1));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    void testAddTagNoExceptionAlreadyThere() {
        try {
            t1.addTag(tag);
            assertTrue(t1.containsTag(tag));
            assertTrue(t1.containsTag("tag"));
            assertTrue(tag.containsTask(t1));
            t1.addTag(tag);
            assertTrue(t1.containsTag(tag));
            assertTrue(t1.containsTag("tag"));
            assertTrue(tag.containsTask(t1));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    void testRemoveTagException() {
        try {
            tag = null;
            t1.removeTag(tag);
            fail();
        } catch (NullArgumentException e) {
        }
    }

    @Test
    void testRemoveTaskNoException() {
        try {
            t1.removeTag(tag);
            assertFalse(t1.containsTag(tag));
            assertFalse(t1.containsTag("tag"));
            assertFalse(tag.containsTask(t1));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    void testRemoveTaskNoExceptionAlreadyThere() {
        try {
            t1.addTag(tag);
            assertTrue(t1.containsTag(tag));
            assertTrue(t1.containsTag("tag"));
            assertTrue(tag.containsTask(t1));
            t1.removeTag(tag);
            assertFalse(t1.containsTag(tag));
            assertFalse(t1.containsTag("tag"));
            assertFalse(tag.containsTask(t1));
        } catch (NullArgumentException e) {
            fail();
        }
    }


    @Test
    void testAddTagStringException() {
        try {
            t1.addTag("");
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testAddTagStringException2() {
        try {
            String tagName = null;
            t1.addTag(tagName);
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testRemoveTagStringException2() {
        try {
            String tagName = null;
            t1.removeTag(tagName);
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testAddTagString() {
        try {
            t1.addTag("tag");
            assertTrue(t1.containsTag(tag));
            assertTrue(t1.containsTag("tag"));
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testRemoveTagString() {
        try {
            t1.addTag("tag");
            assertTrue(t1.containsTag(tag));
            assertTrue(t1.containsTag("tag"));
            t1.removeTag("tag");
            assertFalse(t1.containsTag(tag));
            assertFalse(t1.containsTag("tag"));
        } catch (EmptyStringException e) {
            fail();

        }
    }

    @Test
    void testContainsTag() {
        t1.addTag(tag);
        try {
            assertTrue(t1.containsTag(tag));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    void testContainsTagNull() {
        Tag tag = null;
        try {
            assertTrue(t1.containsTag(tag));
            fail();
        } catch (NullArgumentException e) {
        }
    }

    @Test
    void testContainsTagString() {
        t1.addTag("tag");
        try {
            assertTrue(t1.containsTag("tag"));
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testContainsTagNullString() {
        String tag = null;
        try {
            assertTrue(t1.containsTag(tag));
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testContainsTagEmptyString() {
        try {
            assertTrue(t1.containsTag(""));
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    void testTask1() {
        Parser parser = new TagParser();
        task = new Task("Register course.");
        try {
            parser.parse("Register course. ## cpsc210; tomorrow; important; urgent; in progress", task);
            assertEquals(parser.getDescription().trim(), task.getDescription().trim());
            assertEquals("Register course.", parser.getDescription().trim());
        } catch (ParsingException e) {
            fail("unexpected exception");
        }
    }

    @Test
    void testSetProgress() {
        try {
            task.setProgress(80);
            assertEquals(80, task.getProgress());
        } catch (InvalidProgressException e) {
            fail();
        }
    }

    @Test
    void testSetProgressZero() {
        try {
            task.setProgress(0);
            assertEquals(0, task.getProgress());
        } catch (InvalidProgressException e) {
            fail();
        }
    }

    @Test
    void testSetProgressHundred() {
        try {
            task.setProgress(100);
            assertEquals(100, task.getProgress());
        } catch (InvalidProgressException e) {
            fail();
        }
    }


    @Test
    void testSetProgressLess() {
        try {
            task.setProgress(-3);
            fail();
        } catch (InvalidProgressException e) {
        }
    }

    @Test
    void testSetProgressMore() {
        try {
            task.setProgress(167);
            fail();
        } catch (InvalidProgressException e) {
        }
    }

    @Test
    void testSetEstimatedTimeToComplete() {
        try {
            task.setEstimatedTimeToComplete(10);
            assertEquals(10, task.getEstimatedTimeToComplete());
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    void testSetEstimatedTimeToCompleteLess() {
        try {
            task.setEstimatedTimeToComplete(-3);
            fail();
        } catch (NegativeInputException e) {
        }
    }

}

