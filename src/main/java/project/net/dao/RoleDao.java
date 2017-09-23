package project.net.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.net.model.Role;

/**
 * Created by ${USER} on ${DATE}.
 */

@Repository
public interface RoleDao  extends PagingAndSortingRepository<Role, Long>{
   Role findByName(String name);
}
