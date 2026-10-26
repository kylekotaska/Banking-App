import java.sql.SQLException;
import java.time.LocalTime;
public class Main {
    public static void main(String[] args) throws SQLException {
        Account accounting = new Account(100, "Kyle", "Kotaska", 1234);
        accounting.changeName("Mclovin", "Kotaska");
        accounting.changePin(6969);
        accounting.changeActive(true);
        accounting.insertPerson("Power", "BestGirl", 4200000,
                33550337, true );
        accounting.viewCard("Power", "BestGirl");

        accounting.deposit(500);
        accounting.withDraw(200);

        accounting.finished();
    }
}