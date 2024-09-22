package org.safepay.user_registration.services;

import lombok.extern.slf4j.Slf4j;
import org.safepay.user_registration.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class VerificationEmailSenderService {

    private final JavaMailSender mailSender;

    private final JwtUtility jwtUtility;

    private final RedisTemplate<String,String> redisTemplate;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    VerificationEmailSenderService(JavaMailSender mailSender, JwtUtility jwtUtility, RedisTemplate<String, String> redisTemplate){
        this.mailSender = mailSender;
        this.jwtUtility = jwtUtility;
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "sign-up", groupId = "email_group")
    public void consume(String email){
        log.info("New user registered with the mail id {}", email);
        sendVerificationEmail(email);
    }

    public void sendVerificationEmail(String email){
        String token = jwtUtility.generateToken(email);
        String verificationLink = "http://localhost:8080/verify-account/new-user?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(fromEmail);
        message.setSubject("Verify Your Account");
        message.setText("Please click on the link to verify your account. \n\n" + verificationLink);
        mailSender.send(message);
        //We have generated and sent a token for verification, it is not yet verified so let's keep it as "N" (not verified) in redis.
        redisTemplate.opsForValue().set(token, "N", Duration.ofHours(24));
        log.info("Verification email sent to {}", email);
    }

}
