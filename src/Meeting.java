import java.time.LocalDateTime;
import java.time.Duration;

public class Meeting extends Event implements Completable {

    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;

    public Meeting(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        super(name, startDateTime);
        this.endDateTime = endDateTime;
        this.location = location;
        this.complete = false;
    }

    public void complete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDuration() {
        Duration duration = Duration.between(super.getDateTime(), getEndDateTime());
        return (int) duration.toMinutes();

    }
}
