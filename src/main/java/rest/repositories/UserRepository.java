package rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.model.User;


public interface UserRepository extends JpaRepository<User, Long>
{
/*   @Query("SELECT u FROM Role u WHERE u.name = :name")
   User findByUsername(@Param("name") String name);*/

   User findByUsername(String username);

}