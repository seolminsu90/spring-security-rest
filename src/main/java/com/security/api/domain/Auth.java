package com.security.api.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TB_AUTH")
public class Auth implements Serializable{
	@Id
	private Long id;
	private String role;
}
