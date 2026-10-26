import java.sql.SQLException;
import java.time.LocalTime;
public class Account {

    double balance;
    String firstName;
    String lastName;
    int pin;
    DBmanager manager;

    public Account(double balance, String firstName, String lastName, int pin) throws SQLException {
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.manager = new DBmanager();
    }

    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String name) {
        this.firstName = name;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String name) {
        this.lastName = name;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void withDraw(double amount) throws SQLException {
        this.balance = balance - amount;
        manager.changeBalance(manager.getID(getfirstName(), getlastName()), this.balance);
        sendToLog("You have withdrew $" + amount);
    }

    public void deposit(double amount) throws SQLException {
        this.balance = balance + amount;
        manager.changeBalance(manager.getID(getfirstName(), getlastName()), this.balance);
        sendToLog("You have deposited $" + amount);
    }

    public void changeName(String firstName, String lastName) throws SQLException {
        setfirstName(firstName);
        setlastName(lastName);
        int id = manager.getID(getfirstName(), getlastName());
        manager.modifyName(id, getfirstName(), getlastName());
        sendToLog("Your name has been Changed");
    }

    public void changePin(int pin) throws SQLException {
        int id = manager.getID(getfirstName(), getlastName());
        manager.modifyPin(id, pin);
        sendToLog("Your pin has been Changed");
    }

    public void changeActive(boolean active) throws SQLException {
        int id = manager.getID(getfirstName(), getlastName());
        manager.modifyActive(id, active);
        sendToLog("You have changed your status");
    }

    public void insertPerson(String firstName, String lastName, double balance,
                             long cardNumber, boolean active) throws SQLException {
        manager.insertCard(firstName, lastName, cardNumber, balance, active);
        sendToLog("Your card has been inserted");
    }

    public void viewCard(String firstName, String lastName) throws SQLException {
        int id = manager.getID(firstName, lastName);
        manager.viewCardInformation(id);
        sendToLog("Your card has been viewed");
    }

    public void finished() throws SQLException {
        manager.close();
    }

    public void sendToLog(String message) {
        History logging = new History(LocalTime.now(), message);
        logging.log();
    }

}
