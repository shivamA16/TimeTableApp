package persistence;

import model.Subject;
import model.TTable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSubject(String name, Subject subject) {
        assertEquals(name, subject.getTitle());
    }
}
