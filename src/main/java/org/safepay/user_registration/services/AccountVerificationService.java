package org.safepay.user_registration.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.safepay.user_registration.entities.Users;
import org.safepay.user_registration.repositories.UsersRepository;
import org.safepay.user_registration.util.JwtUtility;
import org.safepay.user_registration.util.ResponseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@Slf4j
public class AccountVerificationService {

    private final UsersRepository usersRepository;

    private final JwtUtility jwtUtility;

    private final RedisTemplate<String,String> redisTemplate;

    @Autowired
    AccountVerificationService(UsersRepository usersRepository, JwtUtility jwtUtility, RedisTemplate<String,String> redisTemplate){
        this.usersRepository = usersRepository;
        this.jwtUtility = jwtUtility;
        this.redisTemplate = redisTemplate;
    }

    public ResponseUtility activateNewUserAccount(String token){
        ResponseUtility response = new ResponseUtility();
        try {
            String value = redisTemplate.opsForValue().get(token);
            //If token is not present in redis or is already verified ("Y") or is expired then return false.
            if(value == null || value.equals("Y") || jwtUtility.isTokenExpired(token)){
                response.setResult(false);
                response.setMessage("Token Expired! Or is Invalid!");
                return response;
            }
            String email = jwtUtility.extractEmail(token);
           if (!areTheReceivedNewUserDetailsValid(email)) {
                response.setResult(false);
                response.setMessage("User account does not exist.");
            }
        }
        catch(Exception ex){
                response.setResult(false);
                response.setMessage("Something went wrong! Please try again!");
                log.error("Error occurred during account activation", ex);
        }
        if(response.getResult() == null){
            response.setResult(true);
            response.setMessage("Account verification successful! You may close this window and login.");
            //Now that we have used this token, we can set this token's value to Y so that it counts as expired according to our above logic, and in future we will avoid unnecessary database calls
            redisTemplate.opsForValue().set(token,"Y");
        }
        return response;
    }

    @Transactional
    private boolean areTheReceivedNewUserDetailsValid(String email){
        Users newUserAccount = usersRepository.findByEmailId(email);
        if(newUserAccount != null && newUserAccount.getIsActive() == 'N'){
            //User account exists, so set the flag to 'yes' in table and update the id activation time as well.
            newUserAccount.setIsActive('Y');
            newUserAccount.setIdActivatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
            usersRepository.save(newUserAccount);
            return true;
        }
        return false;
    }
}
