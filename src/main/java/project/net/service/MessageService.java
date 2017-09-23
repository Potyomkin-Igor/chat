package project.net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.net.dao.MessageDao;
import project.net.model.Message;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageService {
    @Autowired
   private MessageDao messageDao;

    public void saveMessage(Message message) {
        messageDao.save(message);
    }

    public Message getMessage(Long messageId) {
        return messageDao.findOne(messageId);
    }

    public List<Message> getAll() {
        return (List<Message>) messageDao.findAll();
    }

    public List<Message> getUserMessages(Long userId) {
        return messageDao.findByUserId(userId);
    }
}
