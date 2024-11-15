// Design Calendar GUI

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel
{
    private Event event;
    private JButton completeButton;

    // constructor
    public EventPanel(Event event)
    {
        this.event = event;
        setLayout(new GridLayout(0, 1));
        addEventDetails();
    }

    // format event area
    private void addEventDetails()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        JLabel line = new JLabel("---------------------------------------------------------------------------------------------------------------");
        JLabel nameLabel = new JLabel(event.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(line);
        add(nameLabel);

        // set due date
        if (event instanceof Deadline)
        {
            Deadline deadline = (Deadline) event;
            JLabel deadlineLabel = new JLabel("Due Date: " + event.getDateTime().format(formatter));
            deadlineLabel.setFont(new Font("Arial",Font.PLAIN, 15));
            add(deadlineLabel);
        }

        // set start/end date and location
        if (event instanceof Meeting)
        {
            Meeting meeting = (Meeting) event;

            JLabel startLabel = new JLabel("Start Time: " + event.getDateTime().format(formatter));
            startLabel.setFont(new Font("Arial",Font.PLAIN, 15));
            add(startLabel);

            JLabel endTimeLabel = new JLabel("End Time: " + meeting.getEndDateTime().format(formatter));
            endTimeLabel.setFont(new Font("Arial",Font.PLAIN, 15));
            add(endTimeLabel);

            JLabel locationLabel = new JLabel("Location: " + meeting.getLocation());
            locationLabel.setFont(new Font("Arial",Font.PLAIN, 15));
            add(locationLabel);

            JLabel durationLabel = new JLabel("Duration: " + meeting.getDuration() + " minutes");
            durationLabel.setFont(new Font("Arial",Font.PLAIN, 15));
            add(durationLabel);
        }

        // add checkbox
        if (event instanceof Completable)
        {
            Completable checked = (Completable) event;

            JCheckBox checkbox = new JCheckBox("Complete", checked.isComplete());
            checkbox.setBackground(Color.WHITE);
            add(checkbox); // add checkbox

            if (checked.isComplete())
            {
                checkbox.setEnabled(false); // disable box
            }

            // add a listener to the checkbox to call complete
            checkbox.addActionListener(new ActionListener()
            {@Override
                public void actionPerformed(ActionEvent e)
                {
                    if (checkbox.isSelected())
                    {
                        checked.complete();
                        checkbox.setEnabled(true); // allow to be unselected
                    }
                }
            });
        }
    }
}

