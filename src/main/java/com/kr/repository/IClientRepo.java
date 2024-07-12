package com.kr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kr.model.ClientEntity;

import jakarta.transaction.Transactional;

public interface IClientRepo extends JpaRepository<ClientEntity, Integer>{
	ClientEntity findBycEmail(String email);
	
	@Query(value="UPDATE  ClientEntity SET cPswd=?1 WHERE cEmail=?2")
	@Modifying
	@Transactional
	public int changeCPswdByCEmail(String pswd,String email);
}
