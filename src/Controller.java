import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller Class
 * 
 *         The controller manages user interaction and passes these interactions
 *         to the view (main/panel) and the model(repository)
 */
public class Controller {

	public static String getOpenDir() {

		AttendanceFolderSelector fileChooser = new AttendanceFolderSelector();
		return fileChooser.chooser.getSelectedFile().toString();
	}

	/**
	 * Calls getOpenFile in CSV file chooser
	 * 
	 * @return String path to the csv file the user wants to open
	 */
	public static String getOpenPath() {
		try {
			return new CSVFileChooser().getOpenFile().getAbsolutePath().toString();
		} catch (java.lang.NullPointerException e) {
			return null;
		}
	}

	/**
	 * Calls getSaveFile in CSV file chooser
	 * 
	 * @return String path where the user wants to save the csv file
	 */
	public static String getSavePath() {
		try {
			return new CSVFileChooser().getSaveFile().getAbsolutePath().toString();
		} catch (java.lang.NullPointerException e) {
			return null;
		}
	}

	/**
	 * Calls the load function in Repository
	 * 
	 * @param filepath path to the csv file being loaded into the roster
	 */
	public static void loadTable(String filepath) {
		Main.repo.load(filepath);
	}

	/**
	 * Calls the save function iin Repository
	 * 
	 * @param filepath path to the csv file that the current roster is being saved
	 *                 to
	 */
	public static void saveTable(String filepath) {
		Main.repo.save(filepath);
	}

	/**
	 * Calls the Display to display an error when the roster has not been loaded
	 * before save, add attendance, and plot data are called
	 */
	public static void displayEmptyRosterError() {
		// Display error for when the roster has not been loaded
		Display dis = new Display();
		dis.emptyRosterErrorHandler();
	}

	/**
	 * Displays the scatter plot GUI
	 */
	public static void displayBarPlot() {
		BarPlot.barPlotGUI();
	}

	/**
	 * Displays the About dialogue box
	 */
	public static void displayAboutTab() {
		Display dis = new Display();
		dis.displayTeamInfo();
	}

	/**
	 * Reads attendance file records from the directory
	 */
	public static void readAttendanceFiles(String path) {

		String[] pathnames;
		File f = new File(path);
		pathnames = f.list();

		// For each pathname in the pathnames array
		for (String pathname : pathnames) {
			String[] fileName = pathname.split(" ");
			String extensionFilter = pathname.substring(pathname.length() - 3);
			String fileDate = fileName[0];
			if (fileDate.length() == 8 && extensionFilter.equals("csv")) {
				String filePath = path + "//" + pathname;
				// System.out.println("fileDate read : " + fileDate);
				int date_year = Integer.parseInt(fileDate.substring(0, 4));
				int date_month = Integer.parseInt(fileDate.substring(4, 6));
				int date_day = Integer.parseInt(fileDate.substring(6));
				LocalDate date = LocalDate.of(date_year, date_month, date_day);
				// LocalDate date = LocalDate.parse(fileDate, dateTimeFormatter);
				Main.repo.addStudentAttendance(date, filePath);
				System.out.println("File read : " + pathname);
			} else {
				System.out.println("File cannot be read : ");
			}
		}
		// Display JDialog with added info
		Display dis = new Display();
		// System.out.println("additionalStudents");
		// System.out.println(Repository.additionalStudents);
		// System.out.println("studentsAdded");
		// System.out.println(Repository.studentsAdded);
		// System.out.println(Repository.students_added.size());
		dis.displayAttendanceResult(Repository.additionalStudents, Repository.students_added.size());

	}
}
