package tn.esprit.pfespringespritdeltatrue.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;  // This should refer to your ERole enum

  // Default constructor
  public Role() {
  }

  // If you need relationships, ensure they're correct.
  // For example, one-to-many relationship with User (if a User can have many roles):
  // @OneToMany(mappedBy = "role") or @ManyToOne depending on your use case.
}
