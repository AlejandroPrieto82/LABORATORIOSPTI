package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlueprintsServices {

  private static final Logger logger = LoggerFactory.getLogger(BlueprintsServices.class);

  @Autowired
  private BlueprintsPersistence bpp;

  @Autowired
  private BlueprintFilter filter;

  public void addNewBlueprint(Blueprint bp) {
    try {
      bpp.saveBlueprint(bp);
      logger.info("Se agregó un nuevo plano: {}", bp);
    } catch (Exception e) {
      logger.error("Error al agregar el nuevo plano: {}", bp, e);
    }
  }

  public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
    Blueprint bp = bpp.getBlueprint(author, name);
    return filter.filter(bp);
  }

  public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
    Set<Blueprint> blueprints = bpp.getBlueprintsByAuthor(author);
    return blueprints.stream().map(filter::filter).collect(Collectors.toSet());
  }

  /**
   * Este método obtiene todos los planos almacenados.
   * @return Conjunto de planos filtrados.
   * @throws BlueprintNotFoundException Si no hay planos disponibles.
   */
  public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
    Set<Blueprint> blueprints = bpp.getAllBlueprints();
    return blueprints.stream().map(filter::filter).collect(Collectors.toSet());
  }
}
