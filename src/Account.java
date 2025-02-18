public abstract class Account {
    protected String userName;
    protected double balance;

    public Account(String userName, double balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

}
