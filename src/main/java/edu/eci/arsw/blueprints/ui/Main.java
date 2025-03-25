package edu.eci.arsw.blueprints.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.eci.arsw.blueprints.config.AppConfig;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    BlueprintsServices blueprintsServices = context.getBean(BlueprintsServices.class);

    String autor = "author1";

    // Crear y registrar nuevos planos
    Point[] pts1 = new Point[] { new Point(10, 10), new Point(20, 20) };
    Blueprint bp1 = new Blueprint(autor, "bp1", pts1);
    blueprintsServices.addNewBlueprint(bp1);

    Point[] pts2 = new Point[] { new Point(30, 30), new Point(40, 40) };
    Blueprint bp2 = new Blueprint(autor, "bp2", pts2);
    blueprintsServices.addNewBlueprint(bp2);

    // Consultar planos por autor
    try {
      if (logger.isInfoEnabled()) {
        logger.info("Blueprints by {}:", autor);
        for (Blueprint bp : blueprintsServices.getBlueprintsByAuthor(autor)) {
          logger.info(bp.toString());
        }
      }

      // Consultar un plano específico
      Blueprint retrievedBp = blueprintsServices.getBlueprint(autor, "bp1");
      if (logger.isInfoEnabled()) {
        logger.info("Retrieved blueprint: {}", retrievedBp);
      }
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error("No se encontró el plano.", e);
      }
    }

    ((AnnotationConfigApplicationContext) context).close();
  }
}
