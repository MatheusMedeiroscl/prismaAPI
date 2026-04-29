package com.medeiros.prisma_api.domains.stocks;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<StockStatus, String> {

    @Override
    public String convertToDatabaseColumn(StockStatus attribute) {
        if (attribute == null) return null;
        return attribute.getStatus();
    }

    @Override
    public StockStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (StockStatus status : StockStatus.values()) {
            if (status.getStatus().equals(dbData)) return status;
        }

        throw new IllegalArgumentException("INVALID VALUE: " + dbData);
    }
}

