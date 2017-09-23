package project.net.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import project.net.model.Message;

import java.util.List;

public interface MessageDao extends PagingAndSortingRepository <Message, Long>{
    List<Message> findByUserId(Long userId);
}
