package tn.esprit.pfespringespritdeltatrue.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pfespringespritdeltatrue.Entities.ERole;
import tn.esprit.pfespringespritdeltatrue.Entities.Role;
import tn.esprit.pfespringespritdeltatrue.Repositories.RoleRepository;
@Service
@Slf4j
public class Roleservice {
    @Autowired
    RoleRepository roleRepository;


    private Role addRoleIfNotExists(Role roleName) {
           return roleRepository.save(roleName);
    }
}
