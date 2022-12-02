import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Class for handling the display functionality for the about,
 * attendance data
 *
 */
public class Display {

    /**
     * Method for displaying the error message when user tries
     * to load attendance data before loading the Roster
     *
     */
    public void emptyRosterErrorHandler() {
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "Error");
        JPanel panel = new JPanel();
        JLabel message = new JLabel("Please load the Roster before loading the attendance");

        panel.add(message);
        dialog.add(panel);

        dialog.setSize(300, 70);
        dialog.setVisible(true);
    }

    /**
     * Method for displaying the team info when about is selected
     *
     */
    public void displayTeamInfo() {
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "About");
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        Font f3 = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);

        String teamInfo = "<html> <style: font-family: Garamond, serif>html {text-align: center} html {word-wrap: break-word} </style>"
                +
                "<h1>CSE563 Final Project<br></h1>"
                + "<h2>Members:<br></h2>"
                + "<h3> Shantanu Shishodia, Shreyas Kolte, Akshat Bakliwal</h3>"
                + "<h3>Atul Prakash, Ireish Purohit, Kedar Sai Nadh Reddy Kanchi</h3>"
                + "</html>";
        JLabel teamInfoLabel = new JLabel(teamInfo);
        teamInfoLabel.setFont(f3);
        teamInfoLabel.setForeground(Color.GREEN);
        Border b1 = BorderFactory.createDashedBorder(Color.black);
        Border b2 = BorderFactory.createEtchedBorder(Color.YELLOW, Color.RED);
        Border border = BorderFactory.createCompoundBorder(b2, b1);
        teamInfoLabel.setBorder(border);
        panel.add(teamInfoLabel);
        dialog.add(panel);
        dialog.setSize(600, 250);
        dialog.setVisible(true);
    }

    /**
     * Method for displaying the attendacne data
     *
     * @param additionalStudents
     * @param studentsAdded
     */
    public void displayAttendanceResult(
            LinkedHashMap<String, Integer> additionalStudents, int studentsAdded) {
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "Attendance Results");

        String loadedMessageText = "<html>"+"Data loaded for " + (studentsAdded)
                + " users in the roster.";

        String additionalMessageText = "<html>" + +(additionalStudents.size())
                + " additional attendee(s) was found:<br>";

        JPanel panel = new JPanel();
        JLabel loadedMessage = new JLabel(loadedMessageText);
        JLabel additionalMessage = new JLabel(additionalMessageText);

        loadedMessage.setHorizontalAlignment(JLabel.CENTER);
        additionalMessage.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(loadedMessage);
        panel.add(additionalMessage);

        String attendeeMessage = "";

        if (!additionalStudents.isEmpty()) {
            for (Map.Entry<String, Integer> e : additionalStudents.entrySet()) {
                attendeeMessage = "<html>" + e.getKey() + ", connected for " + e.getValue() + " minute(s)"
                        + "<br></br>";
                JLabel additionalLabel = new JLabel(attendeeMessage);
                additionalLabel.setHorizontalAlignment(JLabel.CENTER);
                panel.add(additionalLabel);
            }
        }

        dialog.add(new JScrollPane(panel));

        dialog.setSize(300, 300);
        dialog.setVisible(true);
        Repository.additionalStudents.clear();
        Repository.studentsAdded = 0;
        Repository.students_added.clear();

    }


}