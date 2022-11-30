import java.io.*;
import java.time.LocalDate; // import the LocalDate class
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Repository Class
 * CSE360-70605 Final Project
 *
 * <p>
 * The main data structure for the assignment. Includes the roster, headers, and
 * all the
 * functions needed to set, update, and convert the data in the data structure.
 * 
 * @author Yasser Dbeis, Junghwan (Kevin) Park, Hunter Paulson
 */
@SuppressWarnings("deprecation")
public class Repository extends Observable {

    public static List<Student> roster;
    public static List<String> headers;
    public static int studentsAdded = 0;
    public static LinkedHashMap<String, Integer> additionalStudents;
    public static List<LocalDate> dates;

    public static final String delimiter = ",";
    public static final int baseHeaders = 4;

    public Repository() {
        headers = new ArrayList();
        headers.add("ID");
        headers.add("First Name");
        headers.add("Last Name");
        // headers.add("Program");
        // headers.add("Level");
        headers.add("ASURITE");
        additionalStudents = new LinkedHashMap();
        dates = new ArrayList<LocalDate>();
    }

    /**
     * Reads from the csvFile path passed as a parameter line by line adding each
     * student to the
     * roster.
     *
     * @param csvFile path to roster csv file
     */
    public void read(String csvFile) {
        List<Student> studentList = new ArrayList();
        System.out.println("debugging step 1");
        System.out.println(csvFile);
        System.out.println("debugging step 2");
        System.out.println(studentList);

        try {

            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String[] studentAttributes;

            line = br.readLine();
            studentAttributes = line.split(delimiter);
            System.out.println("debugging step 3");
            System.out.println(line);
            System.out.println("debugging step 4");
            System.out.println(studentAttributes.toString());

            if (studentAttributes[0].equals("ID")) {
                for (int i = headers.size(); i < studentAttributes.length; i++) {
                    headers.add(studentAttributes[i]);
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

        roster = studentList;
    }

    public String parseDate(String date) {
        String complete_date = date.toString();
        String year = complete_date.substring(2, 4).trim();
        String month = complete_date.substring(5, 7).trim();
        String day = complete_date.substring(8).trim();
        String new_date = month + "/" + day + "/" + year;
        System.out.println("new_date - " + new_date.toString());
        return new_date;
    }

    /**
     * Calls the read file function and notifies observers as per the
     * observable-observer pattern
     *
     * @param csvInputFile path to roster csv file
     */
    public void load(String csvInputFile) {

        headers = headers.subList(0, baseHeaders); // reset to default headers

        this.read(csvInputFile);
        setChanged();
        notifyObservers();
    }

    /**
     * createStudent takes in an array of strings,
     * each entry corresponding to an attribute of
     * the student read in a line of the input csv.
     * The method will use these attributes to
     * construct a Student object and return it as stu.
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
            stu.addAttendance(LocalDate.parse(headers.get(i)), Integer.parseInt(attributes[i]));
        }

        return stu;
    }

    /**
     * saves the roster to a file
     *
     * @param saveFilePath (String) path where file is to be saved
     * @return boolean status
     */
    public boolean save(String saveFilePath) {

        try {
            FileWriter csvWriter = new FileWriter(saveFilePath);

            if (!headers.isEmpty())
                csvWriter.append(String.join(",", headers));

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
     * Converts the headers array list to an array of strings Needed because JTable
     * only accepts an
     * array of strings for headers
     *
     * @return String[] of the roster headers
     */
    public String[] getHeaders() {

        String[] headersArr = new String[headers.size()];
        int i = 0;
        for (String s : headers) {
            if (s == "ID" || s == "First Name" || s == "Last Name" || s == "ASURITE") {
                headersArr[i] = s;
            } else {
                //System.out.println("headers in get headers - " + s);
                String new_date = parseDate(s);
                headersArr[i] = new_date;
            }
            i++;
        }
        return headersArr;
    }

    /**
     * Converts the table data to a double array of strings Needed because JTable
     * only accepts a
     * double array of strings for the data
     *
     * @return String[][] of the roster data
     */
    public String[][] getTableData() {

        String[][] tableData = new String[roster.size()][];

        for (int i = 0; i < roster.size(); i++) {
            String[] stuAttributes = new String[headers.size()];
            stuAttributes[0] = roster.get(i).getID();
            stuAttributes[1] = roster.get(i).getFirstName();
            stuAttributes[2] = roster.get(i).getLastName();
            // stuAttributes[3] = roster.get(i).getProgram();
            // stuAttributes[4] = roster.get(i).getLevel();
            stuAttributes[3] = roster.get(i).getASURITE();

            int studentIndex = baseHeaders;
            for (Map.Entry<LocalDate, Integer> e : roster.get(i).getAttendance().entrySet()) {
                stuAttributes[studentIndex] = Integer.toString(e.getValue());
                studentIndex++;
            }

            tableData[i] = stuAttributes;
        }

        return tableData;
    }

    /**
     * Adds the attendance data for the students by reading from the attendance
     * filepath
     *
     * @param date     date that the attendance data is for
     * @param filepath path to the csv file with attendance data
     */
    public void addStudentAttendance(LocalDate date, String filepath) {

        try {
            File file = new File(filepath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String ASURITE = "";
            int time = 0;

            if (!headers.contains(date.toString())) {
                headers.add(date.toString());
            }

            while ((line = br.readLine()) != null) { // Read all lines of csv file
                ASURITE = line.split(delimiter)[0];

                if (line.split(delimiter)[1].equals("")) {
                    time = 0;
                } else {
                    time = Integer.parseInt(line.split(delimiter)[1]);
                }

                additionalStudents.put(ASURITE, time);

                for (Student student : roster) { // Find student by ASURITE
                    student.addAttendance(date, 0);

                    if (student.getASURITE().equals(ASURITE)) {
                        student.addAttendance(date, time);
                        additionalStudents.remove(ASURITE);
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
     * Checks if the the dateToCheck is in the dates in the roster
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
     * Gets the attendance data in percentage of time attended for the scatter plot
     *
     * @param date to collect the attendance data for
     * @return an array list of attendance data percentages
     */
    public List<Double> getDataSet(LocalDate date) {
        List<Double> xAxis = new ArrayList();

        for (Student student : roster) {
            if (student.getDateAttendance(date) > 0) {
                xAxis.add(1.0);
            } else {
                xAxis.add(0.0);
            }
        }
        return xAxis;
    }
}
