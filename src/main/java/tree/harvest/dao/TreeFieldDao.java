package tree.harvest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tree.harvest.entity.TreeField;

public interface TreeFieldDao extends JpaRepository<TreeField, Long> {

	
}
