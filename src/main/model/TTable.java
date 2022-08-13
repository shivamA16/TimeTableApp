package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;

// Represents a time-table that has multiple subjects in it
public class TTable implements Writable {
    private ArrayList<Subject> subjects;
    private String name;


    // EFFECTS: initiates a new TTable (time-table) with an empty subjects list
    public TTable(String name) {
        this.name = name;
        subjects = new ArrayList<>();
    }



    // EFFECTS: returns this.subjects
    public ArrayList<Subject> getSubjects() {
        return this.subjects;
    }



    // MODIFIES: this
    // EFFECTS: returns true if subject with given title is found in this.subjects, and removes it, false otherwise
    public boolean removeSubjectWithTitle(String title) {
        boolean isThere = false;
        Subject toBeRemoved = null;

        for (Subject s: subjects) {
            if (s.getTitle().equals(title)) {
                isThere = true;
                toBeRemoved = s;
            }
        }

        if (isThere) {
            subjects.remove(toBeRemoved);
            return true;
        } else {
            return false;
        }
    }





    // REQUIRES: subject s has valid start and end times (times do not exceed a value of 24)
    // MODIFIES: this
    // EFFECTS: returns true if subject is added, false if there is another subject in that time slot
    public boolean addSubject(Subject s) {
        ArrayList<String> listOfDays = new ArrayList<>();
        listOfDays.add("Monday");
        listOfDays.add("Tuesday");
        listOfDays.add("Wednesday");
        listOfDays.add("Thursday");
        listOfDays.add("Friday");

        for (String day: listOfDays) {
            if (timeSlotFree(s.getStart(day), s.getEnd(day), day) == false) {
                return false;
            }
        }
        subjects.add(s);
        return true;
    }



    // EFFECTS: returns true if this.subjects has no subject between entered start and end times, false otherwise
    public boolean timeSlotFree(double start, double end, String day) {
        for (Subject s: this.subjects) {
            if (start >= s.getStart(day) && start < s.getEnd(day)) {
                return false;
            } else if (end > s.getStart(day) && end <= s.getEnd(day)) {
                return false;
            }
        }
        return true;
    }




    // EFFECTS: returns subject from this.subject with given start time on given day, null if subject not found
    public Subject findSubjectByStartTime(double time, String day) {
        for (Subject s: subjects) {
            if (s.getStart(day) == time) {
                return s;
            }
        }
        return null;
    }



    // EFFECTS: returns list of subjects sorted from earliest to latest on given day
    public ArrayList<Subject> sortDayTime(String day) {
        ArrayList<Double> check = new ArrayList<>();
        ArrayList<Subject> finalList = new ArrayList<>();

        for (Subject s: this.subjects) {
            if (s.getStart(day) != 0 || s.getEnd(day) != 0) {
                check.add(s.getStart(day));
            }
        }
        Collections.sort(check);

        for (double startTime: check) {
            Subject test;
            test = findSubjectByStartTime(startTime, day);
            finalList.add(test);
        }

        return finalList;
    }


    // Note - Much of the code in this method was adapted from CPSC 210's JsonSerializationDemo project
    // EFFECTS: returns timetable as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("subjects", subjectsToJson());
        return json;
    }

    // EFFECTS: returns things in this timetable as a JSON array
    private JSONArray subjectsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Subject s : subjects) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns name of timetable
    public String getName() {
        return this.name;
    }

}
