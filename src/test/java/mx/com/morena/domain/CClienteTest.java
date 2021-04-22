package mx.com.morena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.com.morena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCliente.class);
        CCliente cCliente1 = new CCliente();
        cCliente1.setId(1L);
        CCliente cCliente2 = new CCliente();
        cCliente2.setId(cCliente1.getId());
        assertThat(cCliente1).isEqualTo(cCliente2);
        cCliente2.setId(2L);
        assertThat(cCliente1).isNotEqualTo(cCliente2);
        cCliente1.setId(null);
        assertThat(cCliente1).isNotEqualTo(cCliente2);
    }
}
