package tn.esprit.pfespringespritdeltatrue.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pfespringespritdeltatrue.Entities.Subtype;

import java.util.List;

@Repository
public interface SubtypeRepository extends JpaRepository<Subtype, Long> {
    public Boolean existsByName(String name);
    @Query("SELECT s FROM Subtype s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Subtype> searchByKeyword(@Param("keyword") String keyword);
}
