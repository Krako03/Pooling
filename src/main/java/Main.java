import javax.sql.DataSource;
import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        CustomDataSource customDataSource = new CustomDataSource();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            Connection connection = customDataSource.getConnection();
            executor.submit(new WorkerThread(connection));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");

        DataSource hikariDataSource = HikariCPDataSource.getDataSource();
        ExecutorService executor2 = Executors.newFixedThreadPool(10);

        long startTime2 = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            executor2.submit(() -> {
                try (Connection connection = hikariDataSource.getConnection()) {
                    new WorkerThread(connection).run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor2.shutdown();
        while (!executor2.isTerminated()) {}

        long endTime2 = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime2 - startTime2) + " ms");
    }
}
