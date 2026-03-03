package com.project.user_service.repositories;

import com.project.user_service.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE User p SET p.address = :address, p.name = :name, p.age = :age WHERE p.ssn = :ssn")
    int updatePerson(@Param("ssn") long ssn, @Param("address") String address, @Param("name") String name, @Param("age") int age);
}
