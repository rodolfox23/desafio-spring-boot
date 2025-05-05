package com.previred.entrevista.dao;

import com.previred.entrevista.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
    @Query(value = "select t from Token t inner join Usuario u on t.user.user_id = u.user_id where u.user_id = :id and (t.expired = false or t.revoked = false) ")
    List<Token> findAllValidTokenByUser(Long id);
}
