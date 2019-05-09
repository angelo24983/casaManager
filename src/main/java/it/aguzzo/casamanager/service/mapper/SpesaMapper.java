package it.aguzzo.casamanager.service.mapper;

import it.aguzzo.casamanager.domain.*;
import it.aguzzo.casamanager.service.dto.SpesaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Spesa} and its DTO {@link SpesaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProdottoMapper.class})
public interface SpesaMapper extends EntityMapper<SpesaDTO, Spesa> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    SpesaDTO toDto(Spesa spesa);

    @Mapping(source = "userId", target = "user")
    Spesa toEntity(SpesaDTO spesaDTO);

    default Spesa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Spesa spesa = new Spesa();
        spesa.setId(id);
        return spesa;
    }
}
