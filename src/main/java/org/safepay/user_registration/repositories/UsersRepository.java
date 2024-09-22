package org.safepay.user_registration.repositories;

import org.safepay.user_registration.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Bound to Users table, CRUD and other desired operations can be performed.
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findByEmailId(String email);
}
