import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {

    private Event event;
    private JButton completeButton;

    // constructor to initialize EventPanel with an Event object
    public EventPanel(Event event) {
        this.event = event;
        setLayout(new GridLayout(0, 1));


        addEventDetails();
        updateUrgency();
    }

    // method to add event details to the panel
    private void addEventDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        JLabel nameLabel = new JLabel("Event Name: " + event.getName());
        nameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
        add(nameLabel);


        JLabel timeLabel = new JLabel("Event Time: " + event.getDateTime().format(formatter));
        timeLabel.setFont(new Font("Malgun Gothic",Font.BOLD, 15));
        add(timeLabel);


        // if the event is a meeting, add end time, location, and duration
        if (event instanceof Meeting) {
            Meeting meeting = (Meeting) event;

            JLabel endTimeLabel = new JLabel("End Time: " + meeting.getEndDateTime().format(formatter));
            endTimeLabel.setFont(new Font("Malgun Gothic",Font.BOLD, 15));
            add(endTimeLabel);

            JLabel locationLabel = new JLabel("Location: " + meeting.getLocation());
            locationLabel.setFont(new Font("Malgun Gothic",Font.BOLD, 15));
            add(locationLabel);

            JLabel durationLabel = new JLabel("Duration: " + meeting.getDuration() + " minutes");
            durationLabel.setFont(new Font("Malgun Gothic",Font.BOLD, 15));
            add(durationLabel);
        }

        // if the event is Completable, add the completion status and a completion checkbox
        if (event instanceof Completable) {
            Completable completableEvent = (Completable) event;

            JCheckBox completeCheckBox = new JCheckBox("Complete", completableEvent.isComplete());
            add(completeCheckBox);


            if (completableEvent.isComplete()) {
                completeCheckBox.setEnabled(false);
            }

            // add a listener to the checkbox
            completeCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (completeCheckBox.isSelected()) {
                        completableEvent.complete();
                        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.ITALIC));
                        completeCheckBox.setEnabled(false);

                    }
                }
            });
        }


    }

    // method to update the panel background color based on event urgency
    public void updateUrgency() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventTime = event.getDateTime();


        Duration duration = Duration.between(now, eventTime);

        // set the background color based on the urgency of the event
        if (duration.isNegative()) {

            setBackground(new Color(255,102,102));

        } else if (duration.toHours() <= 1) {

            setBackground(new Color(255,255,153));

        } else {

            setBackground(new Color(144,238,144));
        }
    }
}

