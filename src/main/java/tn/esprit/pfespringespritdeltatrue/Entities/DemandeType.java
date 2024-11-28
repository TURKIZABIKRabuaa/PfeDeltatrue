package tn.esprit.pfespringespritdeltatrue.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

// Import the User class
import tn.esprit.pfespringespritdeltatrue.Entities.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemandeType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Field name changed to 'id' to follow Java naming conventions

    private String name;
    private String description;
    private String logo;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)// Assuming EtatDemandetype is an enum, this annotation maps it correctly to DB
    private EtatDemandetype etatDemandetype = EtatDemandetype.enAttente;  // Default value is enAttente

    @ManyToOne
    @JoinColumn(name = "user_id")  // Optional: Specify the column name explicitly
    private User user;  // Now the User class should be recognized correctly

}
