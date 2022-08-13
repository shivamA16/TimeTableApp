package persistence;

import exceptions.InvalidTimeException;
import model.Subject;
import model.TTable;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Note - Much of this code was adapted from CPSC 210's JsonSerializationDemo project
// Represents a reader that reads timetable from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads timetable from file and returns it;
    // throws IOException if an error occurs reading data from file, throws InvalidTimeException if any of the
    // subject timings are invalid.
    public TTable read() throws IOException, InvalidTimeException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTTable(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses timetable from JSON object and returns it, throws InvalidTimeException if any of the
    // subject timings are invalid.
    private TTable parseTTable(JSONObject jsonObject) throws InvalidTimeException {
        String name = jsonObject.getString("name");
        TTable tt = new TTable(name);
        addSubjects(tt, jsonObject);
        return tt;
    }

    // MODIFIES: tt
    // EFFECTS: parses subjects from JSON object and adds them to timetable, throws InvalidTimeException if any of the
    // subject timings are invalid.
    private void addSubjects(TTable tt, JSONObject jsonObject) throws InvalidTimeException {
        JSONArray jsonArray = jsonObject.getJSONArray("subjects");
        for (Object json : jsonArray) {
            JSONObject nextSubject = (JSONObject) json;
            addSubject(tt, nextSubject);
        }
    }

    // MODIFIES: tt
    // EFFECTS: parses subject from JSON object and adds it to timetable, throws InvalidTimeException if any of the
    // subject timings are invalid.
    private void addSubject(TTable tt, JSONObject jsonObject) throws InvalidTimeException {
        String name = jsonObject.getString("name");
        Subject subject = new Subject(name);

        ArrayList<String> listOfDays = new ArrayList<>();
        listOfDays.add("Monday");
        listOfDays.add("Tuesday");
        listOfDays.add("Wednesday");
        listOfDays.add("Thursday");
        listOfDays.add("Friday");


        for (String day: listOfDays) {
            Double start = jsonObject.getDouble(day.toLowerCase() + " start time");
            Double end = jsonObject.getDouble(day.toLowerCase() + " end time");
            subject.setTime(day, start, end);
        }


        tt.addSubject(subject);
    }
}
