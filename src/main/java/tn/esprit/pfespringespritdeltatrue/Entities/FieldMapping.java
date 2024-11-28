package tn.esprit.pfespringespritdeltatrue.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldMapping implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subtype_id", nullable = false)
    private Subtype subtype;

    private String fieldName;  // Nom du champ que l'utilisateur choisit
    private String excelColumn;  // Nom de la colonne Excel associée
}

