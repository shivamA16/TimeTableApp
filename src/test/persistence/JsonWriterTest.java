package persistence;

import exceptions.InvalidTimeException;
import model.Subject;
import model.TTable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Note - Much of this code was adapted from CPSC 210's JsonSerializationDemo project
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TTable tt = new TTable("My timeTable");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTimeTable() {
        try {
            TTable tt = new TTable("My time-table");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(tt);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            tt = reader.read();
            assertEquals("My time-table", tt.getName());
            assertEquals(0, tt.getSubjects().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidTimeException invalidTimeException) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTimeTable() {
        try {
            TTable tt = new TTable("My table");

            Subject one = new Subject("class1");
            Subject two = new Subject("class2");

            try {
                one.setTime("Thursday", 11.5, 12.5);
                two.setTime("Friday", 14, 15);
            } catch (InvalidTimeException e) {
                fail("Should not have caught exception");
            }

            tt.addSubject(one);
            tt.addSubject(two);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(tt);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            tt = reader.read();
            assertEquals("My table", tt.getName());
            List<Subject> subjects = tt.getSubjects();
            assertEquals(2, subjects.size());
            checkSubject("class1", subjects.get(0));
            checkSubject("class2", subjects.get(1));

            assertEquals(15, subjects.get(1).getEnd("Friday"));
            assertEquals(14, subjects.get(1).getStart("Friday"));
            assertEquals(12.5, subjects.get(0).getEnd("Thursday"));
            assertEquals(11.5, subjects.get(0).getStart("Thursday"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidTimeException invalidTimeException) {
            fail("Exception should not have been thrown");
        }
    }
}