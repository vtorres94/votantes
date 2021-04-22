package mx.com.morena.service.mapper;

import mx.com.morena.domain.*;
import mx.com.morena.service.dto.CVotanteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CVotante} and its DTO {@link CVotanteDTO}.
 */
@Mapper(componentModel = "spring", uses = { CClienteMapper.class, CDefensorVotoMapper.class })
public interface CVotanteMapper extends EntityMapper<CVotanteDTO, CVotante> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "cliente")
    @Mapping(target = "defensorVoto", source = "defensorVoto", qualifiedByName = "nombreCompleto")
    CVotanteDTO toDto(CVotante s);
}
