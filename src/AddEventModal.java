// Add Event button logic

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEventModal extends JDialog
{
    // variables!
    private JTextField name;
    private JTextField dateTime;
    private JTextField endDateTimeTxt;
    private JTextField location;
    private JButton submit;
    private JComboBox<String> eventType;
    private EventListPanel panel;

    // constructor
    public AddEventModal(EventListPanel panel)
    {
        this.panel = panel;
        setTitle("Add New Event");
        setSize(400, 300);
        setLayout(new GridLayout(7, 2));
        getContentPane().setBackground(Color.WHITE); // add color

        // user input fields
        name = new JTextField();
        dateTime = new JTextField("yyyy-MM-dd HH:mm");
        endDateTimeTxt = new JTextField("yyyy-MM-dd HH:mm");
        location = new JTextField();

        // choose from meeting or deadline
        eventType = new JComboBox<>(new String[]{"Meeting", "Deadline"}); // add dropdown
        eventType.addActionListener(e -> toggleMeetingFields()); // allow user to change

        submit = new JButton("Add Event");
        submit.addActionListener(e -> addNewEvent()); // trigger func

        // modal layout----------------------------
        add(new JLabel("Event Name:"));
        add(name);
        add(new JLabel("Event Start Time:"));
        add(dateTime);
        add(new JLabel("Event End Time:"));
        add(endDateTimeTxt);
        add(new JLabel("Location:"));
        add(location);
        add(new JLabel("Event Type:"));
        add(eventType);
        add(submit);
        toggleMeetingFields();
        setVisible(true);
    }

    // visibility controls
    private void toggleMeetingFields()
    {
        boolean isMeeting = eventType.getSelectedItem().equals("Meeting");
        endDateTimeTxt.setVisible(isMeeting);
        location.setVisible(isMeeting); // allow location

        revalidate();
        repaint();
    }

    // method to add a new event based on the input fields
    private void addNewEvent()
    {
        String nameTxt = name.getText();
        LocalDateTime startDateTime = LocalDateTime.parse(dateTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (eventType.getSelectedItem().equals("Deadline"))
        {
            Deadline deadline = new Deadline(nameTxt, startDateTime);
            panel.addEvent(deadline);
        }
        else if (eventType.getSelectedItem().equals("Meeting"))
        {
            LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeTxt.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String locationTxt = location.getText();
            Meeting meeting = new Meeting(nameTxt, startDateTime, endDateTime, locationTxt);
            panel.addEvent(meeting);
        }
        dispose(); // close after adding event
    }
}

