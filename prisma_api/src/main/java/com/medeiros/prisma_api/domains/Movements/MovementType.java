package com.medeiros.prisma_api.domains.Movements;

import com.medeiros.prisma_api.domains.stocks.StockStatus;

public enum MovementType {
    IN("in"),
    OUT("out"),
    ORDER("order"),
    RESERVED("reserved"),
    ADJUSTMENT("adjustment");

    private final String moviment;

    MovementType(String type){this.moviment = type;}

    public String getMoviment() {
        return moviment;
    }

    public static MovementType from(StockStatus status){
        return switch (status){
            case ORDER -> ORDER;
            case AVAILABLE -> IN;
        };
    }
}
