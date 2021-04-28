package mx.com.morena.service.mapper;

import mx.com.morena.domain.*;
import mx.com.morena.service.dto.CAgendaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CAgenda} and its DTO {@link CAgendaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CAgendaMapper extends EntityMapper<CAgendaDTO, CAgenda> {}
