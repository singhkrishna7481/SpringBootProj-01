package com.kr.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kr.model.ClientEntity;
import com.kr.service.IClientService;

@Controller
public class ClientController {
	@Autowired
	IClientService clientService;

	@GetMapping("/")
	public String homepage(Model model) {
		model.addAttribute("client", new ClientEntity());
		return "index";
	}

	@GetMapping("/index")
	public String homePage(Model model) {
		model.addAttribute("client", new ClientEntity());
		return "index";
	}

	@GetMapping("/forgot-pswd")
	public String forgot(Model model) {
		model.addAttribute("clientOTP", new ClientEntity());
		return "forgot-pswd";
	}

	@GetMapping("/sign-up")
	public String signUp() {
		return "sign-up";
	}

	@PostMapping("/register-client")
	public String newClient(ClientEntity c,Model model) {
		System.out.println(c);
		try {
			if(c.getPswd().equals(c.getCPswd()))
			{
				clientService.addNewClient(c);
				model.addAttribute("msg","Registration Success");
				return "success-page";
			}
			else
			{
				model.addAttribute("error","Password Didn't Matched");
				return "sign-up";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg",e.getMessage());
			return "error-page";
		}
	}
	@PostMapping("/update-client")
	public String updateClient(ClientEntity c) {
		clientService.addNewClient(c);
		System.out.println(c);
		return "index";
	}

	@PostMapping("/client-login")
	public String loginClient(@ModelAttribute ClientEntity c,Model model) {
		ClientEntity client = clientService.getClient(c.getCEmail());
		try {
			if (Objects.nonNull(client)) {
				if (client.getCPswd().equals(c.getCPswd())) {
					model.addAttribute("clientname",client.getCName());
					model.addAttribute("clientid",client.getId());
					model.addAttribute("clientemail",client.getCEmail());
					model.addAttribute("clientphno",client.getCPhNo());
					model.addAttribute("clientdob",client.getCDob());
					return "profile";
				} else {
					model.addAttribute("passError","Invalid Password");
					return homepage(model);
				}
			} else {
				model.addAttribute("emailError","User Not Found");
				return homePage(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg",e.getMessage());
			return "error-page";
		}
		
	}

	private String otp;
	private String email;

	@PostMapping("/send-otp")
	public String sendOTP(@ModelAttribute("clientOTP") ClientEntity c,Model model) {
		try {
			if(Objects.nonNull(clientService.getClient(c.getCEmail())))
			{
				email = c.getCEmail();
				otp = clientService.sendOTP(email);
				System.out.println(otp);
				model.addAttribute("otpSent","OTP Sent To: "+email);
				return "forgot-pswd";
			}
			else
			{
				model.addAttribute("errorMsg",email+" Not Found");
				return "forgot-pswd";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg",e.getMessage());
			return "error-page";
		}
	}

	@PostMapping("/verify-otp")
	public String verifyOTP(@ModelAttribute("clientOTP") ClientEntity c, Model model) {
		try {
			if (this.otp.equals(c.getCPswd())) {
				model.addAttribute("clientUpdate", new ClientEntity());
				return "update-pswd";
			} else {
				model.addAttribute("errorOtp","Invalid OTP");
				return "forgot-pswd";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg",e.getMessage());
			return "error-page";
		}
	}
	@PostMapping("/update-records")
	public String updateRecords()
	{
		return "update-records";
	}
	
	@PostMapping("/update-pswd")
	public String updatePswd(@ModelAttribute("clientUpdate") ClientEntity c, Model model) {
		try {
			String pswd = c.getPswd();
			String cPswd = c.getCPswd();
			if((Objects.nonNull(pswd) && Objects.nonNull(cPswd)) && pswd.equals(cPswd))
			{
				if(Objects.nonNull(email))
				{
					System.out.println(email);
					int k = clientService.updatePswd(pswd, email);
					System.out.println(clientService.getClient(email));
					System.out.println(k);
					model.addAttribute("msg","Password Changed");
					email=null;
					return "success-page";
				}
				else
				{
					model.addAttribute("errorMsg","Session Expired");
					return "error-page";
				}
			}
			else
			{
				model.addAttribute("errorMsg","Password Didn't Match");
				return "update-pswd";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMsg",e.getMessage());
			return "error-page";
		}
	}
}
