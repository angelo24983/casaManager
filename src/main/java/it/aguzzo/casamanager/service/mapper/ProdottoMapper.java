package it.aguzzo.casamanager.service.mapper;

import it.aguzzo.casamanager.domain.*;
import it.aguzzo.casamanager.service.dto.ProdottoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prodotto} and its DTO {@link ProdottoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipologiaProdottoMapper.class})
public interface ProdottoMapper extends EntityMapper<ProdottoDTO, Prodotto> {

    @Mapping(source = "tipologia.id", target = "tipologiaId")
    @Mapping(source = "tipologia.nome", target = "tipologiaNome")
    ProdottoDTO toDto(Prodotto prodotto);

    @Mapping(source = "tipologiaId", target = "tipologia")
    @Mapping(target = "spesas", ignore = true)
    Prodotto toEntity(ProdottoDTO prodottoDTO);

    default Prodotto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prodotto prodotto = new Prodotto();
        prodotto.setId(id);
        return prodotto;
    }
}
