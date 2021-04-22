package mx.com.morena.service.mapper;

import mx.com.morena.domain.*;
import mx.com.morena.service.dto.CClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCliente} and its DTO {@link CClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CClienteMapper extends EntityMapper<CClienteDTO, CCliente> {
    @Named("cliente")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cliente", source = "cliente")
    CClienteDTO toDtoCliente(CCliente cCliente);
}
