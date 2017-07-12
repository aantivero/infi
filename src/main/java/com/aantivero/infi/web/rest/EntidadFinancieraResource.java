package com.aantivero.infi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aantivero.infi.domain.EntidadFinanciera;
import com.aantivero.infi.service.EntidadFinancieraService;
import com.aantivero.infi.web.rest.util.HeaderUtil;
import com.aantivero.infi.web.rest.util.PaginationUtil;
import com.opencsv.CSVReader;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EntidadFinanciera.
 */
@RestController
@RequestMapping("/api")
public class EntidadFinancieraResource {

    private final Logger log = LoggerFactory.getLogger(EntidadFinancieraResource.class);

    private static final String ENTITY_NAME = "entidadFinanciera";

    private final EntidadFinancieraService entidadFinancieraService;

    public EntidadFinancieraResource(EntidadFinancieraService entidadFinancieraService) {
        this.entidadFinancieraService = entidadFinancieraService;
    }

    /**
     * POST  /entidad-financieras : Create a new entidadFinanciera.
     *
     * @param entidadFinanciera the entidadFinanciera to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entidadFinanciera, or with status 400 (Bad Request) if the entidadFinanciera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entidad-financieras")
    @Timed
    public ResponseEntity<EntidadFinanciera> createEntidadFinanciera(@Valid @RequestBody EntidadFinanciera entidadFinanciera) throws URISyntaxException {
        log.debug("REST request to save EntidadFinanciera : {}", entidadFinanciera);
        if (entidadFinanciera.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entidadFinanciera cannot already have an ID")).body(null);
        }
        EntidadFinanciera result = entidadFinancieraService.save(entidadFinanciera);
        return ResponseEntity.created(new URI("/api/entidad-financieras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entidad-financieras : Updates an existing entidadFinanciera.
     *
     * @param entidadFinanciera the entidadFinanciera to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entidadFinanciera,
     * or with status 400 (Bad Request) if the entidadFinanciera is not valid,
     * or with status 500 (Internal Server Error) if the entidadFinanciera couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entidad-financieras")
    @Timed
    public ResponseEntity<EntidadFinanciera> updateEntidadFinanciera(@Valid @RequestBody EntidadFinanciera entidadFinanciera) throws URISyntaxException {
        log.debug("REST request to update EntidadFinanciera : {}", entidadFinanciera);
        if (entidadFinanciera.getId() == null) {
            return createEntidadFinanciera(entidadFinanciera);
        }
        EntidadFinanciera result = entidadFinancieraService.save(entidadFinanciera);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entidadFinanciera.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entidad-financieras : get all the entidadFinancieras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entidadFinancieras in body
     */
    @GetMapping("/entidad-financieras")
    @Timed
    public ResponseEntity<List<EntidadFinanciera>> getAllEntidadFinancieras(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EntidadFinancieras");
        Page<EntidadFinanciera> page = entidadFinancieraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entidad-financieras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eeff : get all the entidadFinancieras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entidadFinancieras in body
     */
    @GetMapping("/eeff")
    @Timed
    public ResponseEntity<List<EntidadFinanciera>> getAllEntidadesFinancieras(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EntidadFinancieras");
        Page<EntidadFinanciera> page = entidadFinancieraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eeff");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entidad-financieras/:id : get the "id" entidadFinanciera.
     *
     * @param id the id of the entidadFinanciera to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entidadFinanciera, or with status 404 (Not Found)
     */
    @GetMapping("/entidad-financieras/{id}")
    @Timed
    public ResponseEntity<EntidadFinanciera> getEntidadFinanciera(@PathVariable Long id) {
        log.debug("REST request to get EntidadFinanciera : {}", id);
        EntidadFinanciera entidadFinanciera = entidadFinancieraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entidadFinanciera));
    }

    /**
     * DELETE  /entidad-financieras/:id : delete the "id" entidadFinanciera.
     *
     * @param id the id of the entidadFinanciera to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entidad-financieras/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntidadFinanciera(@PathVariable Long id) {
        log.debug("REST request to delete EntidadFinanciera : {}", id);
        entidadFinancieraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /entidad-financiera/load : Cargar las entidades financieras
     *
     * @param archivo  el archivo a cargar
     * @return Boolean
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entidad-financiera/load")
    @Timed
    public Boolean loadEntidadFinanciera(@RequestParam("archivo")MultipartFile archivo){
        log.debug("REST request to load entidades financieras");
        boolean response = false;
        try {
            InputStream inputStream = archivo.getInputStream();
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream), ';');
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                //System.out.println(nextLine.length + " " + nextLine[0]);
                EntidadFinanciera eeff = new EntidadFinanciera();
                eeff.setCodigo(nextLine[0]);
                eeff.setCodigoNumerico(new Integer(nextLine[1]));
                eeff.setDenominacion(nextLine[2]);
                entidadFinancieraService.save(eeff);
            }
            response = true;
        } catch (IOException e) {
            log.error("Occurs an error when tried to process the file", e);
        }
        return response;
    }
}
