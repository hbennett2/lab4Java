import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventListPanel extends JPanel
{
    // variables!
    private ArrayList<Event> events; // put events in list
    private JPanel headerPanel;
    private JButton addEventButton; // user controls
    private JComboBox<String> sortButton; // sort dropdown button
    private JComboBox<String> filterButton; // filter dropdown button
    private JCheckBox checkFilter;
    private JPanel calendarPanel;  // displays events
    private JList<String> eventList; // list of event names
    private DefaultListModel<String> listModel; // model for the event list

    public EventListPanel()
    {
        events = new ArrayList<>();
        setLayout(new BorderLayout());
        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(new Color(173, 216, 230));

        // ------------------------------- sort dropdown -------------------------------------------
        // sort dropdown logic
        String[] sortBy = {"Sort by Name", "Sort by Name (Reverse)", "Sort by Date", "Sort by Date (Reverse)"}; // list of options
        sortButton = new JComboBox<>(sortBy);
        sortButton.addActionListener(e -> sortEvents((String) sortButton.getSelectedItem()));
        sortButton.setBackground(Color.WHITE);
        headerPanel.add(sortButton);

        // ------------------------------- filter dropdown -------------------------------------------
        String[] filterOptions = {"All Events", "Deadlines", "Meetings"}; // list of options
        filterButton = new JComboBox<>(filterOptions);
        filterButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterEventsByType((String) filterButton.getSelectedItem());
            }
        });
        filterButton.setBackground(Color.WHITE);
        headerPanel.add(filterButton);

        // ------------------------------- completion checkbox -------------------------------------------
        checkFilter = new JCheckBox("Show Completed Events");
        checkFilter.setSelected(true); // sets checked as true
        checkFilter.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                filterEvents();
            }
        });
        checkFilter.setBackground(Color.WHITE);
        headerPanel.add(checkFilter);

        // ------------------------------- add event button -------------------------------------------
        addEventButton = new JButton("Add Event");
        addEventButton.setBackground(Color.WHITE);
        addEventButton.addActionListener(e -> new AddEventModal(this)); // call modal
        headerPanel.add(addEventButton);
        add(headerPanel, BorderLayout.NORTH);

        // ------------------------------- event list panel -------------------------------------------
        listModel = new DefaultListModel<>();
        eventList = new JList<>(listModel);
        eventList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = eventList.getSelectedIndex();
                if (index >= 0) {
                    highlightEventInCalendar(index); // highlight the selected event in calendar
                }
            }
        });
        //------------------------------------ QUICK VIEW ----------------------------------------------------------
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BorderLayout());
        westPanel.setBackground(new Color(173, 216, 230)); // light blue

        // title label for panel
        JLabel titleLabel = new JLabel("Quick View");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        westPanel.add(titleLabel, BorderLayout.NORTH);

        JScrollPane listScrollPane = new JScrollPane(eventList);
        listScrollPane.setBackground(new Color(173, 216, 230)); // light blue color -- scroll
        eventList.setBackground(new Color(173, 216, 230)); // light blue color -- events
        eventList.setForeground(Color.BLACK); // txt color -- events
        westPanel.add(listScrollPane, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);

        // ------------------------------- calendar display -------------------------------------------
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(calendarPanel); // add scrollbar
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addEvent(Event event)
    {
        events.add(event); // add event to list
        listModel.addElement(event.getName()); // add event name to the list model
        refreshDisplay(); // func call
    }

    private void refreshDisplay()
    {
        calendarPanel.removeAll(); // clear

        // create new/updated list
        for (Event i : events)
        {
            displayEvent(i); // use displayEvent to center events
        }
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // highlight event when selected in quick view
    private void highlightEventInCalendar(int index)
    {
        calendarPanel.removeAll(); // clear highlights
        for (int i = 0; i < events.size(); i++)
        {
            Event event = events.get(i);
            EventPanel eventPanel = new EventPanel(event);
            eventPanel.setBackground(Color.WHITE);

            // highlight event
            if (i == index)
            {
                eventPanel.setBackground(new Color(173, 216, 230));  // light blue highlight
            }

            // panel to center calendar
            JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
            center.setBackground(Color.WHITE);
            center.add(eventPanel);

            calendarPanel.add(center);
        }
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // sort events from dropdown
    private void sortEvents(String sortBy)
    {
        switch (sortBy)
        {
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

    // checkbox filter!
    private void filterEvents()
    {
        calendarPanel.removeAll(); // clear

        for (Event event : events)
        {
            if (checkFilter.isSelected())
            {
                // show complete events only
                if (event instanceof Completable && ((Completable) event).isComplete())
                {
                    displayEvent(event);
                }
            }
            else // if checkFilter is not selected
            {
                if (!(event instanceof Completable) || !((Completable) event).isComplete())
                {
                    displayEvent(event); // display events that are not complete
                }
            }
        }
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // filter events by type
    private void filterEventsByType(String filterBy)
    {
        calendarPanel.removeAll();

        for (Event i : events)
        {
            if (filterBy.equals("All Events"))
            {
                displayEvent(i); // use displayEvent to center events
            } else if (filterBy.equals("Deadlines") && i instanceof Deadline)
            {
                displayEvent(i); // use displayEvent to center events
            } else if (filterBy.equals("Meetings") && i instanceof Meeting)
            {
                displayEvent(i); // use displayEvent to center events
            }
        }
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // method to display the event and center it in the panel
    private void displayEvent(Event event)
    {
        EventPanel eventPanel = new EventPanel(event);
        eventPanel.setBackground(Color.WHITE);

        // extra panel to center calendar
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setBackground(Color.WHITE);
        center.add(eventPanel);

        // add the wrapper panel to the calendar panel
        calendarPanel.add(center);
    }
}
