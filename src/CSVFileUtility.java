import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class for choosing the Roster File using JFileChooser
 *
 */
public class CSVFileUtility extends JFileChooser {

    private JFileChooser fileChooser = new JFileChooser();
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
    final Dimension preferred = new Dimension();

    /**
     * Constructor for CSVFileChooser
     *
     */
    public CSVFileUtility() {
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setFileFilter(filter);
        fileChooser.setForeground(Color.white);
        fileChooser.setBackground(Color.WHITE);
        preferred.setSize(
                Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2.25,
                Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2.25);
        fileChooser.setPreferredSize(preferred);
    }

    /**
     * Method for letting the user saveCSV the final file
     *
     * @return File
     */
    public File getSaveFile() {
        File file;
        int returnVal = fileChooser.showSaveDialog(getParent());
        file = fileChooser.getSelectedFile();

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            file = null;
        }

        if (file.getName().indexOf('.') == -1) {
            // no extension
            file = new File(file.toString() + ".csv");
        } else if (!file.getName()
                .substring(file.getName().indexOf('.'))
                .equalsIgnoreCase(".csv")) {
            file = null;
        } else {

        }
        return file;
    }

    /**
     * Method for displaying a dialogue box for allowing the user to select a Roster File
     *
     * @return File Roster File selected by the user
     */
    public File getRosterFile() {
        File file;
        int returnVal = fileChooser.showOpenDialog(null);
        file = fileChooser.getSelectedFile();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = file.getName();
            if (!fileName.substring(fileName.lastIndexOf('.')).equals(".csv")) {
                file = null;
            }
        } else {
            file = null;
        }

        return file;
    }


}
