package mx.com.morena.repository;

import mx.com.morena.domain.CAgenda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CAgenda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CAgendaRepository extends JpaRepository<CAgenda, Long>, JpaSpecificationExecutor<CAgenda> {}
