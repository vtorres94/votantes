package mx.com.morena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.com.morena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CAgendaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAgendaDTO.class);
        CAgendaDTO cAgendaDTO1 = new CAgendaDTO();
        cAgendaDTO1.setId(1L);
        CAgendaDTO cAgendaDTO2 = new CAgendaDTO();
        assertThat(cAgendaDTO1).isNotEqualTo(cAgendaDTO2);
        cAgendaDTO2.setId(cAgendaDTO1.getId());
        assertThat(cAgendaDTO1).isEqualTo(cAgendaDTO2);
        cAgendaDTO2.setId(2L);
        assertThat(cAgendaDTO1).isNotEqualTo(cAgendaDTO2);
        cAgendaDTO1.setId(null);
        assertThat(cAgendaDTO1).isNotEqualTo(cAgendaDTO2);
    }
}
