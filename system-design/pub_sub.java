// Components:
// Publisher: Publishes messages to topics.
// Subscriber: Subscribes to topics and receives messages.
// Topic: A channel for publishing and subscribing messages.
// Partition: Subdivision of a topic to distribute load among consumers.
// Consumer Group: Group of consumers that share the message processing load.
// Partition Manager: Manages partitions for each topic and assigns partitions to consumers of a consumer group.
// Signal Handler: Listens for SIGINT and SIGTERM signals to gracefully close subscribers.

// Algorithm:
// Publishers publish messages to topics.
// Subscribers subscribe to topics and are assigned partitions based on the number of partitions defined for the topic.
// Each consumer within the same consumer group reads from one partition only.
// Subscribers run in parallel to process messages.
// Signal handlers are installed to gracefully close subscribers upon receiving SIGINT or SIGTERM signals.

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

// Message class representing a message
class Message {
    private final String content;

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

// Partition class representing a partition of a topic
class Partition {
    private final int id;

    public Partition(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

// Topic class representing a messaging topic with partitions
class Topic {
    private final String name;
    private final int numPartitions;
    private final List<Partition> partitions;

    public Topic(String name, int numPartitions) {
        this.name = name;
        this.numPartitions = numPartitions;
        this.partitions = new ArrayList<>();
        for (int i = 0; i < numPartitions; i++) {
            partitions.add(new Partition(i));
        }
    }

    public String getName() {
        return name;
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

    public int getNumPartitions() {
        return numPartitions;
    }
}

// Consumer class representing a subscriber/consumer
class Consumer implements Runnable {
    private final String groupId;
    private final Topic topic;
    private final BlockingQueue<Message> messageQueue;
    private final AtomicBoolean running;

    public Consumer(String groupId, Topic topic) {
        this.groupId = groupId;
        this.topic = topic;
        this.messageQueue = new LinkedBlockingQueue<>();
        this.running = new AtomicBoolean(true);
    }

    public void run() {
        try {
            while (running.get()) {
                // Consume messages from assigned partition
                for (Partition partition : topic.getPartitions()) {
                    if (partition.getId() % topic.getNumPartitions() == Math.abs(groupId.hashCode()) % topic.getNumPartitions()) {
                        Message message = messageQueue.poll(100, TimeUnit.MILLISECONDS);
                        if (message != null) {
                            System.out.println("Consumer " + groupId + " received message: " + message.getContent() + " from partition " + partition.getId());
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running.set(false);
    }

    public void handleMessage(Message message) {
        messageQueue.offer(message);
    }
}

// Publisher class representing a publisher
class Publisher {
    private final Topic topic;
    private final ExecutorService executor;

    public Publisher(Topic topic) {
        this.topic = topic;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void publish(Message message) {
        executor.submit(() -> {
            // Publish message to topic
            System.out.println("Published message: " + message.getContent() + " to topic: " + topic.getName());
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}

// Topic Manager class representing a manager for topics and partitions
class TopicManager {
    private final Map<String, Topic> topics;

    public TopicManager() {
        this.topics = new HashMap<>();
    }

    public void createTopic(String name, int numPartitions) {
        topics.put(name, new Topic(name, numPartitions));
    }

    public Topic getTopic(String name) {
        return topics.get(name);
    }
}

// Signal Handler class to handle SIGINT and SIGTERM signals
class SignalHandler implements Runnable {
    private final List<Consumer> consumers;

    public SignalHandler(List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down subscribers gracefully...");
            for (Consumer consumer : consumers) {
                consumer.stop();
            }
        }));
    }
}

// Main class to test the messaging service
public class Main {
    public static void main(String[] args) {
        TopicManager topicManager = new TopicManager();
        topicManager.createTopic("test-topic", 3); // Create a topic with 3 partitions

        Topic topic = topicManager.getTopic("test-topic");

        Publisher publisher = new Publisher(topic);
        List<Consumer> consumers = new ArrayList<>();
        SignalHandler signalHandler = new SignalHandler(consumers);
        new Thread(signalHandler).start();

        // Create consumers
        for (int i = 0; i < 3; i++) {
            Consumer consumer = new Consumer("group-1", topic);
            consumers.add(consumer);
            new Thread(consumer).start();
        }

        // Publish messages
        for (int i = 0; i < 10; i++) {
            Message message = new Message("Message " + (i + 1));
            publisher.publish(message);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
