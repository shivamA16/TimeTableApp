package ui;

import model.Subject;
import model.TTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Represents an ViewTimeTableGUI window
public class ViewTimeTableGUI {
    private TTable table;
    private JFrame frame;
    private JLabel labelMonday;
    private JLabel labelTuesday;
    private JLabel labelWednesday;
    private JLabel labelThursday;
    private JLabel labelFriday;

    // EFFECTS: runs ViewTimeTableGUI, sets up JFrame
    public ViewTimeTableGUI(TTable table) {
        this.table = table;
        frame = new JFrame();

        frame.setTitle("Time-Table");
        frame.setSize(510, 600);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        labelMonday = new JLabel();
        labelTuesday = new JLabel();
        labelWednesday = new JLabel();
        labelThursday = new JLabel();
        labelFriday = new JLabel();

        view();

        frame.add(labelMonday);
        frame.add(labelTuesday);
        frame.add(labelWednesday);
        frame.add(labelThursday);
        frame.add(labelFriday);

        frame.getContentPane().setBackground(new Color(0x123456));
    }

    // MODIFIES: this
    // EFFECTS: gets subjects timings from table, and assigns strings (times + text) to the labels created for each day
    public void view() {
        ArrayList<String> listOfDays = new ArrayList<>();
        listOfDays.add("Monday");
        listOfDays.add("Tuesday");
        listOfDays.add("Wednesday");
        listOfDays.add("Thursday");
        listOfDays.add("Friday");

        for (String day: listOfDays) {

            ArrayList<Subject> myList = table.sortDayTime(day);

            String string = "<html>" + day + ":";

            for (Subject s : myList) {
                string = string + "<br>      " + s.getTitle() + " - " + s.getStart(day) + " to " + s.getEnd(day);
            }
            string = string + "</html>";
            viewHelper(day, string);
        }


    }

    // MODIFIES: this
    // EFFECTS: sets up a label's text, bounds and font color depending upon what day, string were inputted
    private void viewHelper(String day, String string) {
        if (day.equals("Monday")) {
            labelMonday.setText(string);
            labelMonday.setBounds(100, 0, 300, 100);
            labelMonday.setForeground(Color.WHITE);
        } else if (day.equals("Tuesday")) {
            labelTuesday.setText(string);
            labelTuesday.setBounds(100, 100, 300, 100);
            labelTuesday.setForeground(Color.WHITE);
        } else if (day.equals("Wednesday")) {
            labelWednesday.setText(string);
            labelWednesday.setBounds(100, 200, 300, 100);
            labelWednesday.setForeground(Color.WHITE);
        } else if (day.equals("Thursday")) {
            labelThursday.setText(string);
            labelThursday.setBounds(100, 300, 300, 100);
            labelThursday.setForeground(Color.WHITE);
        } else if (day.equals("Friday")) {
            labelFriday.setText(string);
            labelFriday.setBounds(100, 400, 300, 100);
            labelFriday.setForeground(Color.WHITE);
        }
    }



}
