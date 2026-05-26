package com.medeiros.prisma_api.domains.sales;

public enum PaymentMethod {
    PIX("pix"),
    CASH("cash"),
    BANK_SLIP("bank_slip");

    private final String value;
    PaymentMethod(String value) {this.value = value;}

    public String getValue() {return value;}
}
