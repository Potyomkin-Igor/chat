package project.net.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DtoUser {
    @Email
    @NotNull
    @Pattern(regexp = "|^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
            + "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "{email.valid}")
    private String email;
    @NotNull
    @Size(min = 5, max = 15, message = "{password.size}")
    private String password;
    @NotNull
    private String confirmPassword;
}
