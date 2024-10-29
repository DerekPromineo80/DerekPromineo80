package tree.harvest.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import tree.harvest.entity.Tree;

public interface TreeDao extends JpaRepository<Tree, Long> {

	Set<Tree> findAllByTreeIn(Set<String> trees);
	
}
