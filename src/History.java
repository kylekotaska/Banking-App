import java.time.LocalTime;
public class History {

    LocalTime date;
    String message;
    public History(LocalTime date, String message) {
        this.date = LocalTime.now();
        this.message = message;
    }

    public LocalTime getDate() {
        return date;
    }
    public void setDate(LocalTime date) {
        this.date = date;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void log() {
        System.out.println(this.date + " " + this.message);
    }
}
