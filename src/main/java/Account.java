public class Account implements Comparable<Account>{

    private final String accNumber;
    private long money;
    private boolean access = true;

    public Account(String accNumber, long money) {
        this.accNumber = accNumber;
        this.money = money;
    }

    public void setMoney(long money, boolean access) {
        if (access) {
            this.money = money;
        } else {
            this.access = false;
        }
    }

    public long getMoney() {
        return money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public boolean isAccess() {
        return access;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accNumber='" + accNumber + '\'' +
                ", money=" + money +
                ", access=" + access +
                '}';
    }

    @Override
    public int compareTo(Account acc) {
        int thisAccNumber = Integer.parseInt(this.accNumber);
        int anotherAccNumber = Integer.parseInt(acc.getAccNumber());
        boolean result = thisAccNumber < anotherAccNumber;
        return result ? 1 : -1;
    }
}
