package uni.travelguide.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uni.travelguide.model.Role;


@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String role);
}