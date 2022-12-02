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
public class CSVFileChooser extends JFileChooser {

    private JFileChooser chooser = new JFileChooser();
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
    final Dimension preferred = new Dimension();

    /**
     * Constructor for CSVFileChooser
     *
     */
    public CSVFileChooser() {
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setFileFilter(filter);
        chooser.setBackground(Color.WHITE);
        chooser.setForeground(Color.white);
        preferred.setSize(
                Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2.25,
                Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2.25);
        chooser.setPreferredSize(preferred);
    }

    /**
     * Method for letting the user save the final file
     *
     * @return File
     */
    public File getSaveFile() {
        File file;
        int returnVal = chooser.showSaveDialog(getParent());
        file = chooser.getSelectedFile();

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
        int returnVal = chooser.showOpenDialog(null);
        file = chooser.getSelectedFile();

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
