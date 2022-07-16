package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.respository.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.respository.ExtractRepository;
import br.com.alelo.consumer.consumerpat.service.ConsumerService;
import br.com.alelo.consumer.consumerpat.service.ConsumerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;


@RestController
@RequestMapping(value = "/consumer", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerController {

    @Autowired
    private ConsumerServiceImpl consumerService;

    /* Deve listar todos os clientes (cerca de 500) */
    @ResponseBody
    @GetMapping(value = "/consumerList")
    public ResponseEntity<?> listAllConsumers() {
        return !this.consumerService.listAllConsumers().isEmpty() ? ResponseEntity.ok(consumerService.listAllConsumers()) : ResponseEntity.noContent().build();
    }

    /* Cadastrar novos clientes */
    @PostMapping(value = "/createConsumer")
    public void createConsumer(@RequestBody Consumer consumer) {
        this.consumerService.createConsumer(consumer);
        ResponseEntity.status(HttpStatus.CREATED);
    }

    // Não deve ser possível alterar o saldo do cartão
    @PostMapping(value = "/updateConsumer")
    public void updateConsumer(@RequestBody Consumer consumer) {
        this.consumerService.updateConsumer(consumer);
    }

    /*
     * Deve creditar(adicionar) um valor(value) em um no cartão.
     * Para isso ele precisa indenficar qual o cartão correto a ser recarregado,
     * para isso deve usar o número do cartão(cardNumber) fornecido.
     */
    @GetMapping(value = "/setcardbalance")
    public void setBalance(int cardNumber, double value) {
        this.consumerService.setBalance(cardNumber, value);
    }

    @ResponseBody
    @GetMapping(value = "/buy")
    public void buy(int establishmentType, String establishmentName, int cardNumber, String productDescription, double value) {
        this.consumerService.buy(establishmentType, establishmentName, cardNumber, productDescription, value);
    }

}
