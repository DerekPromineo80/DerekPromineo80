package tree.harvest.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tree.harvest.entity.Forester;

public interface ForesterDao extends JpaRepository<Forester, Long>{

	Optional<Forester> findByForesterEmail(String forresterEmail);

}
