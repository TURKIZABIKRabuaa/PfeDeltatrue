package tn.esprit.pfespringespritdeltatrue.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemandeSubtype implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String description;
    private String logo;
    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private EtatDemandeSubtype etatDemandeSubtype=EtatDemandeSubtype.enAttente;
    @ManyToOne
    private Types type;
}
