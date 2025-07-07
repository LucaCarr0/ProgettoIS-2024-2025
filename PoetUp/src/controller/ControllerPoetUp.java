package controller;

import entity.EntityPoetUp;

public class ControllerPoetUp {
	
	public static String registrazione(String nickname,String email,String pwd) {
		
        

        // Salva il nuovo utente
        Integer result = EntityPoetUp.registrazione(nickname,email,pwd);

        if (result==0) return "Utente registrato";
        else return "Registrazione fallita";
	}
	
	public static String autenticazione(String email, String pwd) {
		Integer result = EntityPoetUp.autenticazione(email,pwd);
		if(result == 0) return "Utente autenticato";
		else return "Autenticazione fallita";
	}
}
