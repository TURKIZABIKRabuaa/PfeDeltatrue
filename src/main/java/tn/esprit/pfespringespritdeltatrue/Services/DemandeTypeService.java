package tn.esprit.pfespringespritdeltatrue.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import tn.esprit.pfespringespritdeltatrue.Entities.*;
import tn.esprit.pfespringespritdeltatrue.Repositories.DemandeSubtypeRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.DemandeTypeRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.TypesRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DemandeTypeService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DemandeTypeRepository demandeTypeRepository;
    @Autowired
    private  TypesRepository typesRepository;
    @Autowired
    EmailService emailService;
    public ResponseEntity<String> addDemandeType(Principal principal, DemandeType demandeType) {
        // Check if the user is authenticated
        if (principal == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        // Validate demandeType object and its name
        if (demandeType == null || demandeType.getName() == null || demandeType.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Invalid DemandeType object", HttpStatus.BAD_REQUEST);
        }

        // Fetch the user by email from the principal
        Optional<User> userOptional = userRepository.findByEmail(principal.getName());
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        demandeType.setUser(user); // Associate the DemandeType with the current user

        // Check if a DemandeType with the same name already exists
        if (typesRepository.existsByName(demandeType.getName())) {
            return new ResponseEntity<>("Type with this name already exists", HttpStatus.CONFLICT);
        }
        if (demandeTypeRepository.existsDemandeTypeByName(demandeType.getName())) {
            return new ResponseEntity<>("DemandeType with this name already exists", HttpStatus.CONFLICT);
        }


        // Save the new DemandeType and return success
        demandeTypeRepository.save(demandeType);
        log.info("DemandeType '{}' added successfully by user: {}", demandeType.getName(), user.getEmail());
        return new ResponseEntity<>("DemandeType added successfully", HttpStatus.CREATED);
    }

    public DemandeType UpdateDemandeType(Long id,DemandeType demandeType) {
        if (typesRepository.existsByName(demandeType.getName())) {
            throw new IllegalArgumentException("Type with this name already exists");
        }
        if (demandeTypeRepository.existsDemandeTypeByName(demandeType.getName())) {
            throw new IllegalArgumentException("DemandeType with this name already exists");
        }
        return demandeTypeRepository.save(demandeTypeRepository.findById(id).get());
    }
    public List<DemandeType> getAllDemandeTypes() {
        return demandeTypeRepository.findAll();
    }
    public DemandeType getDemandeTypeById(Long id) {
        return demandeTypeRepository.findById(id).get();
    }
    public void deleteDemandeTypeById(Long id) {
        demandeTypeRepository.deleteById(id);
    }
    public List<DemandeType> getDemandeTypesenAttente(){
        return demandeTypeRepository.getDemandeTypesenAttente();

    }
    public List<DemandeType> getDemandeTypesenRefusee(){

        return demandeTypeRepository.getDemandeTypesenRefusee();

    }
    public List<DemandeType> getDemandeTypesenAccepter(){
        return demandeTypeRepository.getDemandeTypesenAccepter();

    }
    public DemandeType AccepterDemandetype(Long id) {
        Optional<DemandeType> demandetypeOptional = demandeTypeRepository.findById(id);
        User user= demandetypeOptional.get().getUser();
        String emailuser=user.getEmail();
        String name= user.getName();
        String lastname=user.getSurname();
        if (demandetypeOptional.isPresent()) {
            DemandeType demandetype = demandetypeOptional.get();

            // Mettre à jour l'état de DemandeSubtype en 'Accepter'
            demandetype.setEtatDemandetype(EtatDemandetype.Accepter);
            emailService.sendSimpleEmail(emailuser,"Votre demande d’ajout de type a été acceptée","Bonjour" +name+lastname+"\n Votre demande concernant l’ajout d’un nouveau type, **"+demandetype.getName()+"**, a été approuvée. \n Nous avons procédé à son ajout dans notre système, et il est désormais accessible à l’ensemble des utilisateurs. \n Nous vous remercions de votre initiative, qui contribue à enrichir notre plateforme. Si vous avez d'autres suggestions ou remarques, n’hésitez pas à nous les partager \n Cordialement.");
            Types newType = new Types();
            newType.setName(demandetype.getName());  // Exemple : vous pouvez prendre les propriétés nécessaires du DemandeSubtype
            newType.setDescription(demandetype.getDescription());
            newType.setLogo(demandetype.getLogo());// Exemple de propriété

            // Associer le Type avec l'entité DemandeSubtype si nécessaire
           // demandetype.settype(newType);  // Si DemandeSubtype a une relation avec Type

            // Sauvegarder le nouveau 'Type' dans le repository
            typesRepository.save(newType);

            // Sauvegarder le DemandeSubtype mis à jour dans le repository
             demandeTypeRepository.save(demandetype);
             return  demandetype;// Retourner l'objet mis à jour
        } else {
            // Si l'objet DemandeSubtype n'est pas trouvé, vous pouvez lever une exception
            // ou gérer l'erreur autrement, selon vos besoins
            throw new EntityNotFoundException("DemandeSubtype with ID " + id + " not found");
        }
    }
    public DemandeType RefuseeDemandetype(Long id) {
        Optional<DemandeType> demandetypeOptional = demandeTypeRepository.findById(id);
        User user= demandetypeOptional.get().getUser();
        String emailuser=user.getEmail();
        String name= user.getName();
        String lastname=user.getSurname();
        if (demandetypeOptional.isPresent()) {
            DemandeType demandetype = demandetypeOptional.get();

            // Mettre à jour l'état de DemandeSubtype en 'Accepter'
            demandetype.setEtatDemandetype(EtatDemandetype.Refusee);
            emailService.sendSimpleEmail(emailuser,"Refus de votre demande d’ajout de type","Bonjour" +name+lastname+"\n Nous avons bien pris en compte votre demande d’ajout du type **"+demandetype.getName()+"****. Après une analyse approfondie, nous avons le regret de vous informer que cette demande ne peut pas être acceptée à ce stade. Les raisons sont les suivantes : \n -Raison spécifique: \"Le type proposé ne répond pas aux critères actuels de notre système.\"  \n Nous comprenons que cette réponse puisse ne pas correspondre à vos attentes, et nous vous encourageons à reformuler ou ajuster votre demande si nécessaire. Si vous avez des questions ou souhaitez en discuter davantage, nous restons bien entendu à votre disposition pour toute explication complémentaire.\n Merci de votre compréhension et de votre collaboration. \n Cordialement.");

            // Sauvegarder le DemandeSubtype mis à jour dans le repository
            return demandeTypeRepository.save(demandetype); // Retourner l'objet mis à jour
        } else {
            // Si l'objet DemandeSubtype n'est pas trouvé, vous pouvez lever une exception
            // ou gérer l'erreur autrement, selon vos besoins
            throw new EntityNotFoundException("DemandeSubtype with ID " + id + " not found");
        }
    }
    public List<DemandeType> searchByKeyword(@Param("keyword") String keyword){
        return demandeTypeRepository.searchByKeyword(keyword);
    }


}
