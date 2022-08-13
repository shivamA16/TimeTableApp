package model;

import exceptions.InvalidTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TTableTest {
    TTable table;
    Subject one;
    Subject two;

    @BeforeEach
    void setup() {
        table = new TTable("main");
        one = new Subject("one");
        two = new Subject("two");
    }

    @Test
    void removeSubjectWithTitleTest() {
        assertFalse(table.removeSubjectWithTitle("one"));
        assertFalse(table.removeSubjectWithTitle("three"));

        table.addSubject(one);
        assertTrue(table.removeSubjectWithTitle("one"));
        assertFalse(table.removeSubjectWithTitle("two"));
        assertFalse(table.removeSubjectWithTitle("three"));

        table.addSubject(one);
        table.addSubject(two);
        assertTrue(table.removeSubjectWithTitle("two"));
        assertTrue(table.removeSubjectWithTitle("one"));
    }

    @Test
    void addSubjectTest() {
        try {
            one.setTime("Monday", 5, 6);
            assertTrue(table.addSubject(one));
            assertTrue(table.addSubject(two));

            table.removeSubjectWithTitle("one");
            table.removeSubjectWithTitle("two");

            two.setTime("Monday", 5, 6);
            assertTrue(table.addSubject(one));
            assertFalse(table.addSubject(two));
        } catch (InvalidTimeException e) {
            fail("Should not have caught exception");
        }

        try {
            two.setTime("Monday", 4, 6);
            assertFalse(table.addSubject(two));
            two.setTime("Monday", 4, 5.5);
            assertFalse(table.addSubject(two));
            two.setTime("Monday", 5.5, 7);
            assertFalse(table.addSubject(two));
        } catch (InvalidTimeException e) {
            fail("Should not have caught exception");
        }

    }

    @Test
    void sortDayTimeTest() {
        Subject three = new Subject("three");

        try {
            three.setTime("Tuesday", 6, 7);
            one.setTime("Tuesday", 5, 6);
            two.setTime("Tuesday", 7, 8);
        } catch (InvalidTimeException e) {
            fail("Should not have caught exception");
        }

        table.addSubject(one);
        table.addSubject(two);
        table.addSubject(three);

        ArrayList<Subject> myList = new ArrayList<>();
        myList.add(one);
        myList.add(three);
        myList.add(two);

        assertEquals(myList, table.sortDayTime("Tuesday"));

        table = new TTable("test");
        assertEquals(Collections.emptyList(), table.sortDayTime("Friday"));

        try {
            one.setTime("Monday", 0, 0);
            table.addSubject(one);
            assertEquals(Collections.emptyList(), table.sortDayTime("Monday"));
        } catch (InvalidTimeException e) {
            fail("Should not have caught exception");
        }


        table = new TTable("alsoTest");
        try {
            one.setTime("Tuesday", 0, 2);
            two.setTime("Tuesday", 2, 3);
            table.addSubject(two);
            table.addSubject(one);
            myList = new ArrayList<>();
            myList.add(one);
            myList.add(two);
            assertEquals(myList, table.sortDayTime("Tuesday"));
        } catch (InvalidTimeException e) {
            fail("Should not have caught exception");
        }
    }

    @Test
    void getSubjectsTest() {
        table.addSubject(one);
        table.addSubject(two);
        ArrayList<Subject> myList = new ArrayList<>();
        myList.add(one);
        myList.add(two);
        assertEquals(myList, table.getSubjects());
    }

    @Test
    void findSubjectByStartTimeTest() {
        assertEquals(null, table.findSubjectByStartTime(5, "Friday"));
        try {
            one.setTime("Wednesday", 5, 6);
        } catch (InvalidTimeException e) {
            fail("Should not have caught exception");
        }
        table.addSubject(one);
        assertEquals(one, table.findSubjectByStartTime(5, "Wednesday"));
    }
}
