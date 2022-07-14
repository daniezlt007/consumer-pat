package br.com.alelo.consumer.consumerpat.service;

import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.respository.ExtractRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ConsumerServiceImpl implements ConsumerService{

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private ExtractRepository extractRepository;

    @Override
    public List<Consumer> listAllConsumers() {
        return this.consumerRepository.getAllConsumersList();
    }

    @Override
    public void createConsumer(Consumer consumer) {
        if(consumer != null){
            this.consumerRepository.save(consumer);
        }
    }

    @Override
    public void updateConsumer(Consumer consumer) {
        if(consumer != null){
            Consumer consumerUpdate = amountObjectConsumer(consumer);
            this.consumerRepository.save(consumerUpdate);
        }
    }

    @Override
    public void setBalance(int cardNumber, double value) {
        Consumer consumer = null;
        consumer = this.consumerRepository.findByDrugstoreNumber(cardNumber);
        if(consumer != null) {
            // é cartão de farmácia
            consumer.setDrugstoreCardBalance(consumer.getDrugstoreCardBalance() + value);
            this.consumerRepository.save(consumer);
        } else {
            consumer = this.consumerRepository.findByFoodCardNumber(cardNumber);
            if(consumer != null) {
                // é cartão de refeição
                consumer.setFoodCardBalance(consumer.getFoodCardBalance() + value);
                this.consumerRepository.save(consumer);
            } else {
                // É cartão de combustivel
                consumer = this.consumerRepository.findByFuelCardNumber(cardNumber);
                consumer.setFuelCardBalance(consumer.getFuelCardBalance() + value);
                this.consumerRepository.save(consumer);
            }
        }
    }

    @Override
    public void buy(int establishmentType, String establishmentName, int cardNumber, String productDescription, double value) {
        Consumer consumer = null;
        /* O valores só podem ser debitados dos cartões com os tipos correspondentes ao tipo do estabelecimento da compra.
         *  Exemplo: Se a compra é em um estabelecimeto de Alimentação(food) então o valor só pode ser debitado do cartão e alimentação
         *
         * Tipos de estabelecimentos
         * 1 - Alimentação (food)
         * 2 - Farmácia (DrugStore)
         * 3 - Posto de combustivel (Fuel)
         */

        switch (establishmentType){
            case 1:
                // Para compras no cartão de alimentação o cliente recebe um desconto de 10%
                Double cashback  = (value / 100) * 10;
                value = value - cashback;

                consumer = this.consumerRepository.findByFoodCardNumber(cardNumber);
                consumer.setFoodCardBalance(consumer.getFoodCardBalance() - value);
                this.consumerRepository.save(consumer);
                break;
            case 2:
                consumer = this.consumerRepository.findByDrugstoreNumber(cardNumber);
                consumer.setDrugstoreCardBalance(consumer.getDrugstoreCardBalance() - value);
                this.consumerRepository.save(consumer);
                break;
            case 3:
                // Nas compras com o cartão de combustivel existe um acrescimo de 35%;
                Double tax  = (value / 100) * 35;
                value = value + tax;
                consumer = this.consumerRepository.findByFuelCardNumber(cardNumber);
                consumer.setFuelCardBalance(consumer.getFuelCardBalance() - value);
                this.consumerRepository.save(consumer);
                break;
        }
        Extract extract = new Extract(establishmentName, productDescription, new Date(), cardNumber, value);
        this.extractRepository.save(extract);
    }

    private Consumer amountObjectConsumer(Consumer consumer){
        Consumer consumerUpdate =  new Consumer();
        consumerUpdate.setId(consumer.getId());
        consumerUpdate.setName(consumer.getName());
        consumerUpdate.setDocumentNumber(consumer.getDocumentNumber());
        consumerUpdate.setBirthDate(consumerUpdate.getBirthDate());
        consumerUpdate.setMobilePhoneNumber(consumerUpdate.getMobilePhoneNumber());
        consumerUpdate.setResidencePhoneNumber(consumerUpdate.getResidencePhoneNumber());
        consumerUpdate.setPhoneNumber(consumerUpdate.getPhoneNumber());
        consumerUpdate.setEmail(consumerUpdate.getEmail());
        consumerUpdate.setStreet(consumerUpdate.getStreet());
        consumerUpdate.setNumber(consumerUpdate.getNumber());
        consumerUpdate.setCity(consumerUpdate.getCity());
        consumerUpdate.setCountry(consumerUpdate.getCountry());
        consumerUpdate.setPortalCode(consumer.getPortalCode());
        consumerUpdate.setFoodCardNumber(consumer.getFoodCardNumber());
        consumerUpdate.setFuelCardNumber(consumer.getFuelCardNumber());
        consumerUpdate.setDrugstoreNumber(consumer.getDrugstoreNumber());
        return consumerUpdate;
    }

}
