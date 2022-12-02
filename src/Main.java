import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The Main class for the program. Initializes the GUI.
 * Adds different components like JPanels, Menubar,etc.
 * Also calls the functionalities respectively.
 *
 */
public class Main extends JFrame {

    protected static AttendanceDatabase attendanceRecord;

    /**
     * Constructor for Main class. Initiates the GUI for the program.
     *
     */
    public Main() {
        setLayout(new BorderLayout());

        Dimension preferred = new Dimension();
        preferred.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
        setPreferredSize(preferred);

        JMenuItem loadStudentRoster = new JMenuItem("Load a Roster");
        JMenuItem addStudentAttendance = new JMenuItem("Add Attendance");
        JMenuItem saveStudentRoster = new JMenuItem("Save");
        JMenuItem plotStudentData = new JMenuItem("Plot Data");

        JMenu file = new JMenu("File");
        file.add(loadStudentRoster);
        file.add(addStudentAttendance);
        file.add(saveStudentRoster);
        file.add(plotStudentData);

        JMenuItem about = new JMenuItem("About");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(about);

        add(menuBar);
        setJMenuBar(menuBar);

        setTitle("CSE563 Squad Final Project");

        Panel panel = new Panel();
        add(panel, BorderLayout.CENTER);

        attendanceRecord = new AttendanceDatabase();
        attendanceRecord.addObserver(panel);

        saveStudentRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AttendanceDatabase.Roster != null) {
                    String inputFilepath = Controller.getSaveFilePath();
                    if (inputFilepath != null) {
                        Controller.saveTable(inputFilepath);
                    }
                } else {
                    Controller.displayRosterErrorMessage();
                }
            }
        });

        loadStudentRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputFilepath = Controller.getRosterFilePath();
                if (inputFilepath != null) {
                    Controller.loadRosterTable(inputFilepath);
                }
            }
        });

        plotStudentData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AttendanceDatabase.Roster != null) {
                    Controller.displayBarPlot();
                } else {
                    Controller.displayRosterErrorMessage();
                }
            }
        });

        addStudentAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AttendanceDatabase.Roster != null) {
                    String inputFilepath = Controller.getOpenDirectory();
                    Controller.readAttendanceFiles(inputFilepath);
                } else {
                    Controller.displayRosterErrorMessage();
                }
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.displayGroupDetails();
            }
        });

    }

    /**
     * Begins the execution and creates an object of the Main class
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("\n CSE 563 Final Project is loading \n");
        Main window = new Main();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(window.getPreferredSize());
        window.setVisible(true);

    }

}
