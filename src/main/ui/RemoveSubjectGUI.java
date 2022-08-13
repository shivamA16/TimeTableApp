package ui;

import model.TTable;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

// Represents an RemoveSubjectGUI window
public class RemoveSubjectGUI implements ActionListener {
    private TTable table;
    private JFrame frame;
    private JTextField field = new JTextField();
    private JButton submit = new JButton("Submit");
    private JPanel panel = new JPanel();

    // EFFECTS: runs RemoveSubjectGUI, sets up JFrame
    public RemoveSubjectGUI(TTable table) {
        this.table = table;

        frame = new JFrame();

        submit.addActionListener(this);

        frame.setTitle("Time-Table");
        frame.setSize(510, 200);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0x123456));

        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets labels, panel, JTextField for inputting subject title, adds them to frame
    private void setup() {
        JLabel label = new JLabel("Enter title of subject to be removed");
        label.setForeground(Color.WHITE);
        label.setBounds(0,0, 100, 100);
        field.setPreferredSize(new Dimension(100, 50));
        panel.setBackground(new Color(0x123456));

        submit.setBackground(Color.WHITE);
        submit.setForeground(new Color(0x123456));

        panel.add(label);
        panel.add(field);
        panel.add(submit);

        panel.setBorder(new EmptyBorder(50, 10, 50, 10));
        frame.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: removes subject if found and gives success dialog, gives error dialog otherwise
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String title = field.getText();
            if (table.removeSubjectWithTitle(title)) {
                playSuccessMusic();
                JOptionPane.showMessageDialog(null, "SUBJECT WITH TITLE: "
                        + field.getText() + " REMOVED");
            } else {
                playUnSuccessMusic();
                JOptionPane.showMessageDialog(null, "ERROR: SUBJECT WITH TITLE: "
                        + field.getText() + " NOT FOUND");
            }
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
