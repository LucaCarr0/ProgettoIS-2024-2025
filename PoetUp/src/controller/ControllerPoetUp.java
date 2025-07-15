package controller;

import java.util.ArrayList;

import dto.PoesiaDTO;
import entity.EntityPoetUp;

public class ControllerPoetUp {

	 /**
     * Registra un nuovo utente nel sistema con i dati forniti.
     *
     * @param nickname Il nickname scelto dall'utente.
     * @param email L'indirizzo email dell'utente.
     * @param pwd La password scelta dall'utente.
     * @return Un messaggio che indica se la registrazione è andata a buon fine
     *         o se l'utente è già esistente.
     */
	public static String registrazione(String nickname,String email,String pwd) {



        
        Integer result = EntityPoetUp.registrazione(nickname,email,pwd);

        if (result==0) {
			return "Registrazione completata!";
		} else {
			return "Utente già esistente!";
		}
	}

	/**
     * Esegue l'autenticazione di un utente in base alle credenziali fornite.
     *
     * @param email L'email dell'utente.
     * @param pwd La password associata all'email.
     * @return Una stringa che indica se l'autenticazione è andata a buon fine
     *         oppure è fallita.
     */
	public static String autenticazione(String email, String pwd) {
		Integer result = EntityPoetUp.autenticazione(email,pwd);
		if(result == 0) {
			return "Utente autenticato";
		} else {
			return "Autenticazione fallita";
		}
	}


	/**
     * Recupera l'elenco delle poesie da visualizzare nel feed principale.
     *
     * @return Una lista di oggetti {@link PoesiaDTO} contenente le poesie più recenti.
     */
	public static ArrayList<PoesiaDTO> visualizzaFeed() {
		
		ArrayList<PoesiaDTO> feed = EntityPoetUp.visualizzaFeed();
		return feed;
	}

	/**
     * Esegue una ricerca tra le poesie in base a un termine e un filtro.
     *
     * @param termineRicerca La parola chiave da cercare (es. parte del titolo o autore).
     * @param filtro Il filtro da applicare alla ricerca (es. per data, popolarità, ecc.).
     * @return Una lista di {@link PoesiaDTO} che soddisfano i criteri di ricerca.
     */
	public static ArrayList<PoesiaDTO> ricercaPoesie(String termineRicerca, String filtro) {
	    return EntityPoetUp.ricercaPoesie(termineRicerca, filtro);
	}

	/**
     * Termina la sessione dell'utente attualmente loggato, modificando gli attributi di SessioneUtente.
     */
	public static void logout() {
		EntityPoetUp.logout();
	}
	

}
