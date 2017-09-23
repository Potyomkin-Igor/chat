package project.net.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.net.model.User;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> {
   User findUserByEmail(String email);
}