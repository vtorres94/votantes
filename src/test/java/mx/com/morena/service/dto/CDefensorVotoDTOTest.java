package mx.com.morena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.com.morena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CDefensorVotoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CDefensorVotoDTO.class);
        CDefensorVotoDTO cDefensorVotoDTO1 = new CDefensorVotoDTO();
        cDefensorVotoDTO1.setId(1L);
        CDefensorVotoDTO cDefensorVotoDTO2 = new CDefensorVotoDTO();
        assertThat(cDefensorVotoDTO1).isNotEqualTo(cDefensorVotoDTO2);
        cDefensorVotoDTO2.setId(cDefensorVotoDTO1.getId());
        assertThat(cDefensorVotoDTO1).isEqualTo(cDefensorVotoDTO2);
        cDefensorVotoDTO2.setId(2L);
        assertThat(cDefensorVotoDTO1).isNotEqualTo(cDefensorVotoDTO2);
        cDefensorVotoDTO1.setId(null);
        assertThat(cDefensorVotoDTO1).isNotEqualTo(cDefensorVotoDTO2);
    }
}
