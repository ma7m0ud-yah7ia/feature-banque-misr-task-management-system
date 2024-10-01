package banquemisr.challenge05.common.security.entity;

//import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

//@Entity
//@Table(name = "user_entity")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "username")
    private String username;

//    @Column(name = "email")
    private String email;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role_mapping" ,
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}