package tn.esprit.pfespringespritdeltatrue.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pfespringespritdeltatrue.Entities.FieldValue;

import java.util.List;

@Repository
public interface FieldValueRepository extends JpaRepository<FieldValue, Long> {
    List<FieldValue> findByUploadedFileId(Long uploadedFileId); // Récupérer les valeurs pour un fichier donné
}

