package com.demo.api.models;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw");

    private String label;
    TransactionType(String label) {
        this.label = label;
    }

    public static TransactionType findByLabel(String byLabel) {
        for(TransactionType r:TransactionType.values()) {
            if (r.label.equalsIgnoreCase(byLabel))
                return r;
        }
        return null;
    }
}
