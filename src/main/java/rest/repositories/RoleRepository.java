package rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT u FROM Role u WHERE u.name = :name")
    Role findByName(@Param("name") String name);

}
