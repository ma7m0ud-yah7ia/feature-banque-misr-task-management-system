package banquemisr.challenge05.common.security.entity;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



//@Entity
//@Table(name = "keycloak_role")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String name;
}
