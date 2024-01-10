package com.contabancaria.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil{

	private static final Logger log = LoggerFactory.getLogger(PasswordUtil.class);
	
	public PasswordUtil() {}
	
	public static String gerarSenha(String senha) {
		if(senha == null) {
			return senha;
		}
		
		log.info("GERANDO HASH DA SENHA.");
		BCryptPasswordEncoder password = new BCryptPasswordEncoder();
		return password.encode(senha);
	}
}
