package it.aguzzo.casamanager.repository;

import it.aguzzo.casamanager.domain.TipologiaProdotto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipologiaProdotto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipologiaProdottoRepository extends JpaRepository<TipologiaProdotto, Long> {

}
