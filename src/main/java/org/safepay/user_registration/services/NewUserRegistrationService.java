package org.safepay.user_registration.services;

import jakarta.transaction.Transactional;
import org.safepay.user_registration.dto.NewUserDTO;
import org.safepay.user_registration.entities.Users;
import org.safepay.user_registration.repositories.UsersRepository;
import org.safepay.user_registration.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class NewUserRegistrationService {

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String TOPIC = "sign-up";

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    NewUserRegistrationService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder, KafkaTemplate<String,String> kafkaTemplate, JwtUtility jwtUtility){
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public String registerNewUser(NewUserDTO newUserDTO) {
        Users user = new Users();
        setNewUserDetailsFromDTO(user, newUserDTO);
        usersRepository.save(user);

        //Check if the email is sent properly. If not, rollback the overall transaction.
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, user.getEmailId());
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
        return "Welcome " + user.getUsername() + " " + user.getSurname() + "! Please verify your account by following the steps mentioned in the mail sent to you on the registered email address.";
    }

    private void setNewUserDetailsFromDTO(Users user, NewUserDTO newUserDTO){
        user.setUsername(newUserDTO.getUsername());
        user.setSurname(newUserDTO.getSurname());
        user.setEmailId(newUserDTO.getEmailId());
        user.setMobNo(newUserDTO.getMobNo());
        user.setIsActive('N');
        user.setUserPassword(bCryptPasswordEncoder.encode(newUserDTO.getUserPassword()));
    }

}
