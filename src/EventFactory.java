import java.time.LocalDateTime;

public class EventFactory
{
    public static Event createEvent(String eventType, String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location)
    {
        return switch (eventType) {
            case "Meeting" -> new Meeting(name, startDateTime, endDateTime, location);
            case "Deadline" -> new Deadline(name, startDateTime);
            default -> throw new IllegalArgumentException("Unknown event type");
        };
    }
}

