package tn.esprit.pfespringespritdeltatrue.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pfespringespritdeltatrue.Entities.DemandeType;
import tn.esprit.pfespringespritdeltatrue.Repositories.UserRepository;
import tn.esprit.pfespringespritdeltatrue.Services.DemandeTypeService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("DemandeType")
public class DemandeTypeController {

    @Autowired
    private DemandeTypeService demandeTypeService;

    @Autowired
    UserRepository userRepository;

    // Endpoint to add a DemandeType
    @PostMapping("/add")
    public ResponseEntity<String> addDemandeType(@RequestBody DemandeType demandeType, Principal principal) {
        return demandeTypeService.addDemandeType(principal, demandeType);
    }

    // Endpoint to update a DemandeType by ID
    @PutMapping("/update/{id}")
    public DemandeType updateType(@PathVariable Long id, @RequestBody DemandeType demandeType) {
        return demandeTypeService.UpdateDemandeType(id, demandeType);
    }

    // Endpoint to get all DemandeTypes
    @GetMapping("/all")
    public List<DemandeType> getAllDemandeTypes() {
        return demandeTypeService.getAllDemandeTypes();
    }

    // Endpoint to find a DemandeType by ID
    @GetMapping("/find/{id}")
    public DemandeType findById(@PathVariable Long id) {
        return demandeTypeService.getDemandeTypeById(id);
    }

    // Endpoint to delete a DemandeType by ID
    @DeleteMapping("/delete/{id}")
    public void deleteDemandeType(@PathVariable Long id) {
        demandeTypeService.deleteDemandeTypeById(id);
    }

    // Endpoint to get DemandeTypes that are "en attente"
    @GetMapping("/en-attente")
    public List<DemandeType> getDemandeTypesEnAttente() {
        return demandeTypeService.getDemandeTypesenAttente();
    }

    // Endpoint to get DemandeTypes that are "refusee"
    @GetMapping("/refusee")
    public List<DemandeType> getDemandeTypesRefusee() {
        return demandeTypeService.getDemandeTypesenRefusee();
    }

    // Endpoint to get DemandeTypes that are "accepter"
    @GetMapping("/accepter")
    public List<DemandeType> getDemandeTypesAccepter() {
        return demandeTypeService.getDemandeTypesenAccepter();
    }

    // Endpoint to accept a DemandeType by ID
    @PutMapping("/accepter/{id}")
    public DemandeType accepterDemandeType(@PathVariable Long id) {
        return demandeTypeService.AccepterDemandetype(id);
    }

    // Endpoint to refuse a DemandeType by ID
    @PutMapping("/refusee/{id}")
    public DemandeType refuseeDemandeType(@PathVariable Long id) {
        return demandeTypeService.RefuseeDemandetype(id);
    }
    @GetMapping("/Search")
    public List<DemandeType> searchByKeyword(@Param("keyword") String keyword){
        return demandeTypeService.searchByKeyword(keyword);
    }
}
