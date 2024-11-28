package tn.esprit.pfespringespritdeltatrue.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pfespringespritdeltatrue.Entities.Subtype;
import tn.esprit.pfespringespritdeltatrue.Entities.UploadedFile;
import tn.esprit.pfespringespritdeltatrue.Repositories.SubtypeRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.UploadedFileRepository;
import tn.esprit.pfespringespritdeltatrue.Services.SubtypeService;

import java.util.List;


@RestController
@RequestMapping("/Subtype")
public class SubtypeController {
    @Autowired
    SubtypeService subtypeService;
    @Autowired
    private SubtypeRepository subtypeRepository;
    @Autowired
    UploadedFileRepository uploadedFileRepository;

    @PostMapping("/createSubtype")
    public Subtype createSubtype(@RequestBody Subtype subtype,Long id) {
        return subtypeService.Ajoutersubtype(subtype,id);
    }
    @GetMapping("SearchSubtype")
    List<Subtype> searchByKeywordsubtype(@Param("keyword") String keyword){
        return subtypeService.searchByKeyword(keyword);
    }
    @GetMapping("/GetAll")
    public List<Subtype> getAllSubtypes() {return subtypeService.getAllsubtypes();}
    @GetMapping("FileSearch")
    List<UploadedFile> searchByKeyword(@Param("keyword") String keyword){
        return uploadedFileRepository.searchByKeyword(keyword);
    }
    @GetMapping("/GetById")
    public Subtype getById(@RequestParam Long id){return subtypeService.getsubtype(id);}
    @PostMapping("/{subtypeId}/upload")
    public ResponseEntity<String> uploadFile(@PathVariable Long subtypeId, @RequestParam("file") MultipartFile file) {
        try {
            subtypeService.processUploadedFile(subtypeId, file);
            return ResponseEntity.ok("Fichier téléchargé et traité avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du téléchargement du fichier");
        }
    }
}
