package tree.harvest.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Forrester {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long forresterId;
	
	private String forresterFirstName;
	private String forresterLastName;
	
	
	@Column(unique = true)
	private String forresterEmail;
	

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "forrester", cascade = CascadeType.ALL)
	private Set<TreeField> treeFields = new HashSet<>();
	
}