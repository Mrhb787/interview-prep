// Requirements:
// The system should generate even and odd numbers alternatively.
// There should be two threads, one for generating even numbers and one for generating odd numbers.
// Another thread should print the generated numbers alternatively.

// Components:
// Even Number Generator: Thread responsible for generating even numbers.
// Odd Number Generator: Thread responsible for generating odd numbers.
// Printer: Thread responsible for printing the generated numbers.
// Synchronization Mechanism: Used to ensure that numbers are generated and printed in the correct order.

// Algorithm:
// Two threads (Even Number Generator and Odd Number Generator) generate even and odd numbers alternately.
// A Printer thread prints the generated numbers alternatively.
// Synchronization mechanisms such as locks or semaphores are used to ensure the correct order of generation and printing.

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Number Generator interface
interface NumberGenerator {
    void generate();
}

// Even Number Generator class
class EvenNumberGenerator implements NumberGenerator {
    private final Printer printer;
    private final Lock lock;
    private final Condition evenCondition;
    private final Condition oddCondition;
    private int num;

    public EvenNumberGenerator(Printer printer) {
        this.printer = printer;
        this.lock = new ReentrantLock();
        this.evenCondition = lock.newCondition();
        this.oddCondition = lock.newCondition();
        this.num = 0;
    }

    @Override
    public void generate() {
        while (true) {
            lock.lock();
            try {
                while (printer.isEvenPrinted()) {
                    evenCondition.await();
                }
                num += 2;
                printer.print(num);
                oddCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

// Odd Number Generator class
class OddNumberGenerator implements NumberGenerator {
    private final Printer printer;
    private final Lock lock;
    private final Condition evenCondition;
    private final Condition oddCondition;
    private int num;

    public OddNumberGenerator(Printer printer) {
        this.printer = printer;
        this.lock = new ReentrantLock();
        this.evenCondition = lock.newCondition();
        this.oddCondition = lock.newCondition();
        this.num = 1;
    }

    @Override
    public void generate() {
        while (true) {
            lock.lock();
            try {
                while (!printer.isEvenPrinted()) {
                    oddCondition.await();
                }
                num += 2;
                printer.print(num);
                evenCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

// Printer class
class Printer {
    private boolean evenPrinted;

    public Printer() {
        this.evenPrinted = false;
    }

    public synchronized boolean isEvenPrinted() {
        return evenPrinted;
    }

    public synchronized void print(int num) {
        System.out.println(num);
        evenPrinted = !evenPrinted;
    }
}

// Main class to test the even-odd number generation and printing
public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        EvenNumberGenerator evenGenerator = new EvenNumberGenerator(printer);
        OddNumberGenerator oddGenerator = new OddNumberGenerator(printer);

        Thread evenThread = new Thread(evenGenerator::generate);
        Thread oddThread = new Thread(oddGenerator::generate);

        evenThread.start();
        oddThread.start();
    }
}
