package br.com.douglas.project.build_token.repository;

import br.com.douglas.project.build_token.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassWord(String email, String password);

}
