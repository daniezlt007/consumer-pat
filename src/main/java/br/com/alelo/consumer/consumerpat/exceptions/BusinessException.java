package br.com.alelo.consumer.consumerpat.exceptions;

import java.util.function.Supplier;

public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        super(msg);
    }

    public static Supplier<BusinessException> supportException(String msg){
        return () -> new BusinessException(msg);
    }
}
