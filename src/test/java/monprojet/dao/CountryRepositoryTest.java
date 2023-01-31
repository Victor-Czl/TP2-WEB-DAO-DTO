package monprojet.dao;

import lombok.extern.log4j.Log4j2;
import monprojet.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }

    @Test
    void PopoulationTotalestBonne() {
        log.info("On calcul la population total d'un pays");
        int franceId = 1;
        int francePopulation = 12;
        Integer francePopulationTotal = countryDAO.populationTotal(franceId);
        assertEquals(francePopulation, francePopulationTotal, "On doit trouver 12");
    }

    @Test
    void NombreDeRetourPopulationsParPaysEstCorrect() {
       log.info("On vérifie que la méthode getPopulationParPays fonctionne");
       long nombreDePays = countryDAO.count();
       int nombreDeRetour = countryDAO.getPopulationParPays().size();
       assertEquals(nombreDePays, nombreDeRetour, "On doit trouver 3");
    }

}
