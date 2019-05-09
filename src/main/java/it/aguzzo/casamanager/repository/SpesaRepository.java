package it.aguzzo.casamanager.repository;

import it.aguzzo.casamanager.domain.Spesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Spesa entity.
 */
@Repository
public interface SpesaRepository extends JpaRepository<Spesa, Long> {

    @Query("select spesa from Spesa spesa where spesa.user.login = ?#{principal.username}")
    List<Spesa> findByUserIsCurrentUser();

    @Query(value = "select distinct spesa from Spesa spesa left join fetch spesa.prodottos",
        countQuery = "select count(distinct spesa) from Spesa spesa")
    Page<Spesa> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct spesa from Spesa spesa left join fetch spesa.prodottos")
    List<Spesa> findAllWithEagerRelationships();

    @Query("select spesa from Spesa spesa left join fetch spesa.prodottos where spesa.id =:id")
    Optional<Spesa> findOneWithEagerRelationships(@Param("id") Long id);

}
