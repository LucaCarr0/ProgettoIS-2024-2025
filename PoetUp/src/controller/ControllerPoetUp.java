package controller;

import java.util.ArrayList;

import dto.PoesiaDTO;
import dto.ProfiloPersonaleDTO;
import dto.RaccoltaDTO;
import entity.EntityPoetUp;
import facade.FacadeUtenti;
import java.sql.Date;

public class ControllerPoetUp {

	public static String registrazione(String nickname,String email,String pwd) {



        // Salva il nuovo utente
        Integer result = EntityPoetUp.registrazione(nickname,email,pwd);

        if (result==0) {
			return "Registrazione completata!";
		} else {
			return "Utente già esistente!";
		}
	}

	public static String autenticazione(String email, String pwd) {
		Integer result = EntityPoetUp.autenticazione(email,pwd);
		if(result == 0) {
			return "Utente autenticato";
		} else {
			return "Autenticazione fallita";
		}
	}

	public static String addRaccolta(String titolo, String descrizione) {

		Integer result = FacadeUtenti.addRaccolta(titolo,descrizione);
		if(result == -1) {
			return "Creazione fallita";
		} else {
			return "Raccolta Creata";
		}
	}

	public static String pubblicazionePoesia(String titolo, String testo, String tag, String raccolta,
			boolean visibilita) {
		Integer result = FacadeUtenti.pubblicazionePoesia(titolo,testo,tag,raccolta,visibilita);
		if(result == -1) {
			return "Creazione fallita";
		} else {
			return "Poesia Pubblicata!";
		}
	}

	public static ArrayList<PoesiaDTO> visualizzaFeed() {
		// TODO Auto-generated method stub
		ArrayList<PoesiaDTO> feed = EntityPoetUp.visualizzaFeed();
		return feed;
	}
	
	public static String modificaProfilo(String nome, String cognome, Date dataNascita, String biografia) {
	  
	    Integer result = FacadeUtenti.modificaProfilo(nome, cognome, dataNascita, biografia);

	    if(result == -1) {
			return "Errore durante la modifica del profilo.";
		} else {
			return "Profilo aggiornato con successo!";
		}
	    
	}

	public static ProfiloPersonaleDTO getProfiloUtente() {
		ProfiloPersonaleDTO profilo = FacadeUtenti.getProfiloUtente();
		return profilo;
	}

	public static ArrayList<RaccoltaDTO> getRaccolteByUtente() {
		ArrayList<RaccoltaDTO> raccolte = FacadeUtenti.getRaccolteByUtente();
		return raccolte;
	}

	
}
