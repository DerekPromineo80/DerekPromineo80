package tree.harvest.dao;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tree.harvest.entity.Tree;

public interface TreeDao extends JpaRepository<Tree, Long> {
    @Query(value = "SELECT * FROM tree WHERE tree = ?1", nativeQuery = true)
    Tree findByName(String tree);
    
	Set<Tree> findAllByTreeIn(Set<String> trees);
}
