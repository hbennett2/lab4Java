import java.awt.*;
import javax.swing.*;
import java.time.LocalDateTime;

public class EventPlanner {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));

        EventListPanel eventlistpanel = new EventListPanel();
        addDefaultEvents(eventlistpanel);
        frame.add(eventlistpanel);
        frame.pack();
        frame.setVisible(true);
    }

    // add defaults events
    public static void addDefaultEvents(EventListPanel eventListPanel) {

        LocalDateTime deadlineDateTime = LocalDateTime.of(2024, 9, 22, 15, 0);
        Deadline deadline = new Deadline("Submit Project", deadlineDateTime);


        LocalDateTime meetingStartDateTime = LocalDateTime.of(2024, 9, 23, 15, 0);
        LocalDateTime meetingEndDateTime = LocalDateTime.of(2024, 9, 23, 16, 0);
        Meeting meeting = new Meeting("Touchbase Meeting", meetingStartDateTime, meetingEndDateTime, "Conference Room 1");


        eventListPanel.addEvent(deadline);
        eventListPanel.addEvent(meeting);
    }
}
