import java.sql.Connection;
import java.sql.Statement;

public class WorkerThread implements Runnable {
    private final Connection connection;

    public WorkerThread(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (Statement stmt = connection.createStatement()) {
            long startTime = System.currentTimeMillis();
            stmt.execute("SELECT pg_sleep(5)"); // Simula una tarea prolongada
            long endTime = System.currentTimeMillis();
            System.out.println("Thread " + Thread.currentThread().getName() +
                    " finished in " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
