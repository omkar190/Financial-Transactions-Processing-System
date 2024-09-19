package org.safepay.user_registration.services;

import jakarta.transaction.Transactional;
import org.safepay.user_registration.dto.NewUserDTO;
import org.safepay.user_registration.entities.Users;
import org.safepay.user_registration.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class NewUserRegistrationService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    NewUserRegistrationService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public String registerNewUser(NewUserDTO newUserDTO){
        Users user = new Users();
        setNewUserDetailsFromDTO(user, newUserDTO);
        usersRepository.save(user);
        return "Welcome " + user.getUsername() + " " + user.getSurname() + "! Please verify your account by following the steps mentioned in the mail sent to you on the registered email address.";
    }

    private void setNewUserDetailsFromDTO(Users user, NewUserDTO newUserDTO){
        user.setUsername(newUserDTO.getUsername());
        user.setSurname(newUserDTO.getSurname());
        user.setEmailId(newUserDTO.getEmailId());
        user.setMobNo(newUserDTO.getMobNo());
        user.setIsActive('Y');
        user.setUserPassword(bCryptPasswordEncoder.encode(newUserDTO.getUserPassword()));
    }

}
