package com.kr.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kr.model.ClientEntity;
import com.kr.repository.IClientRepo;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	IClientRepo clientRepo;

	@Autowired
	JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Override
	public void addNewClient(ClientEntity c) {
		clientRepo.save(c);
		System.out.println(c.getCName() + ":: Added\n" + c);
	}

	@Override
	public ClientEntity getClient(String email) {
		return clientRepo.findBycEmail(email);
	}

	private String generateOTP() {
		String numbers = "0123456789";
		Random random = new Random();
		String otp = "";
		for (int i = 0; i < 6; i++) {
			otp += numbers.charAt(random.nextInt(numbers.length()));
		}
		return otp;
	}

	@Override
	public String sendOTP(String toEmail) {
		String otp = generateOTP();
		String content = "Verification code\r\n" + "Please use the verification code below to sign in.\r\n" + "\r\n"
				+ otp + "\r\n" + "\r\n" + "If you didn’t request this, you can ignore this email.\r\n" + "\r\n"
				+ "Thanks,\r\n" + "The team";
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(toEmail);
		msg.setFrom(fromEmail);
		msg.setSubject("Your OTP");
		msg.setText(content);
//		mailSender.send(msg);
		return otp;
	}

	@Override
	public int updatePswd(String pswd, String mail) {
		return clientRepo.changeCPswdByCEmail(pswd, mail);
	}

}
