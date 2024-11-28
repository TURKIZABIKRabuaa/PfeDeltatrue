package tn.esprit.pfespringespritdeltatrue.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.pfespringespritdeltatrue.Entities.*;
import tn.esprit.pfespringespritdeltatrue.Repositories.DemandeSubtypeRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.SubtypeRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.TypesRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DemandeSubtypeService {
    @Autowired
    DemandeSubtypeRepository demandeSubtypeRepository;
    @Autowired
    TypesRepository typesRepository;
    @Autowired
    SubtypeRepository subtypeRepository;
    public ResponseEntity<String> addDemandeSubType(Long id, DemandeSubtype demandeSubtype) {
        // Check if a DemandeSubtype with the same name already exists
        if (subtypeRepository.existsByName(demandeSubtype.getName())) {
            return new ResponseEntity<>("Subtype with this name already exists", HttpStatus.CONFLICT);
        }

        // Fetch the parent Type by ID
        Optional<Types> typesOptional = typesRepository.findById(id);
        if (typesOptional.isEmpty()) {
            return new ResponseEntity<>("Type with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }

        // Associate the DemandeSubtype with the parent Type
        Types types = typesOptional.get();
        demandeSubtype.setType(types);

        // Save the new DemandeSubtype
        demandeSubtypeRepository.save(demandeSubtype);

        return new ResponseEntity<>("DemandeSubtype added successfully", HttpStatus.CREATED);
    }


    public List<DemandeSubtype> getAllDemandeSubtypes() {
        return demandeSubtypeRepository.findAll();
    }
    public DemandeSubtype getDemandeSubtypeById(Long id) {
        return demandeSubtypeRepository.findById(id).get();
    }
    public DemandeSubtype updateDemandeSubtype(Long id, DemandeSubtype demandeSubtype) {
        if (demandeSubtypeRepository.existsDemandeSubtypeByName(demandeSubtype.getName()))  {
            throw new IllegalArgumentException("DemandeSubtype with this name already exists");
        }
        if (subtypeRepository.existsByName(demandeSubtype.getName())) {
            throw new IllegalArgumentException("DemandeType with this name already exists");
        }
        return demandeSubtypeRepository.save(demandeSubtypeRepository.findById(id).get());
    }
    public void deleteDemandeSubtypeById(Long id) {
        demandeSubtypeRepository.deleteById(id);
    }
    public List<DemandeSubtype> getDemandeSubTypesenAttente(){
        return demandeSubtypeRepository.getDemandeSubTypesenAttente();
    }
    public List<DemandeSubtype> getDemandeSubTypesenRefusee(){
        return demandeSubtypeRepository.getDemandeSubTypesenRefusee();
    }
    public List<DemandeSubtype> getDemandeSubTypesenAccepter(){
        return demandeSubtypeRepository.getDemandeSubTypesenAccepter();
    }
    public DemandeSubtype AccepterDemandeSubtype(Long id) {
        Optional<DemandeSubtype> demandeSubtypeOptional = demandeSubtypeRepository.findById(id);

        if (demandeSubtypeOptional.isPresent()) {
            DemandeSubtype demandeSubtype = demandeSubtypeOptional.get();

            // Mettre à jour l'état de DemandeSubtype en 'Accepter'
            demandeSubtype.setEtatDemandeSubtype(EtatDemandeSubtype.Accepter);
            Subtype newSubType = new Subtype();
            newSubType.setName(demandeSubtype.getName());  // Exemple : vous pouvez prendre les propriétés nécessaires du DemandeSubtype
            newSubType.setDescription(demandeSubtype.getDescription());
            newSubType.setLogo(demandeSubtype.getLogo());// Exemple de propriété

            // Associer le Type avec l'entité DemandeSubtype si nécessaire
            //demandeSubtype.set(newSubType);  // Si DemandeSubtype a une relation avec Type

            // Sauvegarder le nouveau 'Type' dans le repository
            subtypeRepository.save(newSubType);

            // Sauvegarder le DemandeSubtype mis à jour dans le repository
            return demandeSubtypeRepository.save(demandeSubtype); // Retourner l'objet mis à jour
        } else {
            // Si l'objet DemandeSubtype n'est pas trouvé, vous pouvez lever une exception
            // ou gérer l'erreur autrement, selon vos besoins
            throw new EntityNotFoundException("DemandeSubtype with ID " + id + " not found");
        }
    }
    public DemandeSubtype RefuseeDemandeSubtype(Long id) {
        Optional<DemandeSubtype> demandeSubtypeOptional = demandeSubtypeRepository.findById(id);

        if (demandeSubtypeOptional.isPresent()) {
            DemandeSubtype demandeSubtype = demandeSubtypeOptional.get();

            // Mettre à jour l'état de DemandeSubtype en 'Accepter'
            demandeSubtype.setEtatDemandeSubtype(EtatDemandeSubtype.Refusee);

            // Sauvegarder le DemandeSubtype mis à jour dans le repository
            return demandeSubtypeRepository.save(demandeSubtype); // Retourner l'objet mis à jour
        } else {
            // Si l'objet DemandeSubtype n'est pas trouvé, vous pouvez lever une exception
            // ou gérer l'erreur autrement, selon vos besoins
            throw new EntityNotFoundException("DemandeSubtype with ID " + id + " not found");
        }
    }




}
