import java.io.File;
import java.time.LocalDate;

/**
 * Controller Class for controlling the interactions from the user and executing
 * the required functionalities
 *
 */
public class Controller {

	/**
	 * Method for getting the directory path
	 *
	 * @return String of Directory path
	 */
	public static String getOpenDirectory() {

		AttendanceFolderSelector fileChooser = new AttendanceFolderSelector();
		return fileChooser.chooser.getSelectedFile().toString();
	}

	/**
	 * Method for getting the path of Roster File
	 * 
	 * @return String of Roster file Path
	 */
	public static String getRosterFilePath() {
		try {
			return new CSVFileUtility().getRosterFile().getAbsolutePath().toString();
		} catch (java.lang.NullPointerException e) {
			return null;
		}
	}

	/**
	 * Method for getting the path for saving the file
	 * 
	 * @return String of path for saving the file
	 */
	public static String getSaveFilePath() {
		try {
			return new CSVFileUtility().getSaveFile().getAbsolutePath().toString();
		} catch (java.lang.NullPointerException e) {
			return null;
		}
	}

	/**
	 * Method for calling the Roster Load function
	 * 
	 * @param filepath String of path for Roster File
	 */
	public static void loadRosterTable(String filepath) {
		Main.attendanceRecord.loadCSV(filepath);
	}

	/**
	 * Method for calling the Save File function
	 * 
	 * @param filepath String of path for the Saving file
	 */
	public static void saveTable(String filepath) {
		Main.attendanceRecord.saveCSV(filepath);
	}

	/**
	 * Method for calling the error function when user selects add attendance
	 * before loading the roster
	 *
	 */
	public static void displayRosterErrorMessage() {
		Show errorAlert = new Show();
		errorAlert.missingRosterErrorHandler();
	}

	/**
	 * Method for calling the Bar plot GUI
	 *
	 */
	public static void displayBarPlot() {
		BarPlot.barPlotGUI();
	}

	/**
	 * Method for displaying the About group info
	 *
	 */
	public static void displayAboutInformation() {
		Show groupDetails = new Show();
		groupDetails.displayTeamDetails();
	}

	/**
	 * Method for reading attendance files from the directory and
	 * displaying extra attendees
	 *
	 * @param path
	 */
	public static void readAttendanceFiles(String path) {

		String[] pathNames;
		File f = new File(path);
		pathNames = f.list();

		for (String pathname : pathNames) {
			String[] fileName = pathname.split(" ");
			String extensionFilter = pathname.substring(pathname.length() - 3);
			String fileDate = fileName[0];
			if (fileDate.length() == 8 && extensionFilter.equals("csv")) {
				String filePath = path + "//" + pathname;
				int date_year = Integer.parseInt(fileDate.substring(0, 4));
				int date_month = Integer.parseInt(fileDate.substring(4, 6));
				int date_day = Integer.parseInt(fileDate.substring(6));
				LocalDate date = LocalDate.of(date_year, date_month, date_day);
				Main.attendanceRecord.addStudentAttendance(date, filePath);
				System.out.println("File read : " + pathname);
			} else {
				System.out.println("File cannot be read : ");
			}
		}
		Show dis = new Show();
		dis.displayAttendanceResult(AttendanceDatabase.additionalStudents, AttendanceDatabase.students_added.size());

	}
}
