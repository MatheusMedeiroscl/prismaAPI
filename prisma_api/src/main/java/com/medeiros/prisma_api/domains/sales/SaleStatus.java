package com.medeiros.prisma_api.domains.sales;

public enum SaleStatus {
    PENDING("pending"),
    PAID("paid"),
    RESERVED("reserved"),
    CANCELLED("cancelled"),;

    private final String value;

    SaleStatus(String value) {this.value = value;}
    public String getValue() {return value;}
}
