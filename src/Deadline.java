// Event Type -- Deadline logic

import java.time.LocalDateTime;

public class Deadline extends Event implements Completable
{
    private boolean complete;

    // constructor
    public Deadline(String name, LocalDateTime dateTime)
    {
        super(name, dateTime);
        this.complete = false;
    }

    // getters-----------------------------------
    public String getName() {
        return super.getName();
    }
    public void complete() {
        this.complete = true;
    }
    public boolean isComplete() {
        return complete;
    }
}
