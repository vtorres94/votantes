package mx.com.morena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.com.morena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CVotanteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVotanteDTO.class);
        CVotanteDTO cVotanteDTO1 = new CVotanteDTO();
        cVotanteDTO1.setId(1L);
        CVotanteDTO cVotanteDTO2 = new CVotanteDTO();
        assertThat(cVotanteDTO1).isNotEqualTo(cVotanteDTO2);
        cVotanteDTO2.setId(cVotanteDTO1.getId());
        assertThat(cVotanteDTO1).isEqualTo(cVotanteDTO2);
        cVotanteDTO2.setId(2L);
        assertThat(cVotanteDTO1).isNotEqualTo(cVotanteDTO2);
        cVotanteDTO1.setId(null);
        assertThat(cVotanteDTO1).isNotEqualTo(cVotanteDTO2);
    }
}
