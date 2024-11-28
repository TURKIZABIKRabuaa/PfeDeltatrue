package tn.esprit.pfespringespritdeltatrue.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pfespringespritdeltatrue.Entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    @Query("SELECT u FROM User u WHERE u.connected = 'Connected' AND u.etatCompte = 'Active'")
    List<User> ConnectedUser();

    @Query("SELECT u FROM User u WHERE u.connected = 'Disconnected' AND u.etatCompte = 'Active'")
    List<User> DisconnectedUser();

    @Query("SELECT u FROM User u WHERE u.etatCompte = 'Active'")
    List<User> CompteActiveUser();

    @Query("SELECT u FROM User u WHERE u.etatCompte = 'Bloquee'")
    List<User> BloqueeUser();
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.surname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByKeyword(@Param("keyword") String keyword);
}
