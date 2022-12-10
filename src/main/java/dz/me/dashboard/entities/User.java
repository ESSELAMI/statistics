package dz.me.dashboard.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author MEKRICHE TAREK
 */
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
  private UUID id;

  private String username;

  private String firstname;

  private String lastname;

  private String email;
  @JsonIgnore
  @Size(max = 120)
  private String password;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  private boolean isAccountNonExpired;

  private boolean isAccountNonLocked;

  private boolean isCredentialsNonExpired;

  private boolean isEnabled;

  @ManyToOne
  @JoinColumn(name = "id_service")
  private Service service;

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    isAccountNonExpired = true;
    isAccountNonLocked = true;
    isCredentialsNonExpired = true;
    isEnabled = true;
  }

}
