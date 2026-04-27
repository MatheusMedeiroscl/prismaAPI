package com.medeiros.prisma_api.domains.stocks;

import com.medeiros.prisma_api.domains.Movements.MovementType;

public enum StockStatus {
    ORDER("order"),
    AVAILABLE("available");

    private final String status;


    StockStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public static  StockStatus from(MovementType type){
        return switch (type) {
            case IN -> AVAILABLE;
            case OUT -> null;
            case ORDER -> ORDER;
            case RESERVED -> null;
            case ADJUSTMENT -> null;
        };
    }
}
