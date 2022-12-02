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
    private String firstName;
    private String lastName;
    private String ASURITE;
    private HashMap<LocalDate, Integer> studentAttendance;

    /**
     * Constructor for creating a student object
     *
     * @param ID Student's ID
     * @param firstName Student's First Name
     * @param lastName Student's Last Name
     * @param ASURITE Student's ASURITE
     */
    public Student(
            String ID,
            String firstName,
            String lastName,
            String ASURITE) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ASURITE = ASURITE;
        this.studentAttendance = new LinkedHashMap();
    }

    /**
     * Method for either creating or updating the studentAttendance data for a particular
     * student for a particular date
     *
     * @param date Date of studentAttendance
     * @param time Time spent attending class on that day
     */
    public void addAttendance(LocalDate date, int time) {
        studentAttendance.put(date, studentAttendance.getOrDefault(date, 0) + time);
    }

    /**
     * Method for getting the studentAttendance time for a particular date
     *
     * @param date
     * @return Time
     */
    public int getDateAttendance(LocalDate date) {
        return studentAttendance.get(date);
    }

    /**
     * Method for getting the studentAttendance mapping of respective student
     *
     * @return Map Mapping of students with time for a date
     */
    public HashMap<LocalDate, Integer> getAttendance() {
        return new LinkedHashMap(studentAttendance);
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
//    public void setID(String ID) {
//        this.ID = ID;
//    }

    /**
     * Getter student's ASURITE
     *
     * @return String value of students ASURITE
     */
    public String getASURITE() {
        return this.ASURITE;
    }

    /**
     * Setter students ASURITE
     *
     * @param ASURITE
     */

//    public void setASURITE(String ASURITE) {
//        this.ASURITE = ASURITE;
//    }
    /**
     * Getter for student's first name
     *
     * @return String of students first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter for students first name
     *
     * @param firstName
     */
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }

    /**
     * Getter students last name
     *
     * @return String of students last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter students last name
     *
     * @param lastName
     */
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }


}
