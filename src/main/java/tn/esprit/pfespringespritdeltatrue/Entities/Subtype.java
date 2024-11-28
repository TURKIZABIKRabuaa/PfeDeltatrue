package tn.esprit.pfespringespritdeltatrue.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subtype implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    public Long id;
    private String name;
    private String description;
    private String logo;
    @JsonIgnore
    @ManyToOne
    public Types types;
    @OneToMany(mappedBy = "subtype", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Field> fields;



}

