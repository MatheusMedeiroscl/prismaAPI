package com.medeiros.prisma_api.repositories;

import com.medeiros.prisma_api.domains.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Boolean existsByEmail (String email);
    UserDetails findByEmail(String email);

}
