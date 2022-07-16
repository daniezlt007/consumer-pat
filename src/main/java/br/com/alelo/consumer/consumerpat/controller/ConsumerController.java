package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.model.entity.Consumer;
import br.com.alelo.consumer.consumerpat.service.ConsumerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerServiceImpl consumerService;

    /* Deve listar todos os clientes (cerca de 500) */
    @ResponseBody
    @GetMapping(value = "/consumerList")
    public ResponseEntity<?> listAllConsumers() {
        return !this.consumerService.listAllConsumers().isEmpty() ? ResponseEntity.ok(this.consumerService.listAllConsumers()) : ResponseEntity.noContent().build();
    }

    /* Cadastrar novos clientes */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "/createConsumer")
    public void createConsumer(@RequestBody Consumer consumer) {
        Consumer consumerNew = this.consumerService.createConsumer(consumer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(consumerNew.getId())
                .toUri();
        ResponseEntity.created(location).build();
    }

    // Não deve ser possível alterar o saldo do cartão
    @PutMapping(value = "/updateConsumer")
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

    }

    @ResponseBody
    @GetMapping(value = "/buy")
    public void buy(int establishmentType, String establishmentName, int cardNumber, String productDescription, double value) {

    }

}
