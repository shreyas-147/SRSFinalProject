import java.io.*;
import java.time.LocalDate; // import the LocalDate class
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Class for storing the data of the program including roster, attendance
 * ,fileHeaders, etc. It also contains the functions for manipulating the data stored
 *
 */
public class AttendanceDatabase extends Observable {

    public static List<Student> Roster;
    public static List<String> fileHeaders;
    public static int studentsAdded = 0;
    public static LinkedHashMap<String, Integer> additionalStudents;
    public static List<LocalDate> dates;
    public static List<String> students_added = new ArrayList<String>();

    public static final String delimiter = ",";
    public static final int baseHeaders = 4;

    /**
     * Constructor for the class which initializes the class variables
     *
     */
    public AttendanceDatabase() {
        fileHeaders = new ArrayList();
        fileHeaders.add("ID");
        fileHeaders.add("First Name");
        fileHeaders.add("Last Name");
        fileHeaders.add("ASURITE");
        additionalStudents = new LinkedHashMap();
        dates = new ArrayList<LocalDate>();
    }

    /**
     * Method for reading the files from the path provided and adding students
     * per row
     *
     * @param csvFile String of path for the Roster file
     */
    public void readCSV(String csvFile) {
        List<Student> studentList = new ArrayList();
        try {

            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String[] studentAttributes;

            line = br.readLine();
            studentAttributes = line.split(delimiter);

            if (studentAttributes[0].equals("ID")) {
                for (int i = fileHeaders.size(); i < studentAttributes.length; i++) {
                    fileHeaders.add(studentAttributes[i]);
                }
            } else {
                studentList.add(createStudent(studentAttributes));
            }

            while ((line = br.readLine()) != null) {
                studentAttributes = line.split(delimiter);
                studentList.add(createStudent(studentAttributes));
            }

            br.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Roster = studentList;
    }

    /**
     * Method for calling the readCSV file functionality and simultaneously
     * notifying the observers
     *
     * @param csvInputFile String of path for the Roster File
     */
    public void loadCSV(String csvInputFile) {
        fileHeaders = fileHeaders.subList(0, baseHeaders);
        this.readCSV(csvInputFile);
        setChanged();
        notifyObservers();
    }

    /**
     * Method for parsing the dates from the file name
     *
     * @param date
     * @return
     */
    public String parseDate(String date) {
        String complete_date = date.toString();
        String year = complete_date.substring(2, 4).trim();
        String month = complete_date.substring(5, 7).trim();
        String day = complete_date.substring(8).trim();
        String new_date = month + "/" + day + "/" + year;
        return new_date;
    }

    /**
     * Method for cr eating student object with their corresponding data
     * and adding it to the data structure
     *
     * @param attributes String[]
     * @return Student
     *
     */
    public Student createStudent(String[] attributes) {
        String ID = attributes[0];
        String firstName = attributes[1];
        String lastName = attributes[2];
        String ASURITE = attributes[3];

        Student stu = new Student(ID, firstName, lastName, ASURITE);
        for (int i = baseHeaders; i < attributes.length; i++) {
            stu.addAttendance(LocalDate.parse(fileHeaders.get(i)), Integer.parseInt(attributes[i]));
        }
        return stu;
    }

    /**
     * Method for saving the output file
     *
     * @param saveFilePath String of path for saveCSV file location
     * @return boolean status
     */
    public boolean saveCSV(String saveFilePath) {

        try {
            FileWriter csvWriter = new FileWriter(saveFilePath);

            if (!fileHeaders.isEmpty())
                csvWriter.append(String.join(",", fileHeaders));

            List<List<String>> tableData = new ArrayList();

            String[][] arrTableData = getTableData();

            for (int i = 0; i < arrTableData.length; i++) {
                List<String> tableRow = Arrays.asList(arrTableData[i]);
                tableData.add(tableRow);
            }

            for (List<String> studentInfo : tableData) {
                csvWriter.append("\n");
                csvWriter.append(String.join(",", studentInfo));
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Method for adding attendance of each student from the given file path
     * and notifying the observers simultaneously
     *
     * @param date     attendance date
     * @param filepath String of path for the attendance data
     */
    public void addStudentAttendance(LocalDate date, String filepath) {

        try {
            File file = new File(filepath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String ASURITE = "";
            int time = 0;

            if (!fileHeaders.contains(date.toString())) {
                fileHeaders.add(date.toString());
            }

            while ((line = br.readLine()) != null) { // Read all lines of csv file
                ASURITE = line.split(delimiter)[0];

                if (line.split(delimiter)[1].equals("")) {
                    time = 0;
                } else {
                    time = Integer.parseInt(line.split(delimiter)[1]);
                }

                additionalStudents.put(ASURITE, time);

                for (Student student : Roster) { // Find student by ASURITE
                    student.addAttendance(date, 0);

                    if (student.getASURITE().equals(ASURITE)) {
                        student.addAttendance(date, time);
                        additionalStudents.remove(ASURITE);
                        if (!students_added.contains(ASURITE)) {
                            students_added.add(ASURITE);
                        }
                        studentsAdded++;

                        if (!this.hasDate(date)) {
                            dates.add(date);
                        }
                    }
                }
            }

            br.close();
            setChanged();
            notifyObservers();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    /**
     * Method for converting the arraylist of fileHeaders to an array of string to be used
     * by the JTable
     *
     * @return headersArr String of roster fileHeaders
     */
    public String[] getHeaders() {

        String[] headersArray = new String[fileHeaders.size()];
        int i = 0;
        for (String s : fileHeaders) {
            if (s == "ID" || s == "First Name" || s == "Last Name" || s == "ASURITE") {
                headersArray[i] = s;
            } else {
                String new_date = parseDate(s);
                headersArray[i] = new_date;
            }
            i++;
        }
        return headersArray;
    }

    /**
     * Method for checking if a date has already been added to the roster
     *
     * @param dateToCheck
     * @return boolean True if the date is in the roster, false otherwise
     */
    public boolean hasDate(LocalDate dateToCheck) {
        for (LocalDate date : dates) {
            if ((date).equals(dateToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for converting the data to a format suitable for displaying in the JTable
     *
     * @return tableData String of the Roster data
     */
    public String[][] getTableData() {

        String[][] tableData = new String[Roster.size()][];

        for (int i = 0; i < Roster.size(); i++) {
            String[] stuAttributes = new String[fileHeaders.size()];
            stuAttributes[0] = Roster.get(i).getID();
            stuAttributes[1] = Roster.get(i).getFirstName();
            stuAttributes[2] = Roster.get(i).getLastName();
            stuAttributes[3] = Roster.get(i).getASURITE();

            int studentIndex = baseHeaders;
            for (Map.Entry<LocalDate, Integer> e : Roster.get(i).getAttendance().entrySet()) {
                stuAttributes[studentIndex] = Integer.toString(e.getValue());
                studentIndex++;
            }

            tableData[i] = stuAttributes;
        }

        return tableData;
    }

    /**
     * Method for checking whether a student attended the class or not
     * for displaying it in the bar plot
     *
     * @param date date to check attendance data for each student
     * @return an array list of attendance data percentages
     */
    public List<Double> getDataSet(LocalDate date) {
        List<Double> xAxis = new ArrayList();

        for (Student student : Roster) {
            if (student.getDateAttendance(date) > 0) {
                xAxis.add(1.0);
            } else {
                xAxis.add(0.0);
            }
        }
        return xAxis;
    }
}
