// Test Calendar -- format Date/time

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTester
{
    // test meeting on console
    public static void testMeeting() {
        System.out.println("MEETING____________________________________");
        LocalDateTime startDateTime = createDateTime("2024-09-30 12:00");
        LocalDateTime endDateTime = createDateTime("2024-09-30 14:00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Meeting meeting = new Meeting("Overview Meeting", startDateTime, endDateTime, "Room 335");

        System.out.println("Name: " + meeting.getName());
        System.out.println("Start Time: " + startDateTime.format(formatter));
        System.out.println("End Time: " + endDateTime.format(formatter));
        System.out.println("Location: " + meeting.getLocation());
        System.out.println("Duration: " + meeting.getDuration() + " Minutes");
    }

    // test deadline on console
    public static void testDeadline() {
        System.out.println("\nDEADLINE-----------------------------------");

        LocalDateTime deadlineDateTime = createDateTime("2024-09-20 14:00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Deadline deadline = new Deadline("Final Report", deadlineDateTime);

        System.out.println("Name: " + deadline.getName());
        System.out.println("Deadline: " + deadlineDateTime.format(formatter));
    }

    // set date/time format
    private static LocalDateTime createDateTime(String dateTimeStart) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeStart, format);
    }

    public static void main(String[] args)
    {
        // func call
        testMeeting();
        testDeadline();
    }
}
