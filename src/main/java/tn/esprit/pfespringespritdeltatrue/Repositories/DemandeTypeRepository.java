package tn.esprit.pfespringespritdeltatrue.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pfespringespritdeltatrue.Entities.DemandeSubtype;
import tn.esprit.pfespringespritdeltatrue.Entities.DemandeType;

import java.util.List;

@Repository
public interface DemandeTypeRepository extends JpaRepository<DemandeType, Long> {
    public Boolean existsDemandeTypeByName(String name);
    @Query ("SELECT DT from DemandeType DT WHERE DT.etatDemandetype='enAttente'")
    public List<DemandeType> getDemandeTypesenAttente();
    @Query ("SELECT DT from DemandeType DT WHERE DT.etatDemandetype='Refusee'")
    public List<DemandeType> getDemandeTypesenRefusee();
    @Query ("SELECT DT from DemandeType DT WHERE DT.etatDemandetype='Accepter'")
    public List<DemandeType> getDemandeTypesenAccepter();
    @Query("SELECT s FROM Subtype s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DemandeType> searchByKeyword(@Param("keyword") String keyword);
}
