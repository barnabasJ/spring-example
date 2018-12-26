package net.jovacorp.retroboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id @GeneratedValue private Long id;

  private String username;

  private String password;

  private String role;
}
