package DB.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSQL {
    public static String DB_URL = "jdbc:sqlite:C:\\Users\\moham\\IdeaProjects\\untitled5\\src\\main\\resources\\database.db";
    private static Connection conn;
    private static boolean isConnected = false ;

    private DatabaseSQL() {}

    public static Connection getConnection() throws SQLException {
        if(!isConnected) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(DB_URL);
                isConnected = true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void cleanTables(){
        try (Statement st = getConnection().createStatement()) {
            st.executeUpdate("""
                    DROP TABLE IF EXISTS Suppliers;
                """);
            st.executeUpdate("""
                    DROP TABLE IF EXISTS contactPerson;
                """);
            st.executeUpdate("""
                    DROP TABLE IF EXISTS Contracts;
                """);
            st.executeUpdate("""
                    DROP TABLE IF EXISTS ContractTerms;
                """);
            st.executeUpdate("""
                    DROP TABLE IF EXISTS Orders;
                """);
            st.executeUpdate("""
                    DROP TABLE IF EXISTS OrderItems;
                """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTables() {
        try {

            try (Statement st = getConnection().createStatement()) {
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Suppliers(
                        name TEXT NOT NULL,
                        address TEXT NOT NULL,
                        phone TEXT NOT NULL,
                        number PRIMARY KEY,
                        paymentTerms TEXT NOT NULL,
                        deliveryDays TEXT NOT NULL,
                        type TEXT NOT NULL
                    );
                """);
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS contactPerson(
                        sup_number TEXT NOT NULL,
                        name      TEXT NOT NULL,
                        phone       TEXT NOT NULL,
                        FOREIGN KEY (sup_number) REFERENCES Suppliers(number) ON DELETE CASCADE
                        PRIMARY KEY (sup_number , name , phone)
                    );
                """);
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Contracts(
                        id INTEGER PRIMARY KEY AUTOINCREMENT ,
                        supplierNumber TEXT NOT NULL,
                        FOREIGN KEY (supplierNumber) REFERENCES Suppliers(number) ON DELETE CASCADE
                    );
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS ContractTerms(
                        itemCode TEXT NOT NULL,
                        itemName TEXT NOT NULL,
                        minimalAmount INTEGER NOT NULL,
                        baseDiscount FLOAT NOT NULL,
                        price FLOAT NOT NULL,
                        contractId INTEGER NOT NULL,
                        FOREIGN KEY (contractId) REFERENCES Contracts(id) ON DELETE CASCADE
                        PRIMARY KEY (contractId , itemCode)
                    );
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Orders(
                        supplierNumber TEXT NOT NULL,
                        address TEXT NOT NULL,
                        date TEXT NOT NULL,
                        phone TEXT NOT NULL,
                        status TEXT NOT NULL,
                        id INTEGER PRIMARY KEY AUTOINCREMENT ,
                        FOREIGN KEY (supplierNumber) REFERENCES Suppliers(number)
                    );
                """);

                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS OrderItems(
                        itemCode TEXT NOT NULL,
                        quantity INTEGER NOT NULL,
                        totalPrice FLOAT NOT NULL,
                        orderId INTEGER NOT NULL,
                        FOREIGN KEY (orderId) REFERENCES Orders(id)
                    );
                """);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
