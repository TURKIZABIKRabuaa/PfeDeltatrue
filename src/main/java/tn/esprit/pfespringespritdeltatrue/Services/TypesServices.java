package tn.esprit.pfespringespritdeltatrue.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import tn.esprit.pfespringespritdeltatrue.Entities.Types;
import tn.esprit.pfespringespritdeltatrue.Repositories.DemandeTypeRepository;
import tn.esprit.pfespringespritdeltatrue.Repositories.TypesRepository;


import java.util.List;

@Service
public class TypesServices {
    @Autowired
    TypesRepository typesRepository;
    @Autowired
    DemandeTypeRepository demandeTypeRepository;

    public Types AddTypes (Types type){
            return typesRepository.save(type);
    }
    public List<Types> getAllTypes(){return typesRepository.findAll();}
   public Types getTypeById(Long id){return typesRepository.findById(id).get();}
    public List<Types> searchByKeyword(String keyword){
        return typesRepository.searchByKeyword(keyword);
    }
}
