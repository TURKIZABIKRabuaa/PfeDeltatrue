package tn.esprit.pfespringespritdeltatrue.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pfespringespritdeltatrue.Entities.Types;

import java.util.List;

@Repository
public interface TypesRepository extends JpaRepository<Types, Long> {
    public Boolean existsByName(String name);
    @Query("SELECT t FROM Types t WHERE " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Types> searchByKeyword(@Param("keyword") String keyword);
}
