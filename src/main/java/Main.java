import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        CustomDataSource dataSource = new CustomDataSource();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            Connection connection = dataSource.getConnection();
            executor.submit(new WorkerThread(connection));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
}
