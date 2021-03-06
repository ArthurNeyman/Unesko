package com.unesco.core.managers.account.userManager.interfaces.user;

import com.unesco.core.managers.IManager;
import com.unesco.core.managers.IValidateManager;
import com.unesco.core.dto.account.RoleDTO;
import com.unesco.core.dto.account.UserDTO;
import com.unesco.core.dto.additional.ResponseStatusDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserManager extends IManager<UserDTO>, IValidateManager {

    void init(UserDTO User);
    void Create(UserDTO User, List<RoleDTO> roleList);
    void CleanPassField();
    ResponseStatusDTO ChangePassword(String newPass, String oldPass);
    ResponseStatusDTO ChangePhoto(String photo);

    UserDTO get();
}
