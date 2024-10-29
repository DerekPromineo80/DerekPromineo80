package tree.harvest.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tree.harvest.entity.Forrester;

public interface ForresterDao extends JpaRepository<Forrester, Long>{

	Optional<Forrester> findByForresterEmail(String forresterEmail);

}
