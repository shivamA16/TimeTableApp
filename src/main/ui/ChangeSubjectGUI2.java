package ui;

import exceptions.InvalidTimeException;
import model.Subject;
import model.TTable;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

// Represents an ChangeSubjectGUI2 window
public class ChangeSubjectGUI2 implements ActionListener {
    private String titleOfSubjectToBeChanged;
    private TTable table;
    private JFrame frame;
    private Subject subject;
    private JTextField fieldMondayStart = new JTextField();
    private JTextField fieldMondayEnd = new JTextField();
    private JTextField fieldTuesdayStart = new JTextField();
    private JTextField fieldTuesdayEnd = new JTextField();
    private JTextField fieldWednesdayStart = new JTextField();
    private JTextField fieldWednesdayEnd = new JTextField();
    private JTextField fieldThursdayStart = new JTextField();
    private JTextField fieldThursdayEnd = new JTextField();
    private JTextField fieldFridayStart = new JTextField();
    private JTextField fieldFridayEnd = new JTextField();
    private JPanel panel;

    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;

    private JButton submit = new JButton("Submit");

    // EFFECTS: runs ChangeSubjectGUI2, sets up JFrame
    public ChangeSubjectGUI2(TTable table, String titleOfSubjectToBeChanged) {
        this.table = table;
        frame = new JFrame();
        frame.setTitle("Time-Table");
        frame.setSize(510, 450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0x123456));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        this.titleOfSubjectToBeChanged = titleOfSubjectToBeChanged;
        label = new JLabel("Enter new times for subject on each day");
        label.setFont(new Font("Calibri", Font.ITALIC, 15));
        label.setForeground(Color.WHITE);
        frame.add(label);
        setupText();
        setFieldsAndLabels();
        submit.setBackground(Color.WHITE);
        submit.setForeground(new Color(0x123456));
        submit.addActionListener(this);
        frame.add(submit);
        JLabel label10 = new JLabel("Press Submit when done");
        label10.setForeground(Color.WHITE);
        frame.add(label10);
    }

    // MODIFIES: this
    // EFFECTS: sets labels for starting text on the window, adds them to frame
    private void setupText() {
        JLabel l = new JLabel("Enter start time in first box, end time in second");
        JLabel l2 = new JLabel("Leave box as is if you don't have this class on that day");
        l.setForeground(Color.WHITE);
        l2.setForeground(Color.WHITE);
        frame.add(l);
        frame.add(l2);
    }

    // MODIFIES: this
    // EFFECTS: sets up all JTextFields and labels for subject times
    private void setFieldsAndLabels() {
        fieldMondayStart.setPreferredSize(new Dimension(100, 50));
        fieldMondayEnd.setPreferredSize(new Dimension(100, 50));
        fieldTuesdayStart.setPreferredSize(new Dimension(100, 50));
        fieldTuesdayEnd.setPreferredSize(new Dimension(100, 50));
        fieldWednesdayStart.setPreferredSize(new Dimension(100, 50));
        fieldWednesdayEnd.setPreferredSize(new Dimension(100, 50));
        fieldThursdayStart.setPreferredSize(new Dimension(100, 50));
        fieldThursdayEnd.setPreferredSize(new Dimension(100, 50));
        fieldFridayStart.setPreferredSize(new Dimension(100, 50));
        fieldFridayEnd.setPreferredSize(new Dimension(100, 50));

        label1 = new JLabel("Enter Monday Time");
        label2 = new JLabel("Enter Tuesday Time");
        label3 = new JLabel("Enter Wednesday Time");
        label4 = new JLabel("Enter Thursday Time");
        label5 = new JLabel("Enter Friday Time");
        label1.setForeground(Color.WHITE);
        label2.setForeground(Color.WHITE);
        label3.setForeground(Color.WHITE);
        label4.setForeground(Color.WHITE);
        label5.setForeground(Color.WHITE);
        setALlFieldsToZero();
        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up each JTextfield such that it initially shows 0
    private void setALlFieldsToZero() {
        fieldMondayStart.setText("0");
        fieldMondayEnd.setText("0");
        fieldTuesdayStart.setText("0");
        fieldTuesdayEnd.setText("0");
        fieldWednesdayStart.setText("0");
        fieldWednesdayEnd.setText("0");
        fieldThursdayStart.setText("0");
        fieldThursdayEnd.setText("0");
        fieldFridayStart.setText("0");
        fieldFridayEnd.setText("0");
    }

    // MODIFIES: this
    // EFFECTS: adds all the JTextFields and labels to panel, also creates a grid in the process
    public void addToPanel() {
        panel = new JPanel();
        GridLayout gl = new GridLayout(5,4);
        panel.setLayout(gl);
        panel.setBackground(new Color(0x123456));

        panel.add(label1);
        panel.add(fieldMondayStart);
        panel.add(fieldMondayEnd);
        panel.add(label2);
        panel.add(fieldTuesdayStart);
        panel.add(fieldTuesdayEnd);
        panel.add(label3);
        panel.add(fieldWednesdayStart);
        panel.add(fieldWednesdayEnd);
        panel.add(label4);
        panel.add(fieldThursdayStart);
        panel.add(fieldThursdayEnd);
        panel.add(label5);
        panel.add(fieldFridayStart);
        panel.add(fieldFridayEnd);
        frame.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: adds subject to table with title taken from titleField text, sets up timings accordingly
    public boolean addSubject() {
        subject = new Subject(titleOfSubjectToBeChanged);

        try {
            subject.setTime("Monday", Double.parseDouble(fieldMondayStart.getText()),
                    Double.parseDouble(fieldMondayEnd.getText()));
            subject.setTime("Tuesday", Double.parseDouble(fieldTuesdayStart.getText()),
                    Double.parseDouble(fieldTuesdayEnd.getText()));
            subject.setTime("Wednesday", Double.parseDouble(fieldWednesdayStart.getText()),
                    Double.parseDouble(fieldWednesdayEnd.getText()));
            subject.setTime("Thursday", Double.parseDouble(fieldThursdayStart.getText()),
                    Double.parseDouble(fieldThursdayEnd.getText()));
            subject.setTime("Friday", Double.parseDouble(fieldFridayStart.getText()),
                    Double.parseDouble(fieldFridayEnd.getText()));
            return true;
        } catch (InvalidTimeException e) {
            return false;
        }


    }

    // EFFECTS: returns "" if there are no time clashes on any day, else returns first day with time clash
    private String checkIfSlotFree() {
        ArrayList<String> listOfDays = new ArrayList<>();
        listOfDays.add("Monday");
        listOfDays.add("Tuesday");
        listOfDays.add("Wednesday");
        listOfDays.add("Thursday");
        listOfDays.add("Friday");

        String dayIfSlotNotFree = "";

        for (String day: listOfDays) {
            if (!table.timeSlotFree(subject.getStart(day), subject.getEnd(day), day)) {
                dayIfSlotNotFree = day;
            }
        }

        return dayIfSlotNotFree;
    }

    // MODIFIES: this
    // EFFECTS: adds subject to table when submit button is pressed - shows success dialog, also shows error dialog when
    // there is a time clash - doesn't add subject, plays sounds
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            addSubject();
        }

        if (!checkIfSlotFree().equals("")) {
            playUnSuccessMusic();
            JOptionPane.showMessageDialog(null, "TIME CLASHES ON " + checkIfSlotFree()
                    + ", SUBJECT NOT ADDED");
        } else if (!addSubject()) {
            playUnSuccessMusic();
            JOptionPane.showMessageDialog(null, "START TIME IS GREATER THAN END TIME ON ONE OF"
                    + " THE DAYS, SUBJECT NOT ADDED");

        } else {
            table.addSubject(subject);
            playSuccessMusic();
            JOptionPane.showMessageDialog(null, " TIMINGS CHANGED FOR SUBJECT WITH TITLE: "
                    + titleOfSubjectToBeChanged);
        }

    }

    // EFFECTS: plays success music
    private void playSuccessMusic() {
        InputStream music;

        try {
            music = new FileInputStream(new File("Success.wav"));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // EFFECTS: plays error music
    private void playUnSuccessMusic() {
        InputStream music;

        try {
            music = new FileInputStream(new File("Wrong.wav"));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
