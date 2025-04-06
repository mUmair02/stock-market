package org.jsp.stocks.service.implementation;

import java.time.LocalDate;
import java.util.Random;

import org.jsp.stocks.dto.User;
import org.jsp.stocks.repository.UserRepository;
import org.jsp.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;

@Service
public class StockServiceImple implements StockService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JavaMailSender mailSender;

	@Override
	public String register(User user, Model model) {
		model.addAttribute("user", user);
		return "register.html";
	}

	@Override
	public String register(@Valid User user, BindingResult result) {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "error.confirmPassword", "* Password and confirm Password are not matching");
		}
		if(user.getDob()!=null) {
			if(LocalDate.now().getYear()-user.getDob().getYear()<18) {
				result.rejectValue("dob", "error.dob", "* You should be 18+ to Create Account here");
			}
		}
		if (userRepository.existsByEmail(user.getEmail())) {
			result.rejectValue("email", "error.email", "* Email should be unique");
		}
		if (userRepository.existsByMobile(user.getMobile())) {
			result.rejectValue("mobile", "error.mobile", "* Mobile Number should be unique");
		}
		if (result.hasErrors()){
			return "register.html";
		}else {
			user.setOtp(generateOtp());
			sendEmail(user);
			userRepository.save(user);
			return "redirect:/otp/" + user.getId();
		}
	}

	private void sendEmail(@Valid User user) {
		System.err.println("hello "+user.getName()+ " your otp is :"+user.getOtp() );
		
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		
		try {
			
		
		helper.setFrom("md006umair.gmail.com", "StockMarketApp");
		helper.setTo(user.getEmail());
		helper.setSubject("OTP for account creation");
		helper.setText("<h1>hello"+user.getName()+" your otp is: "+user.getOtp()+"</h1>", true);
		mailSender.send(message);
		}
		catch (Exception e) {
			
		}
		 
	}

	private int generateOtp() {
		return new Random().nextInt(1000000,10000000);
	}

	

	@Override
	public String verifyOtp(int id, int otp) {
		User user=userRepository.findById(id).get();
		if (user.getOtp()==otp) {
			user.setVerified(true);
			userRepository.save(user);
			return "redirect:/login";
		}else {
			return "redirect:/otp/"+id;
		}
	}

	
	
}