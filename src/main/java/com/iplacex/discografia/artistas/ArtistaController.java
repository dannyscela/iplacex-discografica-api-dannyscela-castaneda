package com.iplacex.discografia.artistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api") // Esta es la base de la URL
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetArtistaByIdRequest(@PathVariable String id) {
        return artistaRepo.findById(id)
                .<ResponseEntity<Object>>map(artista -> new ResponseEntity<>(artista, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No encontrado", HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        return new ResponseEntity<>(artistaRepo.save(artista), HttpStatus.CREATED);
    }

    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        return new ResponseEntity<>(artistaRepo.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/artista/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (artistaRepo.existsById(id)) {
            artista._id = id;
            return new ResponseEntity<>(artistaRepo.save(artista), HttpStatus.OK);
        }
        return new ResponseEntity<>("No encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (artistaRepo.existsById(id)) {
            artistaRepo.deleteById(id);
            return new ResponseEntity<>("Eliminado con éxito", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: El registro no existe", HttpStatus.NOT_FOUND);
    }
}
