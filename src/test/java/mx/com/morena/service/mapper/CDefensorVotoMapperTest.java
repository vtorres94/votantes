package mx.com.morena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CDefensorVotoMapperTest {

    private CDefensorVotoMapper cDefensorVotoMapper;

    @BeforeEach
    public void setUp() {
        cDefensorVotoMapper = new CDefensorVotoMapperImpl();
    }
}
