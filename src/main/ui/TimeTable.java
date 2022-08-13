package ui;

import exceptions.InvalidTimeException;
import model.Subject;
import model.TTable;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// TimeTable application
public class TimeTable {
    private TTable table;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/timetable.json";

    // EFFECTS: runs the timetable application
    public TimeTable() {
        input = new Scanner(System.in);
        table = new TTable("Table");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        whatToDo();
    }



    // EFFECTS: starting menu, asks user for input regarding what to do next
    // Some of the code design used in this method was taken from CPSC 210's TellerApp example
    private void whatToDo() {
        input = new Scanner(System.in);
        System.out.println("\nSelect: ");
        System.out.println("\t - For adding a new subject enter 'add'");
        System.out.println("\t - For removing a subject enter 'remove'");
        System.out.println("\t - For changing a subject's timings, enter 'change'");
        System.out.println("\t - For viewing timetable enter 'view'");
        System.out.println("\t - For saving this timetable to file, enter 'save'");
        System.out.println("\t - For loading a timetable, enter 'load'");
        System.out.println("\t - To quit, enter 'quit'");

        doSomething();
    }

    // EFFECTS: does a task based on given input
    private void doSomething() {
        String decision = input.next();
        if (decision.toLowerCase().equals("view")) {
            viewTimeTable();
        } else if (decision.toLowerCase().equals("add")) {
            addSubjects();
        } else if (decision.toLowerCase().equals("quit")) {
            System.out.println("Process ended");
        } else if (decision.toLowerCase().equals("remove")) {
            removeSubject();
        } else if (decision.toLowerCase().equals("change")) {
            changeSubjectTime();
        } else if (decision.toLowerCase().equals("save")) {
            saveTimeTable();
        } else if (decision.toLowerCase().equals("load")) {
            loadTimeTable();
        } else {
            System.out.println("Error, invalid input");
            whatToDo();
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads timetable from file
    private void loadTimeTable() {
        try {
            table = jsonReader.read();
            System.out.println("Loaded " + table.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            whatToDo();
        } catch (InvalidTimeException invalidTimeException) {
            System.out.println("At least one of the subjects has invalid timings.");
        }
        whatToDo();
    }

    // EFFECTS: saves timetable to file
    private void saveTimeTable() {
        try {
            jsonWriter.open();
            jsonWriter.write(table);
            jsonWriter.close();
            System.out.println("Saved " + table.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            whatToDo();
        }
        whatToDo();
    }


    // MODIFIES: this
    // EFFECTS: changes times of inputted subject, asks user to input time for each day, says error if subject not found
    private void changeSubjectTime() {
        System.out.println("Enter title of subject you would like to change timings of");
        input = new Scanner(System.in);
        String toBeChanged = input.nextLine();

        if (table.removeSubjectWithTitle(toBeChanged)) {
            Subject subject = new Subject(toBeChanged);

            setDay(subject, "Monday");
            setDay(subject, "Tuesday");
            setDay(subject, "Wednesday");
            setDay(subject, "Thursday");
            setDay(subject, "Friday");

            table.addSubject(subject);
        } else {
            System.out.println("Error: subject with given title not found");
        }
        whatToDo();
    }


    // MODIFIES: this
    // EFFECTS: removes inputted subject from timetable, gives error if subject not in timetable
    public void removeSubject() {
        Boolean isThere = false;
        Subject toBeRemoved = null;
        input = new Scanner(System.in);
        System.out.println("Enter title of subject to be deleted ");
        String toBeRemovedTitle = input.nextLine();

        if (table.removeSubjectWithTitle(toBeRemovedTitle)) {
            System.out.println("Subject with title: " + toBeRemovedTitle + " is removed");
        } else {
            System.out.println("Error: subject with given title not found");
        }
        whatToDo();
    }





    // EFFECTS: prints out every day's classes from earliest to latest
    private void viewTimeTable() {
        ArrayList<String> listOfDays = new ArrayList<>();
        listOfDays.add("Monday");
        listOfDays.add("Tuesday");
        listOfDays.add("Wednesday");
        listOfDays.add("Thursday");
        listOfDays.add("Friday");

        for (String day: listOfDays) {
            System.out.println("\n" + day + ":");

            ArrayList<Subject> myList = table.sortDayTime(day);

            for (Subject s : myList) {
                System.out.println(s.getTitle() + " - " + s.getStart(day) + " to " + s.getEnd(day));
            }
        }
        whatToDo();
    }





    // MODIFIES: this
    // EFFECTS: adds inputted subject onto this.subjects, asks user if they have that subject, each day of the week
    public void addSubjects() {
        Scanner input = new Scanner(System.in);
        input = new Scanner(System.in);
        System.out.println("Enter the subject title: ");
        String title = input.nextLine();

        Subject subject = new Subject(title);

        setDay(subject, "Monday");
        setDay(subject, "Tuesday");
        setDay(subject, "Wednesday");
        setDay(subject, "Thursday");
        setDay(subject, "Friday");

        table.addSubject(subject);

        whatToDo();
    }




    // EFFECTS: displays options to user if given subject time-clashes with another in setDay()
    public void timeSlotTaken(Subject subject, String day) {
        System.out.println("Timeslot already taken, continue to next day or enter " + day +  " time again?");
        System.out.println("Enter c for continue or e to enter " + day + " time again");
        String rem = input.next();
        if (rem.toLowerCase().equals("e")) {
            setDay(subject, day);
        } else if (!rem.toLowerCase().equals("c")) {
            System.out.println("\nError: invalid input");
            timeSlotTaken(subject, day);
        }
    }




    // MODIFIES: this
    // EFFECTS: sets the given subject time with start and end on given day
    public void setTimeSlot(String day, Subject subject, double start, double end) {
        try {
            subject.setTime(day, start, end);
        } catch (InvalidTimeException e) {
            System.out.println("Day has start time greater than end time");
        }
        System.out.println("Subject added on " + day);
    }




    // MODIFIES: this
    // EFFECTS: adds a subject to this.subjects with inputted start and end times, on given day
    public void setDay(Subject subject, String day) {
        System.out.println("Do you have " + subject.getTitle() + " on " + day + " (y/n)");
        String check = input.next();
        if (check.toLowerCase().equals("y")) {
            System.out.println("Enter start time " + day + " (00h - 24h (military time), 1:30 pm would be 13.5): ");
            Double startTime = input.nextDouble();
            System.out.println("Enter end time " + day + " (00h - 24h (military time),"
                    + " 1:30 pm would be entered as 13.50): ");
            Double endTime = input.nextDouble();

            if (startTime > endTime || endTime > 24 || startTime > 24) {
                System.out.println("Error: enter valid time values");
                setDay(subject, day);
                return;
            }
            if (table.timeSlotFree(startTime, endTime, day)) {
                setTimeSlot(day, subject, startTime, endTime);
            } else {
                timeSlotTaken(subject, day);
            }
        } else if (!check.toLowerCase().equals("n")) {
            System.out.println("\nError: please enter y or n");
            setDay(subject, day);
        }
    }











}
