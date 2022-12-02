import javax.swing.*;

/**
 * Class for selecting the directory for the attendance files
 *
 */
public class AttendanceFolderSelector extends JFileChooser {

    JFileChooser chooser = new JFileChooser();

    /**
     * Default Constructor for the class which creates an object for the
     * JFileChooser to select the folder containing the attendance files
     *
     */
    public AttendanceFolderSelector() {

        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose Attendance Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("Selected Folder Directory : " + chooser.getSelectedFile());
        }
        else {
            System.out.println("No Directory Selected");
        }
    }
}


