package mx.com.morena.repository;

import mx.com.morena.domain.CDefensorVoto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CDefensorVoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CDefensorVotoRepository extends JpaRepository<CDefensorVoto, Long>, JpaSpecificationExecutor<CDefensorVoto> {}
