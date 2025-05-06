package main.java.LoadBalancer.Clients;

public enum RequestType {

    CHECK_BALANCE("check_balance"),
    TRANSFER("transfer"),
    WITHDRAW("withdraw"),
    DEPOSIT("deposit");

    private final String value;

    RequestType(String value) {
        this.value = value;
    }
}
