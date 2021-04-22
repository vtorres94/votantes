package mx.com.morena.repository;

import mx.com.morena.domain.CVotante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CVotante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CVotanteRepository extends JpaRepository<CVotante, Long>, JpaSpecificationExecutor<CVotante> {}
