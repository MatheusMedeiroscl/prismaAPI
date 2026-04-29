package com.medeiros.prisma_api.domains.Movements;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MovementTypeConverter implements AttributeConverter<MovementType, String> {

    @Override
    public String convertToDatabaseColumn(MovementType type) {
        if (type == null) return null;
        return type.getMoviment(); // usa o valor em minúsculo que você já definiu no enum
    }

    @Override
    public MovementType convertToEntityAttribute(String value) {
        if (value == null) return null;
        for (MovementType type : MovementType.values()) {
            if (type.getMoviment().equals(value)) return type;
        }
        throw new IllegalArgumentException("Valor inválido: " + value);
    }
}