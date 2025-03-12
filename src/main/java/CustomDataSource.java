import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CustomDataSource implements DataSource {
    private Connection cachedConnection;

    @Override
    public synchronized Connection getConnection() throws SQLException {
        if (cachedConnection == null || cachedConnection.isClosed()) {
            cachedConnection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/exampleDB",
                    "admin",
                    "i2345678"
            );
        }
        return cachedConnection;
    }

    // Métodos no implementados
    @Override public Connection getConnection(String username, String password) { return null; }
    @Override public PrintWriter getLogWriter() { return null; }
    @Override public void setLogWriter(PrintWriter out) {}
    @Override public void setLoginTimeout(int seconds) {}
    @Override public int getLoginTimeout() { return 0; }
    @Override public Logger getParentLogger() { return null; }
    @Override public <T> T unwrap(Class<T> iface) { return null; }
    @Override public boolean isWrapperFor(Class<?> iface) { return false; }
}
