package mx.com.morena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CClienteMapperTest {

    private CClienteMapper cClienteMapper;

    @BeforeEach
    public void setUp() {
        cClienteMapper = new CClienteMapperImpl();
    }
}
