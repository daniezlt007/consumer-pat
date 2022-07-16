package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CardType {
    FOOD(1),
    DRUGSTORE(2),
    FUEL(3);

    private Integer code;

    public static CardType getByTypeEstablishment(Integer type){
        return Arrays.stream(CardType.values()).filter(
                cardType -> cardType.getCode().equals(type))
                .findFirst()
                .orElseThrow(BusinessException.supportException("Property type is not mapped"));
    }
}
