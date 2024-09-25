import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEventModal extends JDialog {
    private JTextField nameField;
    private JTextField dateTimeField;
    private JTextField endDateTimeField;
    private JTextField locationField;
    private JComboBox<String> eventTypeDropDown;
    private JButton submitButton;

    private EventListPanel eventListPanel;

    // constructor to initialize the dialog
    public AddEventModal(EventListPanel eventListPanel) {
        this.eventListPanel = eventListPanel;
        setTitle("Add New Event");
        setSize(400, 300);
        setLayout(new GridLayout(7, 2));


        nameField = new JTextField();
        dateTimeField = new JTextField("yyyy-MM-dd HH:mm");
        endDateTimeField = new JTextField("yyyy-MM-dd HH:mm");
        locationField = new JTextField();  // Only for Meeting


        eventTypeDropDown = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        eventTypeDropDown.addActionListener(e -> toggleMeetingFields());


        submitButton = new JButton("Add Event");
        submitButton.addActionListener(e -> addNewEvent());


        add(new JLabel("Event Name:"));
        add(nameField);
        add(new JLabel("Event Start Time:"));
        add(dateTimeField);
        add(new JLabel("Event End Time:"));
        add(endDateTimeField);
        add(new JLabel("Location:"));
        add(locationField);
        add(new JLabel("Event Type:"));
        add(eventTypeDropDown);
        add(submitButton);

        toggleMeetingFields();

        setVisible(true);
    }

    // toggle visibility of Meeting-specific fields
    private void toggleMeetingFields() {
        boolean isMeeting = eventTypeDropDown.getSelectedItem().equals("Meeting");
        endDateTimeField.setVisible(isMeeting);
        locationField.setVisible(isMeeting);
        revalidate();
        repaint();
    }

    // method to add a new event based on the input fields
    private void addNewEvent() {
        String name = nameField.getText();
        LocalDateTime startDateTime = LocalDateTime.parse(dateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


        if (eventTypeDropDown.getSelectedItem().equals("Deadline")) {
            Deadline deadline = new Deadline(name, startDateTime);
            eventListPanel.addEvent(deadline);
        } else if (eventTypeDropDown.getSelectedItem().equals("Meeting")) {
            LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String location = locationField.getText();
            Meeting meeting = new Meeting(name, startDateTime, endDateTime, location);
            eventListPanel.addEvent(meeting);
        }

        dispose();
    }
}
