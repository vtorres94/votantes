package mx.com.morena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CVotanteMapperTest {

    private CVotanteMapper cVotanteMapper;

    @BeforeEach
    public void setUp() {
        cVotanteMapper = new CVotanteMapperImpl();
    }
}
