package mx.com.morena.repository;

import mx.com.morena.domain.CCliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CClienteRepository extends JpaRepository<CCliente, Long>, JpaSpecificationExecutor<CCliente> {}
