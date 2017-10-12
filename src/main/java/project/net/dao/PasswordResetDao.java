package project.net.dao;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.net.model.PasswordResetToken;

@Repository
public interface PasswordResetDao extends PagingAndSortingRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    void deleteByToken(String token);
}
