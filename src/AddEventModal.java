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
        setSize(400, 400);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // add color

        // main panel for centering content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // add padding around the edges
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // user input fields
        name = new JTextField(20);
        dateTime = new JTextField("yyyy-MM-dd HH:mm", 20);
        endDateTimeTxt = new JTextField("yyyy-MM-dd HH:mm", 20);
        location = new JTextField(20);

        // choose from meeting or deadline
        eventType = new JComboBox<>(new String[]{"Meeting", "Deadline"}); // add dropdown
        eventType.addActionListener(e -> toggleMeetingFields()); // allow user to change

        submit = new JButton("Add Event");
        submit.addActionListener(e -> addNewEvent()); // trigger func

        // centered labels and fields
        mainPanel.add(createLabeledComponent("Event Name:", name));
        mainPanel.add(createLabeledComponent("Event Start Time:", dateTime));
        mainPanel.add(createLabeledComponent("Event End Time:", endDateTimeTxt));
        mainPanel.add(createLabeledComponent("Location:", location));
        mainPanel.add(createLabeledComponent("Event Type:", eventType));

        // add the submit button at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submit);

        // add the panels to the modal
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        toggleMeetingFields(); // initially hide fields if needed
        setVisible(true);
    }

    // create label
    private JPanel createLabeledComponent(String labelText, JComponent component)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(component);
        return panel;
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
    // In AddEventModal
    private void addNewEvent() {
        String nameTxt = name.getText();
        LocalDateTime startDateTime = LocalDateTime.parse(dateTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Event event;
        if (eventType.getSelectedItem().equals("Deadline")) {
            event = EventFactory.createEvent("Deadline", nameTxt, startDateTime, null, null);
        } else { // Meeting
            LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeTxt.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String locationTxt = location.getText();
            event = EventFactory.createEvent("Meeting", nameTxt, startDateTime, endDateTime, locationTxt);
        }
        panel.addEvent(event);
        dispose(); // close after adding event
    }
}
