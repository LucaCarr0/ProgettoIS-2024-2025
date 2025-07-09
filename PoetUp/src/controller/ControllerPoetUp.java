package controller;

import entity.EntityPoetUp;
import entity.EntityUtente;
import facade.FacadeUtenti;

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
		
		Integer result = FacadeUtenti.addRaccolta(titolo,descrizione);
		if(result == -1) return "Creazione fallita";
		else return "Raccolta Creata";
	}

	public static String pubblicazionePoesia(String titolo, String testo, String tag, String raccolta,
			boolean visibilita) {
		Integer result = FacadeUtenti.pubblicazionePoesia(titolo,testo,tag,raccolta,visibilita);
		if(result == -1) return "Creazione fallita";
		else return "Poesia Pubblicata!";
	}
}
