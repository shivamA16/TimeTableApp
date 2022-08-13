package model;

import exceptions.InvalidTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SubjectTest {
    Subject one;
    Subject two;
    TTable table;

    @BeforeEach
    void setup() {
        table = new TTable("table");
        one = new Subject("one");
        two = new Subject("two");
    }



    @Test
    void getAndSetStartAndEndTest() {
        try {
            one.setTime("Thursday", 5, 6);
            assertEquals(5, one.getStart("Thursday"));
            assertEquals(-1, one.getStart("thurs"));
            two.setTime("Friday", 5, 9);
            assertEquals(9, two.getEnd("Friday"));
            assertEquals(-1, two.getEnd("fri"));
        } catch (InvalidTimeException e) {
            fail("Should not have caught exception");
        }
        try {
            two.setTime("Fridayy", 5, 10);
            assertEquals(9, two.getEnd("Friday"));
        } catch (InvalidTimeException e) {
            fail("Should not have caught exception");
        }
        try {
            two.setTime("Monday", 7, 6);
            fail("Should have caught InvalidTimeException");
        } catch (InvalidTimeException e) {
            //
        }
    }



}
