import java.awt.*;
import javax.swing.*;
import java.time.LocalDateTime;

public class EventPlanner
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("EVENT PLANNER"); // title GUI
        frame.setPreferredSize(new Dimension(700, 600)); // size window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EventListPanel eventlistpanel = new EventListPanel(); // instantiate
        addDefaultEvents(eventlistpanel);
        eventlistpanel.registerObserver(eventlistpanel);

        frame.add(eventlistpanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void addDefaultEvents(EventListPanel eventListPanel)
    {
        LocalDateTime mStartDate = LocalDateTime.of(2024, 9, 27, 13, 0);
        LocalDateTime mEndDate = LocalDateTime.of(2024, 9, 27, 14, 0);
        Meeting meeting = new Meeting("Overview Conference", mStartDate, mEndDate, "Room 335");
        eventListPanel.addEvent(meeting);

        LocalDateTime dDate = LocalDateTime.of(2024, 12, 30, 12, 0);
        Deadline deadline = new Deadline("Finish Lab", dDate);
        eventListPanel.addEvent(deadline);
    }
}
