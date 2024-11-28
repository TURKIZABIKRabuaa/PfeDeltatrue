package tn.esprit.pfespringespritdeltatrue.Entities;

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
public class Field implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;  // Nom du champ (ex : "Nom", "Date", etc.)
    private String type;  // Type du champ (ex : "string", "date", "number")

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtype_id")
    private Subtype subtype;  // Référence à l'entité Subtype
}

