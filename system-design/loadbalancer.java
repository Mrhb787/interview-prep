// WHAT IS LOAD BALANCER ?
// In simple terms, a load balancer acts like a traffic cop for web servers. 
// Imagine a busy intersection where cars (requests) are coming from different directions. 
// The load balancer's job is to direct these cars to different lanes (servers) based on factors like how busy each lane is, 
// which lane is closest, or which lane has the shortest line.

// Similarly, a load balancer sits between clients (like web browsers) and a group of backend servers (like web servers). 
// It receives incoming requests from clients and decides which backend server should handle each request. 
// This helps evenly distribute the workload across multiple servers, ensuring that no single server gets overwhelmed with 
// traffic while optimizing response times and resource usage. In essence, a load balancer balances the "load" of incoming requests across 
// available servers to maintain performance, availability, and reliability of web applications.

// 5 main points for Load Balancer

// Traffic Distribution:
// The primary function of a load balancer is to evenly distribute incoming network traffic across multiple servers or resources. 
// This ensures efficient utilization of server resources and prevents any single server from becoming overwhelmed with requests.
  
// Health Checking and Monitoring:
// Load balancers should continuously monitor the health and availability of backend servers or resources. 
// This involves performing health checks to assess server responsiveness, resource utilization, and detecting failures or degraded performance. 
// Servers deemed unhealthy should be temporarily removed from the pool of available servers to prevent routing traffic to them.
  
// Load Balancing Algorithms:
// Implement various load balancing algorithms to determine how incoming traffic is distributed among backend servers. 
// Common algorithms include round-robin, least connections, weighted round-robin, least response time, and IP hash. 
// The choice of algorithm depends on factors such as traffic patterns, server capacities, and application requirements.
  
// Scalability and High Availability:
// Load balancers should be designed to scale horizontally to handle increasing traffic volumes and accommodate additional backend servers or resources as needed. 
// Additionally, they should be resilient to failures and designed for high availability to minimize service disruptions. This may involve implementing redundancy, 
// failover mechanisms, and automatic recovery.

// Monitoring and Logging:
// Load balancers should provide comprehensive monitoring and logging capabilities to track traffic patterns, performance metrics, and security events. 
// This data can be used for troubleshooting, capacity planning, and compliance purposes. Additionally, load balancers should integrate with existing 
// monitoring and logging systems to provide a unified view of the infrastructure.

// ========= CODE =========

import java.util.ArrayList;
import java.util.List;

// Represents a backend server
class Server {
    private String id;
    private boolean healthy;

    public Server(String id) {
        this.id = id;
        this.healthy = true; // Assume all servers start as healthy
    }

    public String getId() {
        return id;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}

// Represents a simple round-robin load balancer
class LoadBalancer {
    private List<Server> servers;
    private int currentIndex;

    public LoadBalancer() {
        this.servers = new ArrayList<>();
        this.currentIndex = 0;
    }

    // Add a server to the load balancer
    public void addServer(Server server) {
        servers.add(server);
    }

    // Balance the load and return the next available server
    public Server balanceLoad() {
        int totalServers = servers.size();
        if (totalServers == 0) {
            return null;
        }

        while (true) {
            Server nextServer = servers.get(currentIndex);
            currentIndex = (currentIndex + 1) % totalServers; // Move to the next server (round-robin)
            if (nextServer.isHealthy()) {
                return nextServer;
            }
        }
    }

    // Health check all servers and mark unhealthy ones
    public void healthCheck() {
        for (Server server : servers) {
            // Perform a health check (e.g., ping the server)
            // For simplicity, we assume all servers are healthy in this example
            // In a real-world scenario, you would perform actual health checks
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Create a load balancer
        LoadBalancer loadBalancer = new LoadBalancer();

        // Add some backend servers
        loadBalancer.addServer(new Server("Server1"));
        loadBalancer.addServer(new Server("Server2"));
        loadBalancer.addServer(new Server("Server3"));

        // Perform a health check
        loadBalancer.healthCheck();

        // Simulate incoming requests
        for (int i = 0; i < 10; i++) {
            Server server = loadBalancer.balanceLoad();
            if (server != null) {
                System.out.println("Request routed to server: " + server.getId());
            } else {
                System.out.println("No healthy servers available.");
            }
        }
    }
}
