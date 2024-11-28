package tn.esprit.pfespringespritdeltatrue.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pfespringespritdeltatrue.Entities.Role;
import tn.esprit.pfespringespritdeltatrue.Repositories.RoleRepository;
import tn.esprit.pfespringespritdeltatrue.Services.Roleservice;

import java.util.Optional;

@RestController
@RequestMapping("Role")
public class RoleController {
    @Autowired
    Roleservice roleservice;
    @Autowired
    RoleRepository roleRepository;
    @PostMapping("addRole")
    public Role RoleAdd(Role role) {

            return roleRepository.save(role); // Save and return the new role
            }

}
