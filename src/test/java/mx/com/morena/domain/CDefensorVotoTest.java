package mx.com.morena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.com.morena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CDefensorVotoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CDefensorVoto.class);
        CDefensorVoto cDefensorVoto1 = new CDefensorVoto();
        cDefensorVoto1.setId(1L);
        CDefensorVoto cDefensorVoto2 = new CDefensorVoto();
        cDefensorVoto2.setId(cDefensorVoto1.getId());
        assertThat(cDefensorVoto1).isEqualTo(cDefensorVoto2);
        cDefensorVoto2.setId(2L);
        assertThat(cDefensorVoto1).isNotEqualTo(cDefensorVoto2);
        cDefensorVoto1.setId(null);
        assertThat(cDefensorVoto1).isNotEqualTo(cDefensorVoto2);
    }
}
