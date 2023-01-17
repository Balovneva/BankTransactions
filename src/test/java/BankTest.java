import junit.framework.TestCase;
import org.junit.Test;

public class BankTest extends TestCase {
    Bank bank = new Bank();

    @Override
    protected void setUp() throws Exception {
        bank.setAccount("1", new Account("1", 100_000));
        bank.setAccount("2", new Account("2", 200_000));
        bank.setAccount("3", new Account("3", 300_000));
        bank.setAccount("4", new Account("4", 400_000));
        bank.setAccount("5", new Account("5", 500_000));
    }

    @Test
    public void testTransfer() {
        bank.transferMoney("1", "2", 10_000);

        long actual1 = bank.getBalance("1");
        long excepted1 = 90_000;
        long actual2 = bank.getBalance("2");
        long excepted2 = 210_000;

        assertEquals(excepted1, actual1);
        assertEquals(excepted2, actual2);
    }

    @Test
    public void testTransferForLargeSum() {
        bank.transferMoney("1", "2", 60_000);

        long actual1;
        long excepted1;
        long actual2;
        long excepted2;

        if (!bank.getAccess("1")) {
            actual1 = bank.getBalance("1");
            excepted1 = 10_0000;
            actual2 = bank.getBalance("2");
            excepted2 = 200_000;
        } else {
            actual1 = bank.getBalance("1");
            excepted1 = 40_000;
            actual2 = bank.getBalance("2");
            excepted2 = 260_000;
        }

        assertEquals(excepted1, actual1);
        assertEquals(excepted2, actual2);
    }

    @Test
    public void testTwoThread() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            bank.transferMoney("3", "2", 10_000);
            bank.transferMoney("1", "3", 10_000);
            System.out.println(Thread.currentThread().getName());
        });

        Thread thread2 = new Thread(() -> {
            bank.transferMoney("3", "2", 10_000);
            bank.transferMoney("1", "3", 10_000);
            System.out.println(Thread.currentThread().getName());
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        long actual1 = bank.getBalance("1");
        long excepted1 = 80_000;
        long actual2 = bank.getBalance("2");
        long excepted2 = 220_000;
        long actual3 = bank.getBalance("3");
        long excepted3 = 300_000;

        long actual4 = bank.getSumAllAccounts();
        long excepted4 = 1_500_000;

        assertEquals(excepted1, actual1);
        assertEquals(excepted2, actual2);
        assertEquals(excepted3, actual3);
        assertEquals(excepted4, actual4);
    }

    @Test
    public void testMultithreading() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            bank.transferMoney("3", "2", 60_000);
            bank.transferMoney("1", "3", 10_000);
            System.out.println(Thread.currentThread().getName());
        });

        Thread thread2 = new Thread(() -> {
            bank.transferMoney("3", "2", 10_000);
            bank.transferMoney("1", "3", 10_000);
            System.out.println(Thread.currentThread().getName());
        });

        Thread thread3 = new Thread(() -> {
            bank.transferMoney("3", "2", 10_000);
            bank.transferMoney("1", "3", 10_000);
            System.out.println(Thread.currentThread().getName());
        });

        Thread thread4 = new Thread(() -> {
            bank.transferMoney("3", "2", 10_000);
            bank.transferMoney("1", "3", 10_000);
            System.out.println(Thread.currentThread().getName());
        });

        Thread thread5 = new Thread(() -> {
            bank.transferMoney("3", "2", 10_000);
            bank.transferMoney("4", "1", 50_000);
            System.out.println(Thread.currentThread().getName());
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        long actual1;
        long excepted1;
        long actual2;
        long excepted2;
        long actual3;
        long excepted3;
        long actual4;
        long excepted4;

        if (!bank.getAccess("3")) {
            actual1 = bank.getBalance("1");
            excepted1 = 150_000;
            actual2 = bank.getBalance("2");
            excepted2 = 200_000;
            actual3 = bank.getBalance("3");
            excepted3 = 300_000;
            actual4 = bank.getBalance("4");
            excepted4 = 350_000;
        } else {
            actual1 = bank.getBalance("1");
            excepted1 = 110_000;
            actual2 = bank.getBalance("2");
            excepted2 = 300_000;
            actual3 = bank.getBalance("3");
            excepted3 = 240_000;
            actual4 = bank.getBalance("4");
            excepted4 = 350_000;
        }

        long actual5 = bank.getSumAllAccounts();
        long excepted5 = 1_500_000;

        assertEquals(excepted1, actual1);
        assertEquals(excepted2, actual2);
        assertEquals(excepted3, actual3);
        assertEquals(excepted4, actual4);
        assertEquals(excepted5, actual5);
    }
}
