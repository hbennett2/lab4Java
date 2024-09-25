import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events; // list of events
    private JPanel controlPanel;  // panel for controls
    private JPanel displayPanel;  // panel to display EventPanels
    private JComboBox<String> sortDropDown; // dropdown to sort events
    private JComboBox<String> filterDropDown; // dropdown to filter events by type
    private JCheckBox filterDisplay;  // checkbox to filter completed events
    private JButton addEventButton;  // button to add a new event


    public EventListPanel() {
        events = new ArrayList<>();
        setLayout(new BorderLayout());

        // create control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // sort dropdown options
        String[] sortOptions = {"Sort by Name", "Sort by Date", "Sort by Name (Reverse)", "Sort by Date (Reverse)"};
        sortDropDown = new JComboBox<>(sortOptions);
        sortDropDown.addActionListener(e -> sortEvents((String) sortDropDown.getSelectedItem()));
        controlPanel.add(sortDropDown);

        // filter by event type dropdown
        String[] filterOptions = {"All Events", "Deadlines", "Meetings"};
        filterDropDown = new JComboBox<>(filterOptions);
        filterDropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterEventsByType((String) filterDropDown.getSelectedItem());
            }
        });
        controlPanel.add(new JLabel("Filter by Type:"));
        controlPanel.add(filterDropDown);

        // filter checkbox for completed events
        filterDisplay = new JCheckBox("Show Completed Events");
        filterDisplay.setSelected(true);
        filterDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterEvents();
            }
        });
        controlPanel.add(filterDisplay);

        // add event button
        addEventButton = new JButton("Add Event");
        addEventButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

        // open modal to add events
        addEventButton.addActionListener(e -> new AddEventModal(this));

        // add button to controlPanel
        controlPanel.add(addEventButton);

        add(controlPanel, BorderLayout.NORTH);

        // create display panel and add it to the center
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    // method to add an Event to the list and update the display
    public void addEvent(Event event) {
        events.add(event);
        refreshDisplay();
    }

    // refresh the display panel
    private void refreshDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {
            EventPanel eventPanel = new EventPanel(event);
            displayPanel.add(eventPanel);
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    // method to sort events based on selected sorting option
    private void sortEvents(String sortOption) {
        switch (sortOption) {
            case "Sort by Name":
                Collections.sort(events, Comparator.comparing(Event::getName));
                break;
            case "Sort by Date":
                Collections.sort(events, Comparator.comparing(Event::getDateTime));
                break;
            case "Sort by Name (Reverse)":
                Collections.sort(events, Comparator.comparing(Event::getName).reversed());
                break;
            case "Sort by Date (Reverse)":
                Collections.sort(events, Comparator.comparing(Event::getDateTime).reversed());
                break;
        }
        refreshDisplay();
    }

    // method to filter events based on the checkbox
    private void filterEvents() {
        displayPanel.removeAll();
        for (Event event : events) {
            if (filterDisplay.isSelected() || !(event instanceof Completable) || !((Completable) event).isComplete()) {
                EventPanel eventPanel = new EventPanel(event);
                displayPanel.add(eventPanel);
            }
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    // method to filter events by type (Deadlines or Meetings)
    private void filterEventsByType(String filterOption) {
        displayPanel.removeAll();

        for (Event event : events) {
            if (filterOption.equals("All Events")) {
                displayEvent(event);
            } else if (filterOption.equals("Deadlines") && event instanceof Deadline) {
                displayEvent(event);
            } else if (filterOption.equals("Meetings") && event instanceof Meeting) {
                displayEvent(event);
            }
        }

        displayPanel.revalidate();
        displayPanel.repaint();
    }


    private void displayEvent(Event event) {
        EventPanel eventPanel = new EventPanel(event);
        displayPanel.add(eventPanel);
    }
}
