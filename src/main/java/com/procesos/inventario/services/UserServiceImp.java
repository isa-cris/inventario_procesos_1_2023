package com.procesos.inventario.services;
import com.procesos.inventario.models.User;
import com.procesos.inventario.repository.UserRepository;
import com.procesos.inventario.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtutil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Boolean createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception e){
        return true;
    }
    }

    @Override
    public List<User> alluser() {
        return userRepository.findAll();
    }//lista de todos los usuarios

    @Override
    public Boolean updateUser(Long id, User user) {
        try{
        User userBD = userRepository.findById(id).get();
        userBD.setFirstName(user.getFirstName());
        userBD.setLastName(user.getLastName());
        userBD.setAddress(user.getAddress());
        userBD.setBirthday(user.getBirthday());
        userBD.setPassword(passwordEncoder.encode(user.getPassword()));
        User userUp = userRepository.save(userBD);
        return true;
    }catch (Exception e){
        return false;
    }
    }
    @PostMapping(value = "auth/login")
    public String login(User user) {
        Optional <User> userBd = userRepository.findByEmail(user.getEmail());
        if (userBd.isEmpty()){
            throw new RuntimeException("Usuario no encontrado");
        }
        if (!passwordEncoder.matches(user.getPassword(),userBd.get().getPassword())){
            throw new RuntimeException("La contraseña es incorecta");
        }
        return jwtutil.create(String.valueOf(userBd.get().getId()),
                              String.valueOf(userBd.get().getEmail()));
    }
}
