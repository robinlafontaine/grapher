package io.lafontaine.grapher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphRepository extends JpaRepository<GraphDB, Long> {

    GraphDB findByhashcode(int hashcode);

    List<GraphDB> findAll();

}
