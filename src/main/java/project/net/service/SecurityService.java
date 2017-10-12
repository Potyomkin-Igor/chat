package project.net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import project.net.dao.PasswordResetDao;
import project.net.model.PasswordResetToken;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

@Service
@Transactional
public class SecurityService {
    private MessageSource messages;
    private PasswordResetDao passwordResetDao;

    @Autowired
    public SecurityService(MessageSource messages, PasswordResetDao passwordResetDao) {
        this.messages = messages;
        this.passwordResetDao = passwordResetDao;
    }

    public String validateToken(String token, Locale local) {
        String result = null;
        PasswordResetToken passToken = passwordResetDao.findByToken(token);
        if (Objects.isNull(passToken)) {
            result = messages.getMessage("token.invalid", null, local);
        } else if (passToken.getExpiryDate().isBefore(LocalDate.now())) {
            result = messages.getMessage("date.expired", null, local);
        }
        return result;
    }

    public void deleteToken(String token) {
        passwordResetDao.deleteByToken(token);
    }

    public PasswordResetToken getByPasswordTokenByToken(String token) {
        return passwordResetDao.findByToken(token);
    }

}
