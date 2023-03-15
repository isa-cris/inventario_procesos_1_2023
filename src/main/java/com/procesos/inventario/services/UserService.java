package com.procesos.inventario.services;
import com.procesos.inventario.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUser(Long id);
    Boolean createUser (User user);
    List<User> alluser();
    Boolean updateUser(Long id, User user);
}
