import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;


public class Display {
    public void emptyRosterErrorHandler() {
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "Error");
        JPanel panel = new JPanel();
        JLabel message = new JLabel("ERROR: Roster must be loaded first");

        panel.add(message);
        dialog.add(panel);

        dialog.setSize(300, 70);
        dialog.setVisible(true);
    }

    public void displayAttendanceResult(
            LinkedHashMap<String, Integer> additionalStudents, int studentsAdded) {
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "Attendance Results");
        
        String loadedMessageText = "Data loaded for " + studentsAdded + " users in the roster.";
        
        String additionalMessageText =  "<html>" + additionalStudents.size()  + " additional attendee(s) was found:<br>";
        
        JPanel panel = new JPanel();
        JLabel loadedMessage =new JLabel(loadedMessageText);
        JLabel additionalMessage =new JLabel(additionalMessageText);
        
        panel.setLayout(new FlowLayout());
        panel.add(loadedMessage);
        panel.add(additionalMessage);

        String attendeeMessage = "";

        if (!additionalStudents.isEmpty()) {
            for (Map.Entry<String, Integer> e : additionalStudents.entrySet()) {
                attendeeMessage = "<html>" + e.getKey() + ", connected for " + e.getValue() + " minute(s)" + "<br></br>";
                JLabel additionalLabel = new JLabel(attendeeMessage);
                panel.add(additionalLabel);
            }
        }
        

        dialog.add(new JScrollPane(panel));
        
        dialog.setSize(600, 400);
        //dialog.pack();
        dialog.setVisible(true);
        
        Repository.additionalStudents.clear();
        Repository.studentsAdded = 0;
    }

    /*
      Dialog box is displayed with team members' name when About is clicked
     */
    public void displayTeamInfo() {
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "About");
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        Font f3  = new Font(Font.DIALOG_INPUT,  Font.BOLD, 20);
		
        
        String teamInfo = "<html> <style: font-family: Garamond, serif>html {text-align: center} html {word-wrap: break-word} </style>" +
                "<h1>CSE563 Final Project<br></h1>"
                        + "<h2>Members:<br></h2>"
                        + "<h3> Shantanu Shishodia, Shreyas Kolte, Akshat Bakliwal</h3>"
                        + "<h3>Atul Prakash, Ireish Purohit, Kedar Kanchi Sai Nadh Reddy</h3>"
                        + "</html>";
        JLabel teamInfoLabel = new JLabel(teamInfo);
        teamInfoLabel.setFont(f3);
        Border b1=BorderFactory.createDashedBorder(Color.BLACK);
        Border b2=BorderFactory.createEtchedBorder(Color.BLACK,Color.WHITE);
        Border border= BorderFactory.createCompoundBorder(b2,b1);
        teamInfoLabel.setBorder(border);
        panel.add(teamInfoLabel);
        dialog.add(panel);
        
        
        
        dialog.setSize(600, 250);
        dialog.setVisible(true);
    }
}
