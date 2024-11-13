// Event type -- meeting logic
import java.time.LocalDateTime;
import java.time.Duration;

public class Meeting extends Event implements Completable
{
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;

    // constructor
    public Meeting (String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location)
    {
        super(name, startDateTime);
        this.complete = false;
        this.location = location;
        this.endDateTime = endDateTime;
    }

    // getters ----------------------------------------------
    public LocalDateTime getEndDateTime()
    {
        return endDateTime;
    }
    public String getLocation()
    {
        return location;
    }
    public void complete()
    {
        this.complete = true;
    }
    public boolean isComplete()
    {
        return complete;
    }
    // calc duration MATH :)
    public int getDuration()
    {
        Duration duration = Duration.between(super.getDateTime(), getEndDateTime());
        return (int) duration.toMinutes();
    }
}
