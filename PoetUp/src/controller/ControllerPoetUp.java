package controller;

import java.sql.Date;
import java.util.ArrayList;

import dto.PoesiaCompletaDTO;
import dto.PoesiaDTO;
import dto.ProfiloPersonaleDTO;
import dto.RaccoltaDTO;
import entity.EntityPoetUp;
import facade.FacadePoesie;
import facade.FacadeUtenti;

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

	public static String modificaProfilo(String nome, String cognome, Date dataNascita, String biografia, String ImmagineProfilo) {

	    Integer result = FacadeUtenti.modificaProfilo(nome, cognome, dataNascita, biografia, ImmagineProfilo);

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

	public static String modificaRaccolta(String titolo, String descrizione, int id_raccolta) {
		Integer result = FacadeUtenti.modificaRaccolta(titolo, descrizione, id_raccolta);

	    if(result == -1) {
			return "Errore durante la modifica della Raccolta.";
		} else {
			return "Raccolta aggiornata con successo!";
		}
		
	}

	public static String eliminaRaccolta(int id_raccolta) {
		Integer result = FacadeUtenti.eliminaRaccolta(id_raccolta);

	    if(result == -1) {
			return "Errore durante l'eliminazione della Raccolta.";
		} else {
			return "Raccolta eliminata con successo!";
		}
		
	}

	public static PoesiaCompletaDTO visualizzaPoesia(int id_poesia,String autore) {
		PoesiaCompletaDTO poesia = FacadePoesie.visualizzaPoesia(id_poesia,autore);
		return poesia;

	}

	public static ArrayList<PoesiaDTO> ricercaPoesie(String termineRicerca, String filtro) {
	    return EntityPoetUp.ricercaPoesie(termineRicerca, filtro);
	}

}
