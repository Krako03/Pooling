# Connection Pooling in Java with HikariCP

## Introduction
This document describes the implementation of a multi-threaded Java application that performs operations on a PostgreSQL database using two approaches:

1. **Custom `DataSource` Implementation:** A single connection is created and reused for all requests.
2. **Using a Connection Pool (HikariCP):** HikariCP is configured as the connection manager to optimize performance.

## What is Connection Pooling?
Connection pooling is a technique that manages and reuses multiple database connections from a predefined set (pool). Instead of creating and closing connections repeatedly, it reuses previously established connections, reducing the overhead associated with these operations.

## Advantages of Connection Pooling
- **Improved Performance:** Reusing connections significantly reduces the time required to establish a new connection for each request.
- **Efficient Resource Management:** The pool controls the maximum number of open connections, preventing database overload.
- **Scalability:** Efficiently handles high volumes of concurrent requests.
- **Reliability:** Providers like HikariCP offer automatic connection validation, improving system stability.

## Disadvantages of Connection Pooling
- **Complex Initial Configuration:** Parameters such as pool size, idle timeout, etc., must be properly adjusted.
- **Potential Underutilization of Connections:** An oversized pool may lead to inefficient resource usage.
- **Warm-up Time:** At system startup, some time is required to fill the pool.

## Performance Test Results
Tests were conducted to compare the total execution time between both implementations:

| Scenario              | Total Execution Time |
|-----------------------|----------------------|
| Custom Implementation | ~50 seconds          |
| Using HikariCP        | ~10 seconds          |

And here are the results, firstly the Custom Single Connection DataSource

<img width="364" alt="Screenshot 2025-03-12 at 12 45 12 p m" src="https://github.com/user-attachments/assets/8a8bdc9b-0e06-46bb-bc1d-116a2f08bb9f" />

And then the results of using a Connection Pool with HikariCP

<img width="366" alt="Screenshot 2025-03-12 at 1 33 14 p m" src="https://github.com/user-attachments/assets/c00f6f62-82f3-4027-9d79-8aa2017d60cc" />

The main difference is that the custom DataSource executes every connection one by one,
and the Hikari one executes them concurrently using 5 connection.

## Conclusions
- The custom implementation proved inefficient, as a single connection blocked the threads, resulting in sequential execution instead of concurrent processing.
- Using HikariCP demonstrated significant efficiency improvements due to connection reuse and concurrent request handling.

## Recommendations
- Use a connection pool in any application that handles a high number of concurrent requests.
- Configure the pool size according to the expected load and system resources.
- Monitor pool behavior to identify potential bottlenecks or connection leaks.