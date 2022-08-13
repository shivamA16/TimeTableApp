package model;

import exceptions.InvalidTimeException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a subject with start and end times at each day of the week, if applicable and title
public class Subject implements Writable {
    private String title;

    private double startTimeMonday;
    private double endTimeMonday;
    private double startTimeTuesday;
    private double endTimeTuesday;
    private double startTimeWednesday;
    private double endTimeWednesday;
    private double startTimeThursday;
    private double endTimeThursday;
    private double startTimeFriday;
    private double endTimeFriday;

    // REQUIRES: title has a non-zero length
    // EFFECTS: title of the subject is set to titleName
    public Subject(String titleName) {
        this.title = titleName;
    }

    // MODIFIES: this
    // EFFECTS: sets the start and end times of a subject on the given day, does nothing if given day is not properly
    //          spelled
    public void setTime(String day, double start, double end) throws InvalidTimeException {

        if (start > end) {
            throw new InvalidTimeException();
        }

        if (day.equals("Monday")) {
            this.startTimeMonday = start;
            this.endTimeMonday = end;
        } else if (day.equals("Tuesday")) {
            this.startTimeTuesday = start;
            this.endTimeTuesday = end;
        } else if (day.equals("Wednesday")) {
            this.startTimeWednesday = start;
            this.endTimeWednesday = end;
        } else if (day.equals("Thursday")) {
            this.startTimeThursday = start;
            this.endTimeThursday = end;
        } else if (day.equals("Friday")) {
            this.startTimeFriday = start;
            this.endTimeFriday = end;
        } else {
            return;
        }
    }


    // EFFECTS: returns the start time of a subject on the given day, -1 if wrong day is given
    public double getStart(String day) {
        if (day.equals("Monday")) {
            return this.startTimeMonday;
        } else if (day.equals("Tuesday")) {
            return this.startTimeTuesday;
        } else if (day.equals("Wednesday")) {
            return this.startTimeWednesday;
        } else if (day.equals("Thursday")) {
            return this.startTimeThursday;
        } else if (day.equals("Friday")) {
            return this.startTimeFriday;
        } else {
            return -1;
        }
    }

    // EFFECTS: returns the end time of a subject on the given day, -1 if wrong day is given
    public double getEnd(String day) {
        if (day.equals("Monday")) {
            return this.endTimeMonday;
        } else if (day.equals("Tuesday")) {
            return this.endTimeTuesday;
        } else if (day.equals("Wednesday")) {
            return this.endTimeWednesday;
        } else if (day.equals("Thursday")) {
            return this.endTimeThursday;
        } else if (day.equals("Friday")) {
            return this.endTimeFriday;
        } else {
            return -1;
        }
    }

    // EFFECTS: gets title of subject
    public String getTitle() {
        return this.title;
    }

    // Note - Much of the code in this method was adapted from CPSC 210's JsonSerializationDemo project
    // EFFECTS: returns subject as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", title);
        json.put("monday start time", startTimeMonday);
        json.put("monday end time", endTimeMonday);
        json.put("tuesday start time", startTimeTuesday);
        json.put("tuesday end time", endTimeTuesday);
        json.put("wednesday start time", startTimeWednesday);
        json.put("wednesday end time", endTimeWednesday);
        json.put("thursday start time", startTimeThursday);
        json.put("thursday end time", endTimeThursday);
        json.put("friday start time", startTimeFriday);
        json.put("friday end time", endTimeFriday);

        return json;
    }


}
