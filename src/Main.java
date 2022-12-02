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

    protected static Repository repo;

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

        JMenuItem loadRoster = new JMenuItem("Load a Roster");
        JMenuItem addAttendance = new JMenuItem("Add Attendance");
        JMenuItem saveRoster = new JMenuItem("Save");
        JMenuItem plotData = new JMenuItem("Plot Data");

        JMenu file = new JMenu("File");
        file.add(loadRoster);
        file.add(addAttendance);
        file.add(saveRoster);
        file.add(plotData);

        JMenuItem about = new JMenuItem("About");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        menuBar.add(about);

        add(menuBar);
        setJMenuBar(menuBar);

        setTitle("CSE563 Squad Final Project");

        Panel panel = new Panel();
        add(panel, BorderLayout.CENTER);

        repo = new Repository();
        repo.addObserver(panel);

        saveRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Repository.Roster != null) {
                    String inputFilepath = Controller.getSaveFilePath();
                    if (inputFilepath != null) {
                        Controller.saveTable(inputFilepath);
                    }
                } else {
                    Controller.displayNoRosterMessage();
                }
            }
        });

        loadRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputFilepath = Controller.getRosterFilePath();
                if (inputFilepath != null) {
                    Controller.loadRosterTable(inputFilepath);
                }
            }
        });

        plotData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Repository.Roster != null) {
                    Controller.displayBarPlot();
                } else {
                    Controller.displayNoRosterMessage();
                }
            }
        });

        addAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Repository.Roster != null) {
                    String inputFilepath = Controller.getOpenDirectory();
                    Controller.readAttendanceFiles(inputFilepath);
                } else {
                    Controller.displayNoRosterMessage();
                }
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.displayGroupInfo();
            }
        });

    }

    /**
     * Begins the execution and creates an object of the Main class
     * 
     * @param args
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(main.getPreferredSize());
        main.setVisible(true);

    }

}
