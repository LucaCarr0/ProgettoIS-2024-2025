package controller;

import entity.EntityPoetUp;
import entity.EntityUtente;

public class ControllerPoetUp {
	
	public static String registrazione(String nickname,String email,String pwd) {
		
        

        // Salva il nuovo utente
        Integer result = EntityPoetUp.registrazione(nickname,email,pwd);

        if (result==0) return "Registrazione completata!";
        else return "Utente già esistente!";
	}
	
	public static String autenticazione(String email, String pwd) {
		Integer result = EntityPoetUp.autenticazione(email,pwd);
		if(result == 0) return "Utente autenticato";
		else return "Autenticazione fallita";
	}

	public static String addRaccolta(String titolo, String descrizione) {
		EntityUtente utente= new EntityUtente();
		Integer result = utente.addRaccolta(titolo,descrizione);
		if(result == -1) return "Creazione fallita";
		else return "Raccolta Creata";
	}
}
