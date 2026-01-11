package com.iplacex.discografia.discos;

import com.iplacex.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    // 1. BUSCAR POR ARTISTA
    @GetMapping(value = "/discos/{idArtista}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscosPorArtista(@PathVariable("idArtista") String idArtista) { // Es vital
        // el idArtista que se recibe en la URL coincida con el nombre del parametro
        List<Disco> discos = discoRepo.findDiscosByIdArtista(idArtista);
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    // 2. GUARDAR DISCO (REQUERIMIENTO POST)
    @PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        // Validación de que el artista existe antes de guardar el disco
        if (artistaRepo.existsById(disco.idArtista)) {
            return new ResponseEntity<>(discoRepo.save(disco), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error: El artista no existe", HttpStatus.NOT_FOUND);
    }
}