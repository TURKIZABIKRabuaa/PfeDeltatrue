package tn.esprit.pfespringespritdeltatrue.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pfespringespritdeltatrue.Entities.Types;
import tn.esprit.pfespringespritdeltatrue.Services.TypesServices;

import java.util.List;


@RestController
@RequestMapping("/Type")
public class TypesContoller {
   @Autowired
    TypesServices typesServices;

    @PostMapping("/AjouterTypes")
    public Types ajouterTypes(@RequestBody Types types) {
        typesServices.AddTypes(types);
        return types;
    }
    @GetMapping("/SerchType")
    List<Types> searchByKeyword(@Param("keyword") String keyword){
        return typesServices.searchByKeyword(keyword);
    }
   @GetMapping("/AllTypes")
    public List<Types> getAllTypes() {return typesServices.getAllTypes();}
    @GetMapping("/TypeByID")
    public Types getTypeByID(@RequestParam Long id) {return typesServices.getTypeById(id);}
}


