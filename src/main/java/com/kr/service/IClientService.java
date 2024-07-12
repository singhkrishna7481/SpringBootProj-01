package com.kr.service;

import com.kr.model.ClientEntity;

public interface IClientService {

	public void addNewClient(ClientEntity c);
	
	public ClientEntity	getClient(String email);
	
	public String sendOTP(String toEmail);
	
	public int updatePswd(String pswd,String mail);
}
