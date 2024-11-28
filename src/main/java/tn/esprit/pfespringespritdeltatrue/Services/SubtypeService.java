package tn.esprit.pfespringespritdeltatrue.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pfespringespritdeltatrue.Entities.*;
import tn.esprit.pfespringespritdeltatrue.Repositories.SubtypeRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.TypesRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.UploadedFileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubtypeService {

    @Autowired
    SubtypeRepository subtypeRepository;

    @Autowired
    TypesRepository typesRepository;

    public Subtype Ajoutersubtype(Subtype subtype, Long id) {
        Types types = typesRepository.findById(id).orElseThrow(() -> new RuntimeException("Type not found"));
        subtype.setTypes(types);
         return subtypeRepository.save(subtype);
    }

    @Autowired
    UploadedFileRepository uploadedFileRepository;

    public void processUploadedFile(Long subtypeId, MultipartFile file) throws IOException {
        // 1. Récupérer le subtype
        Subtype subtype = subtypeRepository.findById(subtypeId).orElseThrow(() -> new RuntimeException("Subtype introuvable"));

        // 2. Sauvegarder le fichier téléchargé dans le système
        String filePath = saveFile(file);

        // 3. Créer un enregistrement pour le fichier téléchargé
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileName(file.getOriginalFilename());
        uploadedFile.setFilePath(filePath);
        uploadedFile.setSubtype(subtype);
        uploadedFileRepository.save(uploadedFile);

        // 4. Extraire les données des champs (implémentation simplifiée)
        List<Field> fields = subtype.getFields();
        extractFields(file, fields, uploadedFile);
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Implémentation pour enregistrer le fichier (par exemple, dans un répertoire local)
        Path path = Paths.get("uploads/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }

    private void extractFields(MultipartFile file, List<Field> fields, UploadedFile uploadedFile) {
        // Implémentation de l'extraction des données en fonction des champs
        // Pour chaque champ, extraire la valeur et la sauvegarder dans la base de données
        for (Field field : fields) {
            String extractedValue = extractFieldValue(file, field);
            FieldValue fieldValue = new FieldValue();
            fieldValue.setField(field);
            fieldValue.setValue(extractedValue);
            fieldValue.setUploadedFile(uploadedFile);
            uploadedFile.getFieldValues().add(fieldValue);
        }
    }

    private String extractFieldValue(MultipartFile file, Field field) {
        // Implémentation de l'extraction spécifique (ex : parsing d'un fichier Excel, CSV, etc.)
        return "valeur extraite";  // Retourner la valeur extraite
    }


    public Subtype getsubtype(Long id) {
        return subtypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subtype not found"));
    }

    /**
     * Retrieves all Subtypes.
     * @return a list of all Subtypes.
     */
    public List<Subtype> getAllsubtypes() {
        return subtypeRepository.findAll();
    }
    public List<Subtype> searchByKeyword(String keyword){
        return subtypeRepository.searchByKeyword(keyword);
    }
}
