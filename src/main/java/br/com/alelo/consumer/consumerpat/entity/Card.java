package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "card")
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private CardType cardType;

    @Column(name = "number")
    private Integer number;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "id_consumer", referencedColumnName = "id")
    private Consumer consumer;

    public void addConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Card update(Card card) {
        this.cardType = card.getCardType();
        this.number = card.getNumber();
        return this;
    }

    public Card creditar(BigDecimal valor) {
        this.balance = this.balance.add(valor);
        return this;
    }

    public Card depositAmount(BigDecimal value) {
        if(hasSaldo(value, balance)){

        }
        this.balance = this.balance.subtract(value);
        return this;
    }

    private boolean hasSaldo(BigDecimal valor, BigDecimal p) {
        return p.compareTo(valor) < 0;
    }

    private void assertConsumer(Boolean is, String msg) {
        if (is) {
            throw new BusinessException(msg);
        }
    }

}
