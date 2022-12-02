import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Class for storing the details of each student from the
 * roster file
 *
 */
public class Student {

    private String ID;
    private String student_first_name;
    private String student_last_name;
    private String ASURITE;
    private HashMap<LocalDate, Integer> map_of_students_attendance_details;

    /**
     * Constructor for creating a student object
     *
     * @param ID        Student's ID
     * @param student_first_name Student's First Name
     * @param student_last_name  Student's Last Name
     * @param ASURITE   Student's ASURITE
     */
    public Student(
            String ID,
            String student_first_name,
            String student_last_name,
            String ASURITE) {
        this.ID = ID;
        this.student_first_name = student_first_name;
        this.student_last_name = student_last_name;
        this.ASURITE = ASURITE;
        this.map_of_students_attendance_details = new LinkedHashMap();
    }

    /**
     * Method for either creating or updating the map_of_students_attendance_details data for a
     * particular
     * student for a particular date
     *
     * @param date Date of map_of_students_attendance_details
     * @param time Time spent attending class on that day
     */
    public void addAttendance(LocalDate date, int time) {
        map_of_students_attendance_details.put(date, map_of_students_attendance_details.getOrDefault(date, 0) + time);
    }

    /**
     * Method for getting the map_of_students_attendance_details time for a particular date
     *
     * @param date
     * @return Time
     */
    public int getDateAttendance(LocalDate date) {
        return map_of_students_attendance_details.get(date);
    }

    /**
     * Method for getting the map_of_students_attendance_details mapping of respective student
     *
     * @return Map Mapping of students with time for a date
     */
    public HashMap<LocalDate, Integer> getAttendance() {
        return new LinkedHashMap(map_of_students_attendance_details);
    }

    /**
     * Getter for Student ID
     *
     * @return String value of student's ID
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Setter for student ID
     *
     * @param ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Getter student's ASURITE
     *
     * @return String value of students ASURITE
     */
    public String getStudentASURITEID() {
        return this.ASURITE;
    }

    /**
     * Setter students ASURITE
     *
     * @param ASURITE
     */

    public void setStudentASURITEID(String ASURITE) {
        this.ASURITE = ASURITE;
    }

    /**
     * Getter for student's first name
     *
     * @return String of students first name
     */
    public String getStudentFirstName() {
        return this.student_first_name;
    }

    /**
     * Setter for students first name
     *
     * @param student_first_name
     */
    public void setStudentFirstName(String student_first_name) {
        this.student_first_name = student_first_name;
    }

    /**
     * Getter students last name
     *
     * @return String of students last name
     */
    public String getStudentLastName() {
        return this.student_last_name;
    }

    /**
     * Setter students last name
     *
     * @param student_last_name
     */
    public void setStudentLastName(String student_last_name) {
        this.student_last_name = student_last_name;
    }

}
