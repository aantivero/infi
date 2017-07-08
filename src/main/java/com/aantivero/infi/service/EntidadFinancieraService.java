package com.aantivero.infi.service;

import com.aantivero.infi.domain.EntidadFinanciera;
import com.aantivero.infi.repository.EntidadFinancieraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EntidadFinanciera.
 */
@Service
@Transactional
public class EntidadFinancieraService {

    private final Logger log = LoggerFactory.getLogger(EntidadFinancieraService.class);

    private final EntidadFinancieraRepository entidadFinancieraRepository;

    public EntidadFinancieraService(EntidadFinancieraRepository entidadFinancieraRepository) {
        this.entidadFinancieraRepository = entidadFinancieraRepository;
    }

    /**
     * Save a entidadFinanciera.
     *
     * @param entidadFinanciera the entity to save
     * @return the persisted entity
     */
    public EntidadFinanciera save(EntidadFinanciera entidadFinanciera) {
        log.debug("Request to save EntidadFinanciera : {}", entidadFinanciera);
        return entidadFinancieraRepository.save(entidadFinanciera);
    }

    /**
     *  Get all the entidadFinancieras.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EntidadFinanciera> findAll(Pageable pageable) {
        log.debug("Request to get all EntidadFinancieras");
        return entidadFinancieraRepository.findAll(pageable);
    }

    /**
     *  Get one entidadFinanciera by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EntidadFinanciera findOne(Long id) {
        log.debug("Request to get EntidadFinanciera : {}", id);
        return entidadFinancieraRepository.findOne(id);
    }

    /**
     *  Delete the  entidadFinanciera by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EntidadFinanciera : {}", id);
        entidadFinancieraRepository.delete(id);
    }
}
