package ui;

import exceptions.InvalidTimeException;
import model.TTable;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// TimeTable application Guided User Interface
public class TimeTableGUI extends JFrame implements ActionListener {
    private TTable table;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/timetable.json";
    private JButton viewButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton changeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JLabel label = new JLabel();
    private ArrayList<JButton> buttons;


    // EFFECTS: runs the timetable application GUI
    public TimeTableGUI() {
        input = new Scanner(System.in);
        table = new TTable("Table");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setupFrame();
        setupLabel();
        setupButtons();

    }

    // MODIFIES: this
    // EFFECTS: sets up frame for the window
    private void setupFrame() {
        this.setTitle("Time-Table");
        this.setSize(510, 535);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: sets top label for application name
    private void setupLabel() {
        label = new JLabel();
        label.setText("CPSC 210 Project - TimeTable App");
        label.setBounds(0, 0, 535, 170);
        label.setFont(new Font("Calibri", Font.ITALIC, 25));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);
    }

    // MODIFIES: this
    // EFFECTS: sets up 6 different buttons - gives dimensions, color etc., adds buttons to frame
    private void setupButtons() {
        setButtons();

        addButton.setBounds(0, 170, 170, 170);
        removeButton.setBounds(170, 170, 170, 170);
        changeButton.setBounds(340, 170, 170, 170);
        viewButton.setBounds(0, 340, 170, 170);
        saveButton.setBounds(170, 340, 170, 170);
        loadButton.setBounds(340, 340, 170, 170);

        for (JButton b: buttons) {
            b.setBackground(new Color(0x123456));
            b.setForeground(Color.WHITE);
            b.addActionListener(this);
            b.setFont(new Font("Calibri", Font.ITALIC, 15));
            this.add(b);
        }
    }

    // MODIFIES: this
    // EFFECTS: instantiates the 6 buttons, adds them to buttons
    private void setButtons() {
        buttons = new ArrayList<>();
        viewButton = new JButton("View TimeTable");
        addButton = new JButton("Add a Subject");
        removeButton = new JButton("Remove a subject");
        changeButton = new JButton("Change subject time");
        saveButton = new JButton("Save TimeTable");
        loadButton = new JButton("Load TimeTable");

        buttons.add(viewButton);
        buttons.add(addButton);
        buttons.add(changeButton);
        buttons.add(removeButton);
        buttons.add(saveButton);
        buttons.add(loadButton);
    }


    // MODIFIES: this
    // EFFECTS: Loads timetable from file
    private void loadTimeTable() {
        try {
            table = jsonReader.read();
            JOptionPane.showMessageDialog(null, "TIME-TABLE LOADED!");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (InvalidTimeException invalidTimeException) {
            JOptionPane.showMessageDialog(null, "Saved table has invalid timings: Subject "
                    + "with invalid timings not loaded");
        }

    }

    // EFFECTS: saves timetable to file
    private void saveTimeTable() {
        try {
            jsonWriter.open();
            jsonWriter.write(table);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "TIME-TABLE SAVED!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    // MODIFIES: this
    // EFFECTS: opens different windows/runs methods corresponding to the button user pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewButton) {
            ViewTimeTableGUI view = new ViewTimeTableGUI(table);
        } else if (e.getSource() == saveButton) {
            saveTimeTable();
        } else if (e.getSource() == loadButton) {
            loadTimeTable();
        } else if (e.getSource() == removeButton) {
            RemoveSubjectGUI remove = new RemoveSubjectGUI(table);
        } else if (e.getSource() == addButton) {
            AddSubjectGUI add = new AddSubjectGUI(table);
        } else if (e.getSource() == changeButton) {
            ChangeSubjectGUI change = new ChangeSubjectGUI(table);
        }
    }
}
