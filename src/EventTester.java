import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTester {

    public static void main(String[] args) {
        // we will run methods to test our event calendar
        testDeadline();
        testMeeting();
    }

    public static void testDeadline() {

        System.out.println("-----Testing Deadline-----");
        // deadline date variable
        LocalDateTime deadlineDateTime = createDateTime("2024-09-20 12:00");

        // creating a deadline event
        Deadline deadline = new Deadline("Touch base Meeting", deadlineDateTime);
        System.out.println("Meeting Name: " + deadline.getName());
        System.out.println("Meeting Time: " + deadline.getDateTime());
        // mark event as complete
        deadline.complete();
        System.out.println("Meeting complete? " + deadline.isComplete());
    }

    public static void testMeeting() {

        System.out.println("-----Testing Meeting-----");

        // set meeting times
        LocalDateTime startDateTime = createDateTime("2024-09-20 12:00");
        LocalDateTime endDateTime = createDateTime("2024-09-20 13:00");

        Meeting meeting = new Meeting("Touch Base Meeting", startDateTime, endDateTime, "Room 202");

        System.out.println("Meeting Name: " + meeting.getName());
        System.out.println("Meeting Time: " + meeting.getDateTime());
        System.out.println("Meeting end Time: " + meeting.getEndDateTime());
        System.out.println("Meeting Location: " + meeting.getLocation());
        System.out.println("Duration: " + meeting.getDuration() + " Minutes");
        meeting.complete();
        System.out.println("Meeting complete? " + meeting.isComplete());

    }

    // method to format local date time
    private static LocalDateTime createDateTime(String dateTimeStart) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeStart, formatter);
    }
}
