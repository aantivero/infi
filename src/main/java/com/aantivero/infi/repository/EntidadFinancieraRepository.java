package com.aantivero.infi.repository;

import com.aantivero.infi.domain.EntidadFinanciera;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EntidadFinanciera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadFinancieraRepository extends JpaRepository<EntidadFinanciera,Long> {
    
}
