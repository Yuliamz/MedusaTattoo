package com.medusa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.medusa.domain.Foto;
import com.medusa.repository.FotoRepository;
import com.medusa.service.dto.GalleryDTO;
import com.medusa.web.rest.errors.BadRequestAlertException;
import com.medusa.web.rest.util.HeaderUtil;
import com.medusa.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Foto.
 */
@RestController
@RequestMapping("/api")
public class FotoResource {

    private final Logger log = LoggerFactory.getLogger(FotoResource.class);

    private static final String ENTITY_NAME = "foto";

    private final FotoRepository fotoRepository;

    public FotoResource(FotoRepository fotoRepository) {
        this.fotoRepository = fotoRepository;
    }

    /**
     * POST  /fotos : Create a new foto.
     *
     * @param foto the foto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foto, or with status 400 (Bad Request) if the foto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fotos")
    @Timed
    public ResponseEntity<Foto> createFoto(@Valid @RequestBody Foto foto) throws URISyntaxException {
        log.debug("REST request to save Foto : {}", foto);

        if (foto.getId() != null) {
            throw new BadRequestAlertException("A new foto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        /*
        try {
            BufferedImage miniatura = Thumbnails.of(foto.decodeToImage()).size(350,250).asBufferedImage();
            System.out.println("=============miniatura==========="+foto.imgToBase64String(miniatura,"jpeg"));
            System.out.println(foto.print(foto.getImagen()));
            byte[] encoded = ((DataBufferByte) miniatura.getData().getDataBuffer()).getData();
            foto.setMiniatura(encoded);
            System.out.println("==================================");
            System.out.println(foto.print(foto.getMiniatura()));
            foto.setMiniaturaContentType(foto.getImagenContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        foto.setMiniaturaContentType(foto.getImagenContentType());
        Foto result = fotoRepository.save(foto);
        return ResponseEntity.created(new URI("/api/fotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fotos : Updates an existing foto.
     *
     * @param foto the foto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foto,
     * or with status 400 (Bad Request) if the foto is not valid,
     * or with status 500 (Internal Server Error) if the foto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fotos")
    @Timed
    public ResponseEntity<Foto> updateFoto(@Valid @RequestBody Foto foto) throws URISyntaxException {
        log.debug("REST request to update Foto : {}", foto);
        if (foto.getId() == null) {
            return createFoto(foto);
        }
        Foto result = fotoRepository.save(foto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, foto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fotos : get all the fotos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fotos in body
     */
    @GetMapping("/fotos")
    @Timed
    public ResponseEntity<List<Foto>> getAllFotos(Pageable pageable) {
        log.debug("REST request to get a page of Fotos");
        Page<Foto> page = fotoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fotos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fotos/:id : get the "id" foto.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the foto, or with status 404 (Not Found)
     */
    @GetMapping("/fotos/galeria")
    @Timed
    public ResponseEntity<GalleryDTO> getGallery() {
        log.debug("REST request to get Gallery : {}");
        return new ResponseEntity<>(new GalleryDTO(fotoRepository.findAllWithEagerRelationships()),HttpStatus.OK);
    }


    /**
     * GET  /fotos/:id : get the "id" foto.
     *
     * @param id the id of the foto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foto, or with status 404 (Not Found)
     */
    @GetMapping("/fotos/{id}")
    @Timed
    public ResponseEntity<Foto> getFoto(@PathVariable Long id) {
        log.debug("REST request to get Foto : {}", id);
        Foto foto = fotoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(foto));
    }

    /**
     * DELETE  /fotos/:id : delete the "id" foto.
     *
     * @param id the id of the foto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fotos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoto(@PathVariable Long id) {
        log.debug("REST request to delete Foto : {}", id);
        fotoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
