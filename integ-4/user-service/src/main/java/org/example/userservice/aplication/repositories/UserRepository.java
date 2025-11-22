package org.example.userservice.aplication.repositories;

import org.example.userservice.domain.dto.UserDto;
import org.example.userservice.domain.dto.UserEmailDto;
import org.example.userservice.domain.entities.User;
import org.example.userservice.domain.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT DISTINCT new org.example.userservice.domain.dto.UserDto(u) " +
                "FROM User u "+
                "JOIN u.accounts a "+
                "WHERE a.type = :type")
    List<UserDto> findAllByAccountType(@Param("type") AccountType type);
    @Query(" select  count(*) " +
            "FROM User u " +
            "WHERE LOWER(u.mail) LIKE LOWER(:mail) ")
    int countFindByMail(String mail);
    @Query("SELECT new org.example.userservice.domain.dto.UserDto(u) FROM User u " +
            "WHERE LOWER(u.mail) LIKE LOWER(:mail)")
    UserEmailDto findByMail(String mail);
}
