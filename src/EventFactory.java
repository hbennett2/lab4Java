import java.time.LocalDateTime;

public class EventFactory
{
    public static Event createEvent(String eventType, String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location)
    {
        switch (eventType)
        {
            case "Meeting":
                return new Meeting(name, startDateTime, endDateTime, location);
            case "Deadline":
                return new Deadline(name, startDateTime);
            default:
                throw new IllegalArgumentException("Unknown event type");
        }
    }
}

