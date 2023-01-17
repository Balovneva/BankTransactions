import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            String accNumber = String.valueOf(i);
            long money = (long) (Math.round(Math.random() * 700_000));
            bank.setAccount(accNumber, new Account(accNumber, money));
        }

        long startSum = bank.getSumAllAccounts();

        Thread thread1 = new Thread(() -> {

            for (int i = 0; i < 1_000_000; i++) {
                String generateFromAccNum = String.valueOf(Math.round(Math.random() * 999));
                String generateToAccNum = String.valueOf(Math.round(Math.random() * 999));

                if (generateFromAccNum.equals(generateToAccNum)) continue;

                long amount = (long) (Math.round(Math.random() * 51_000));

                bank.transferMoney(generateFromAccNum, generateToAccNum, amount);
            }
        });

        Thread thread2 = new Thread(() -> {

            for (int i = 0; i < 1_000_000; i++) {
                String generateFromAccNum = String.valueOf(Math.round(Math.random() * 999));
                String generateToAccNum = String.valueOf(Math.round(Math.random() * 999));

                if (generateFromAccNum.equals(generateToAccNum)) {
                    continue;
                }

                long amount = (long) (Math.round(Math.random() * 51_000));

                bank.transferMoney(generateFromAccNum, generateToAccNum, amount);
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Start sum in bank: " + startSum);
        System.out.println("Sum in bank after transactions: " + bank.getSumAllAccounts());
    }
}
