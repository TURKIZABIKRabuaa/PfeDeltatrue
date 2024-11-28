package tn.esprit.pfespringespritdeltatrue.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pfespringespritdeltatrue.Entities.ERole;
import tn.esprit.pfespringespritdeltatrue.Entities.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByName(ERole name);
}
