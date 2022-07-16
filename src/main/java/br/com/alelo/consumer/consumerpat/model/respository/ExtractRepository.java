package br.com.alelo.consumer.consumerpat.model.respository;

import br.com.alelo.consumer.consumerpat.model.entity.Extract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractRepository extends JpaRepository<Extract, Integer> {
}
