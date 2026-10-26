import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBmanager {

    private final Connection conn;

    public DBmanager() throws SQLException {
        String url = "jdbc:sqlite:mydatabase.db";

        conn = DriverManager.getConnection(url);
        System.out.println("Connected");
        createTable();

        try {
            createTable();
        } catch (SQLException e) {
            conn.close();
            throw e;
        }
    }

    private void createTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                firstName TEXT,
                lastName TEXT,
                cardNumber VARCHAR,
                balance DECIMAL(15, 2),
                pin CHAR(4),
                isActive BOOLEAN,
                created_at TEXT DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }

    public void insertCard(String first, String last,
                           long cardNumber, double balance,
                           boolean active) throws SQLException {

        String sql = """
            INSERT INTO users
                (firstName, lastName, cardNumber, balance, isActive)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, first);
            pstmt.setString(2, last);
            pstmt.setLong(3, cardNumber);
            pstmt.setDouble(4, balance);
            pstmt.setBoolean(5, active);

            pstmt.executeUpdate();
        }
    }

    public void viewCardInformation(int id) throws SQLException {
        String sql = """
        SELECT id, firstName, lastName, cardNumber, balance
        FROM users
        WHERE id = ?
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));

                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String cardNumber = rs.getString("cardNumber");
                    double balance = rs.getDouble("balance");

                    System.out.println(
                            "First Name: " + firstName
                                    + " Last Name: " + lastName
                                    + " Card Number: " + cardNumber
                                    + " Balance: " + balance
                    );
                }
            }
        }
    }

    public void modifyName(int id, String first, String last)
            throws SQLException {

        String sql = """
            UPDATE users
            SET firstName = ?, lastName = ?
            WHERE id = ?
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, first);
            pstmt.setString(2, last);
            pstmt.setInt(3, id);

            pstmt.executeUpdate();
        }
    }

    public void modifyPin(int id, int pin) throws SQLException {
        String sql = "UPDATE users SET pin = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, pin);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();
        }
    }

    public void modifyActive(int id, boolean isActive)
            throws SQLException {

        String sql = "UPDATE users SET isActive = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isActive);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();
        }
    }

    public int getID(String firstName, String lastName)
            throws SQLException {

        String sql = """
            SELECT id FROM users
            WHERE firstName = ? AND lastName = ?
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        return -1;
    }

    public void changeBalance (int id, double balance) throws SQLException {
        String sql = """
        UPDATE Users
        SET balance = ?
        WHERE id = ?
        """;

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, balance);
                pstmt.setInt(2, id);
            }

    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}