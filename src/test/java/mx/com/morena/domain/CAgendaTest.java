package mx.com.morena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.com.morena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CAgendaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAgenda.class);
        CAgenda cAgenda1 = new CAgenda();
        cAgenda1.setId(1L);
        CAgenda cAgenda2 = new CAgenda();
        cAgenda2.setId(cAgenda1.getId());
        assertThat(cAgenda1).isEqualTo(cAgenda2);
        cAgenda2.setId(2L);
        assertThat(cAgenda1).isNotEqualTo(cAgenda2);
        cAgenda1.setId(null);
        assertThat(cAgenda1).isNotEqualTo(cAgenda2);
    }
}
