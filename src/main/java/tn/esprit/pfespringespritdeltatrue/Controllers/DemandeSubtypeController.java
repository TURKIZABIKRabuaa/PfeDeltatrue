package tn.esprit.pfespringespritdeltatrue.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pfespringespritdeltatrue.Entities.DemandeSubtype;
import tn.esprit.pfespringespritdeltatrue.PfeSpringEspritDeltaTrueApplication;
import tn.esprit.pfespringespritdeltatrue.Repositories.DemandeSubtypeRepository;
import tn.esprit.pfespringespritdeltatrue.Services.DemandeSubtypeService;

import java.util.List;

@RestController
@RequestMapping("DemandeSubtype")
public class DemandeSubtypeController {
    @Autowired
    private DemandeSubtypeService demandeSubtypeService;
    @Autowired
    private PfeSpringEspritDeltaTrueApplication pfeSpringEspritDeltaTrueApplication;

    // Corrected createDemandeSubType to use the right path variable name
    @PostMapping("/{typeId}")
    public ResponseEntity<String> createDemandeSubType(
            @PathVariable Long typeId, @RequestBody DemandeSubtype demandeSubtype) {
        return demandeSubtypeService.addDemandeSubType(typeId, demandeSubtype);
    }
    @Autowired
    DemandeSubtypeRepository demandeSubtypeRepository;
    @GetMapping("/Search")
    List<DemandeSubtype> searchByKeyword(@Param("keyword") String keyword){
        return demandeSubtypeRepository.searchByKeyword(keyword);
    }

    // Renamed mapping for clarity
    @GetMapping("/all")
    public List<DemandeSubtype> getAllDemandeSubtypes() {
        return demandeSubtypeService.getAllDemandeSubtypes();
    }

    // Fixed missing @PathVariable and incorrect method name
    @GetMapping("/{id}")
    public DemandeSubtype getDemandeSubtypeById(@PathVariable Long id) {
        return demandeSubtypeService.getDemandeSubtypeById(id);
    }

    // Updated to correctly map the path variable
    @PutMapping("/update/{id}")
    public DemandeSubtype updateDemandeType(@PathVariable Long id, @RequestBody DemandeSubtype demandeSubtype) {
        return demandeSubtypeService.updateDemandeSubtype(id, demandeSubtype);
    }

    // Corrected path for delete operation
    @DeleteMapping("/delete/{id}")
    public void deleteDemandeSubtype(@PathVariable Long id) {
        demandeSubtypeService.deleteDemandeSubtypeById(id);
    }

    // Corrected path and added clarity
    @GetMapping("/en-attente")
    public List<DemandeSubtype> getDemandeSubTypesEnAttente() {
        return demandeSubtypeService.getDemandeSubTypesenAttente();
    }

    // Corrected path and added clarity
    @GetMapping("/refusee")
    public List<DemandeSubtype> getDemandeSubTypesRefusee() {
        return demandeSubtypeService.getDemandeSubTypesenRefusee();
    }

    // Corrected path and added clarity
    @GetMapping("/accepter")
    public List<DemandeSubtype> getDemandeSubTypesAccepter() {
        return demandeSubtypeService.getDemandeSubTypesenAccepter();
    }

    // Fixed missing @PathVariable and improved clarity
    @PutMapping("/accepter/{id}")
    public DemandeSubtype accepterDemandeSubtype(@PathVariable Long id) {
        return demandeSubtypeService.AccepterDemandeSubtype(id);
    }

    // Fixed missing @PathVariable and improved clarity
    @PutMapping("/refusee/{id}")
    public DemandeSubtype refuseeDemandeSubtype(@PathVariable Long id) {
        return demandeSubtypeService.RefuseeDemandeSubtype(id);
    }
}
