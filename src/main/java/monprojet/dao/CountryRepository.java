package monprojet.dao;

import monprojet.dto.PopulationParPays;
import monprojet.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {

    /**
     * calcul la population total d'un pays
     * @param countryID clef du pays
     * @return la population total du pays
     */
    @Query(" SELECT SUM(c.population) " +
            " FROM City c "
            + " WHERE c.country.id = :countryID")
    public Integer populationTotal(Integer countryID);

    @Query(value = "SELECT Country.name as nomPays, SUM(City.population) as popPays "
            + "FROM City "
            + "JOIN Country ON Country.id = City.country_id "
            + "GROUP BY country_id",
            nativeQuery = true)
    public List<PopulationParPays> getPopulationParPays();

}
