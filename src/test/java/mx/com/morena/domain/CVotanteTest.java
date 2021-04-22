package mx.com.morena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import mx.com.morena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CVotanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVotante.class);
        CVotante cVotante1 = new CVotante();
        cVotante1.setId(1L);
        CVotante cVotante2 = new CVotante();
        cVotante2.setId(cVotante1.getId());
        assertThat(cVotante1).isEqualTo(cVotante2);
        cVotante2.setId(2L);
        assertThat(cVotante1).isNotEqualTo(cVotante2);
        cVotante1.setId(null);
        assertThat(cVotante1).isNotEqualTo(cVotante2);
    }
}
