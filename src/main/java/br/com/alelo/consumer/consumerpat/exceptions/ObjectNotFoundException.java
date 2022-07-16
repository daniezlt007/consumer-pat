package br.com.alelo.consumer.consumerpat.exceptions;

import java.util.function.Supplier;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public static Supplier<ObjectNotFoundException> supplier(String msg) {
        return () -> new ObjectNotFoundException(msg);
    }

}
