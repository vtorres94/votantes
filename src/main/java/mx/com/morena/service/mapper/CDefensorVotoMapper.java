package mx.com.morena.service.mapper;

import mx.com.morena.domain.*;
import mx.com.morena.service.dto.CDefensorVotoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CDefensorVoto} and its DTO {@link CDefensorVotoDTO}.
 */
@Mapper(componentModel = "spring", uses = { CClienteMapper.class })
public interface CDefensorVotoMapper extends EntityMapper<CDefensorVotoDTO, CDefensorVoto> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "cliente")
    CDefensorVotoDTO toDto(CDefensorVoto s);

    @Named("nombreCompleto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreCompleto", source = "nombreCompleto")
    CDefensorVotoDTO toDtoNombreCompleto(CDefensorVoto cDefensorVoto);
}
