package mx.com.morena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CAgendaMapperTest {

    private CAgendaMapper cAgendaMapper;

    @BeforeEach
    public void setUp() {
        cAgendaMapper = new CAgendaMapperImpl();
    }
}
