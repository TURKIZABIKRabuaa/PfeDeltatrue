package tn.esprit.pfespringespritdeltatrue.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pfespringespritdeltatrue.Entities.DemandeSubtype;
import tn.esprit.pfespringespritdeltatrue.Entities.DemandeType;
import tn.esprit.pfespringespritdeltatrue.Entities.Subtype;

import java.util.List;

@Repository
public interface DemandeSubtypeRepository extends JpaRepository<DemandeSubtype, Long> {
    Boolean existsDemandeSubtypeByName(String name);
    @Query("SELECT d FROM DemandeSubtype d WHERE d.etatDemandeSubtype = 'enAttente' ")
    List<DemandeSubtype> getDemandeSubTypesenAttente();

    @Query("SELECT d FROM DemandeSubtype d WHERE d.etatDemandeSubtype = 'Refusee'")
    List<DemandeSubtype> getDemandeSubTypesenRefusee();

    @Query("SELECT d FROM DemandeSubtype d WHERE d.etatDemandeSubtype = 'Accepter'")
   List<DemandeSubtype> getDemandeSubTypesenAccepter();
    @Query("SELECT s FROM Subtype s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DemandeSubtype> searchByKeyword(@Param("keyword") String keyword);


}
