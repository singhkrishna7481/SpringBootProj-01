package com.kr.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "CLIENT_DATA")
public class ClientEntity {
	@Id
	@Column(name = "C_ID")
	@SequenceGenerator(name = "g2",initialValue = 1000,allocationSize = 1)
	@GeneratedValue(generator = "g2",strategy = GenerationType.SEQUENCE)
	private Integer Id;

	@NonNull
	@Column(name = "C_NAME",length = 30)
	private String cName;
	@NonNull
	@Column(name = "C_EMAIL",length = 40)
	private String cEmail;
	@NonNull
	@Column(name = "C_PHONE",length = 10)
	private String cPhNo;
	@NonNull
	@Column(name = "C_PSWD",length = 25)
	private String cPswd;
	@NonNull
	@Column(name = "C_DOB")
	private LocalDate cDob;
	
	@Transient
	@Column(length = 25)
	private String pswd;
}
