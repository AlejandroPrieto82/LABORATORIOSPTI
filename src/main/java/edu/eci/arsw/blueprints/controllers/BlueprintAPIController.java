package edu.eci.arsw.blueprints.controllers;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 * Controlador REST para exponer los blueprints
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

  @Autowired
  private BlueprintsServices blueprintsServices;

  @GetMapping()
  public ResponseEntity<Set<Blueprint>> getAllBlueprints() {
      try {
          Set<Blueprint> blueprints = blueprintsServices.getAllBlueprints();
          return new ResponseEntity<>(blueprints, HttpStatus.OK);
      } catch (Exception e) {
          Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
  
  @GetMapping("/{author}")
  public ResponseEntity<Set<Blueprint>> getBlueprintsByAuthor(@PathVariable String author) {
      try {
          Set<Blueprint> blueprints = blueprintsServices.getBlueprintsByAuthor(author);
          if (blueprints.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 si no hay planos
          }
          return new ResponseEntity<>(blueprints, HttpStatus.OK);
      } catch (Exception e) {
          Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @GetMapping("/{author}/{bpname}")
  public ResponseEntity<Blueprint> getBlueprint(
      @PathVariable String author,
      @PathVariable String bpname
  ) {
      try {
          Blueprint blueprint = blueprintsServices.getBlueprint(author, bpname);
          return new ResponseEntity<>(blueprint, HttpStatus.OK);
      } catch (Exception e) {
          Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @PostMapping()
  public ResponseEntity<String> addNewBlueprint(@RequestBody Blueprint blueprint) { // Cambio en el tipo de retorno
      try {
          blueprintsServices.addNewBlueprint(blueprint);
          return new ResponseEntity<>("Blueprint agregado correctamente", HttpStatus.CREATED);
      } catch (Exception e) {
          Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
          return new ResponseEntity<>("Error al agregar el blueprint", HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
}
