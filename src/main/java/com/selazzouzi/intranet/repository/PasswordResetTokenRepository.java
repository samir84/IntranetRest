package com.selazzouzi.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selazzouzi.intranet.model.PasswordResetToken;
import com.selazzouzi.intranet.model.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

}
