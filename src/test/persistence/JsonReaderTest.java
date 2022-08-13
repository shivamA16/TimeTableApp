package persistence;


import exceptions.InvalidTimeException;
import model.Subject;
import model.TTable;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


// Note - Much of this code was adapted from CPSC 210's JsonSerializationDemo project
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TTable tt = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (InvalidTimeException invalidTimeException) {
            fail("Should not have caught InvalidTimeException");
        }
    }

    @Test
    void testReaderEmptyTimeTable() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTimeTable.json");
        try {
            TTable tt = reader.read();
            assertEquals("emptyTable", tt.getName());
            assertEquals(0, tt.getSubjects().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidTimeException invalidTimeException) {
            fail("Should not have caught InvalidTimeException");
        }
    }

    @Test
    void testReaderGeneralTimeTable() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTimeTable.json");
        try {
            TTable tt = reader.read();
            assertEquals("myTable", tt.getName());
            List<Subject> subjects = tt.getSubjects();
            assertEquals(2, subjects.size());
            ArrayList<Integer> times = new ArrayList<>();
            checkSubject("econ", subjects.get(0));
            checkSubject("cpsc", subjects.get(1));
            assertEquals(18, subjects.get(1).getEnd("Friday"));
            assertEquals(19, subjects.get(1).getEnd("Monday"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidTimeException invalidTimeException) {
            fail("Should not have caught InvalidTimeException");
        }
    }


    @Test
    void testReaderTimingWrong() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTimeTable2.json");
        try {
            TTable tt = reader.read();
            List<Subject> subjects = tt.getSubjects();
            fail("Should have caught InvalidTImeException");

        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (InvalidTimeException invalidTimeException) {
            //
        }
    }
}