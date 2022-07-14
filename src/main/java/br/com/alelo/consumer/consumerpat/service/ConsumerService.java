package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Consumer;

import java.util.List;

public interface ConsumerService {

    public List<Consumer> listAllConsumers();
    public void createConsumer(Consumer consumer);
    public void updateConsumer(Consumer consumer);
    public void setBalance(int cardNumber, double value);
    public void buy(int establishmentType, String establishmentName, int cardNumber, String productDescription, double value);

}
