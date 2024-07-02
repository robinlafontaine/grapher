package io.lafontaine.grapher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphRepository extends JpaRepository<GraphDB, Long> {
}
