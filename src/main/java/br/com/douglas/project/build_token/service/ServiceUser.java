package br.com.douglas.project.build_token.service;


import br.com.douglas.project.build_token.dto.RecordUser;
import br.com.douglas.project.build_token.entity.User;
import br.com.douglas.project.build_token.repository.UserRepository;
import br.com.douglas.project.build_token.service.exception.ExistingUserException;
import br.com.douglas.project.build_token.service.exception.NonExistingUserException;
import br.com.douglas.project.build_token.service.exception.PermissionDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.ClassUtils.isPresent;

@Service
public class ServiceUser {

    private UserRepository userRepository;
    private JWTSevice jwtSevice;

    public ServiceUser(UserRepository userRepository, JWTSevice jwtSevice) {
        this.userRepository = userRepository;
        this.jwtSevice = jwtSevice;
    }


    public User createUser(RecordUser recordUser) {
        Optional<User> returnedUser = userRepository.findByEmail(recordUser.email());

        if(returnedUser.isPresent()){
            throw new ExistingUserException("Usuario já existente no sistema.");
        }
        User SaveNewUser = new User(recordUser);

        userRepository.save(SaveNewUser);

        return SaveNewUser;
    }


    private boolean userWithPermission(String header , String email){
        String userToken = jwtSevice.getUserByToken(header);

        Optional<User> returnedUser = userRepository.findByEmail(userToken);

        return returnedUser.isPresent() && returnedUser.get().getEmail().equals(email);
    }


    public User getUser(String email, String header) {
        Optional<User> returnedUser = userRepository.findByEmail(email);

        if(returnedUser.isEmpty()) {
            throw new NonExistingUserException("Usuário não existente.");
        }
        if(!userWithPermission(header,email)) {
            throw new PermissionDeniedException("Usuário não tem permissão para executar esta ação.");
        }
        return returnedUser.get();
    }


    public void deleteUser(String header, String email) {
        Optional<User> userRemoved = userRepository.findByEmail(email);

        if (userRemoved.isEmpty()) {
            throw new NonExistingUserException("Usuário não existente.");
        }
        if (!userWithPermission(header, email)) {
            throw new PermissionDeniedException("Usuário não tem permissão para executar esta ação.");
        }
        userRepository.delete(userRemoved.get());
    }

}
