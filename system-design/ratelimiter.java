// 5 main points for Rate Limiter System Design:

// Throughput Limiting
// Implement a mechanism to restrict the number of requests processed per unit time to prevent overload on backend services or APIs.

// Scalability
// Design the system to scale horizontally across multiple servers or nodes to handle a large volume of requests in a distributed environment efficiently.

// Dynamic Configuration
// Provide the ability to dynamically update rate limiting rules and configurations without downtime, enabling adaptive response to changing traffic patterns.

// Concurrency Handling
// Ensure efficient handling of concurrent requests while maintaining accurate rate limiting, avoiding contention and bottlenecks in processing.

// Monitoring and Logging
// Implement comprehensive monitoring and logging mechanisms to track rate limiting events, system performance, and traffic patterns for 
// troubleshooting and analysis purposes.

import java.util.ArrayDeque;
import java.util.Deque;

public class RateLimiter {
    private int maxRequests; // Maximum number of requests allowed within the time window
    private long timeWindowMillis; // Time window (in milliseconds) for limiting the requests
    private Deque<Long> requestTimes; // Queue to store timestamps of recent requests

    public RateLimiter(int maxRequests, long timeWindowMillis) {
        this.maxRequests = maxRequests;
        this.timeWindowMillis = timeWindowMillis;
        this.requestTimes = new ArrayDeque<>(maxRequests);
    }

    // Method to check if a request should be allowed or not
    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis(); // Get the current timestamp
        cleanExpiredRequests(currentTime); // Clean up expired requests from the queue

        // Check if the number of requests within the time window is less than the maximum allowed
        if (requestTimes.size() < maxRequests) {
            requestTimes.addLast(currentTime); // Add current request timestamp to the queue
            return true; // Allow the request
        }

        // If the number of requests exceeds the maximum allowed, check the time window
        long earliestRequestTime = requestTimes.getFirst(); // Get the timestamp of the earliest request
        long timeSinceEarliestRequest = currentTime - earliestRequestTime; // Calculate time elapsed since the earliest request

        // If time elapsed is less than the time window, deny the request
        if (timeSinceEarliestRequest < timeWindowMillis) {
            return false; // Rate limit exceeded
        } else {
            // If time elapsed is greater than the time window, remove the earliest request timestamp
            requestTimes.removeFirst();
            // Add the current request timestamp to the queue
            requestTimes.addLast(currentTime);
            return true; // Allow the request
        }
    }

    // Method to clean up expired requests from the queue
    private void cleanExpiredRequests(long currentTime) {
        // Remove requests from the queue whose timestamps are older than the time window
        while (!requestTimes.isEmpty() && currentTime - requestTimes.getFirst() >= timeWindowMillis) {
            requestTimes.removeFirst();
        }
    }

    // Main method for testing the rate limiter
    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter(5, 1000); // Allow maximum 5 requests per second

        // Simulating requests
        for (int i = 0; i < 10; i++) {
            if (rateLimiter.allowRequest()) {
                System.out.println("Request " + (i + 1) + ": Allowed");
            } else {
                System.out.println("Request " + (i + 1) + ": Denied (Rate Limit Exceeded)");
            }

            // Simulating some delay between requests
            try {
                Thread.sleep(200); // Adjust this delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
