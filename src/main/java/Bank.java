import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class Bank {

    private Hashtable<String, Account> accounts = new Hashtable<>();
    private final Random random = new Random();

    public boolean isFraud()
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transferMoney(String fromAccountNum, String toAccountNum, long amount) {
        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);

        int fromId = Integer.parseInt(fromAccountNum);
        int toId = Integer.parseInt(toAccountNum);
        if (fromId < toId) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        doTransfer(fromAccount, toAccount, amount);
                    }
                }
        } else {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    doTransfer(fromAccount, toAccount, amount);
                }
            }
        }
    }

    public void doTransfer(Account fromAccount, Account toAccount, long amount) {

        if (!fromAccount.isAccess() || !toAccount.isAccess()) {
            System.out.println("Операция отклонена. Один из счетов заблокирован");
            return;
        }

        boolean access = true;

        if (fromAccount.getMoney() >= amount) {
            if (amount > 50000) {
                try {
                    if (isFraud()) {
                        access = false;
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            fromAccount.setMoney(fromAccount.getMoney() - amount, access);
            toAccount.setMoney(toAccount.getMoney() + amount, access);
            String result = access ? "Транзакция выполнена" :
                    "Не пройдена проверка безопасности. Счета заблокированы";
            System.out.println(result);
        } else {
            System.out.println("Недостаточно средств для перевода");
        }
    }




    public synchronized long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public synchronized long getSumAllAccounts() {

        long sumMoney = 0;

        for (Map.Entry<String, Account> item : accounts.entrySet()) {
            sumMoney += item.getValue().getMoney();
        }

        return sumMoney;
    }

    public synchronized boolean getAccess(String accountNum) {
        return accounts.get(accountNum).isAccess();
    }

    public synchronized Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccount(String accNumber, Account account) {
        accounts.put(accNumber, account);
    }
}
