package tree.harvest.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Forester {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long foresterId;
	
	private String foresterFirstName;
	private String foresterLastName;
	
	@Column(unique = true)
	private String foresterEmail;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "tree_field_forester",
            joinColumns = @JoinColumn(name = "tree_field_id"),
            inverseJoinColumns = @JoinColumn(name = "forester_id"))	
	private Set<TreeField> treeFields = new HashSet<>();
	
}
