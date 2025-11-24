
package patient.appointment.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
      // غيري اسم الداتابيس إذا كان غير patients
    private static final String URL = "jdbc:mysql://localhost:3306/patients?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // إذا عندك كلمة سر حطيها هون

    public static Connection getConnection() throws SQLException {
        try {
            // تحميل الدرايفر (مرّة وحدة)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
