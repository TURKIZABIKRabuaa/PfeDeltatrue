package tn.esprit.pfespringespritdeltatrue.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pfespringespritdeltatrue.Entities.UploadedFile;

import java.util.List;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
    List<UploadedFile> findBySubtypeId(Long subtypeId); // Récupérer les fichiers d'un sous-type donné
    @Query("SELECT u FROM UploadedFile u WHERE " +
            "LOWER(u.fileName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.filePath) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UploadedFile> searchByKeyword(@Param("keyword") String keyword);
}
