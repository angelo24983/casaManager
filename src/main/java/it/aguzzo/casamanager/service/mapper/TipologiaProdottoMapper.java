package it.aguzzo.casamanager.service.mapper;

import it.aguzzo.casamanager.domain.*;
import it.aguzzo.casamanager.service.dto.TipologiaProdottoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipologiaProdotto} and its DTO {@link TipologiaProdottoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipologiaProdottoMapper extends EntityMapper<TipologiaProdottoDTO, TipologiaProdotto> {



    default TipologiaProdotto fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipologiaProdotto tipologiaProdotto = new TipologiaProdotto();
        tipologiaProdotto.setId(id);
        return tipologiaProdotto;
    }
}
