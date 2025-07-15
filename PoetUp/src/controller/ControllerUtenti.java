package controller;

import java.sql.Date;
import java.util.ArrayList;

import dto.PoesiaDTO;
import dto.ProfiloPersonaleDTO;
import dto.RaccoltaDTO;
import dto.StatisticheDTO;
import facade.FacadePoesie;
import facade.FacadeUtenti;

public class ControllerUtenti {
	
	/**
     * Crea una nuova raccolta personale.
     *
     * @param titolo       Il titolo della raccolta.
     * @param descrizione  La descrizione della raccolta.
     * @return Messaggio che indica il successo o il fallimento della creazione.
     */
	public static String addRaccolta(String titolo, String descrizione) {

		Integer result = FacadeUtenti.addRaccolta(titolo,descrizione);
		if(result == -1) {
			return "Creazione fallita";
		} else {
			return "Raccolta Creata";
		}
	}

	/**
     * Pubblica una nuova poesia per l'utente autenticato.
     *
     * @param titolo      Il titolo della poesia.
     * @param testo       Il contenuto della poesia.
     * @param tag         tag associati.
     * @param raccolta    La raccolta in cui inserire la poesia.
     * @param visibilita  La visibilità della poesia (pubblica o privata).
     * @return Messaggio che indica il successo o il fallimento della pubblicazione.
     */
	public static String pubblicazionePoesia(String titolo, String testo, String tag, String raccolta,
			boolean visibilita) {
		Integer result = FacadeUtenti.pubblicazionePoesia(titolo,testo,tag,raccolta,visibilita);
		if(result == -1) {
			return "Creazione fallita";
		} else {
			return "Poesia Pubblicata!";
		}
	}

	/**
     * Sposta una poesia in un'altra raccolta.
     *
     * @param titolo_raccolta Il titolo della raccolta di destinazione.
     * @param idPoesia        L'ID della poesia da spostare.
     * @return Messaggio che indica il successo o il fallimento dell'operazione.
     */

	public static String spostaPoesia(String titolo_raccolta, int idPoesia) {
		Integer result = FacadePoesie.spostaPoesia(titolo_raccolta, idPoesia);
		
		if(result == -1) {
			return "Errore durante lo spostamento della Poesia.";
		} else {
			return "Poesia spostata con successo!";
		}
	}

	/**
     * Modifica il profilo dell'utente autenticato.
     *
     * @param nome             Il nuovo nome.
     * @param cognome          Il nuovo cognome.
     * @param dataNascita      La nuova data di nascita.
     * @param biografia        La nuova biografia.
     * @param immagineProfilo  il percorso della nuova immagine profilo.
     * @return Messaggio che indica l'esito dell'operazione.
     */
	
	public static String modificaProfilo(String nome, String cognome, Date dataNascita, String biografia,String immagineProfilo) {

	    Integer result = FacadeUtenti.modificaProfilo(nome, cognome, dataNascita, biografia,immagineProfilo);

	    if(result == -1) {
			return "Errore durante la modifica del profilo.";
		} else {
			return "Profilo aggiornato con successo!";
		}

	}

	/**
     * Recupera il profilo dell'utente attualmente autenticato.
     *
     * @return Un oggetto {@link ProfiloPersonaleDTO} con le informazioni personali.
     */
	public static ProfiloPersonaleDTO getProfiloUtente() {
		ProfiloPersonaleDTO profilo = FacadeUtenti.getProfiloUtente();
		return profilo;
	}


	/**
     * Recupera tutte le raccolte create dall'utente autenticato.
     *
     * @return Una lista di oggetti {@link RaccoltaDTO}.
     */
	public static ArrayList<RaccoltaDTO> getRaccolteByUtente() {
		ArrayList<RaccoltaDTO> raccolte = FacadeUtenti.getRaccolteByUtente();
		return raccolte;
	}

	
	/**
     * Modifica una raccolta esistente.
     *
     * @param titolo        Il nuovo titolo della raccolta.
     * @param descrizione   La nuova descrizione.
     * @param id_raccolta   L'ID della raccolta da modificare.
     * @return Messaggio sull'esito dell'operazione.
     */
	public static String modificaRaccolta(String titolo, String descrizione, int id_raccolta) {
		Integer result = FacadeUtenti.modificaRaccolta(titolo, descrizione, id_raccolta);

	    if(result == -1) {
			return "Errore durante la modifica della Raccolta.";
		} else {
			return "Raccolta aggiornata con successo!";
		}

	}


	/**
     * Elimina una raccolta dell'utente.
     *
     * @param id_raccolta L'ID della raccolta da eliminare.
     * @return Messaggio che indica l'esito dell'eliminazione.
     */
	public static String eliminaRaccolta(int id_raccolta) {
		Integer result = FacadeUtenti.eliminaRaccolta(id_raccolta);

	    if(result == -1) {
			return "Errore durante l'eliminazione della Raccolta.";
		} else {
			return "Raccolta eliminata con successo!";
		}

	}
		
	/**
     * Restituisce tutte le poesie appartenenti a una specifica raccolta.
     *
     * @param raccoltaId L'ID della raccolta.
     * @return Lista di {@link PoesiaDTO} contenenti le poesie della raccolta.
     */
	public static ArrayList<PoesiaDTO> getPoesieByRaccolta(int raccoltaId) {
		ArrayList<PoesiaDTO> poesie = FacadePoesie.getPoesieByRaccolta(raccoltaId);
		return poesie;
	}
	

	/**
     * Recupera le statistiche personali dell'utente autenticato.
     *
     * @return Oggetto {@link StatisticheDTO} contenente dati statistici (es. poesia più apprezzata, like tot, commenti tot).
     */
	public static StatisticheDTO getStatistiche() {
	    StatisticheDTO statistiche = FacadeUtenti.getStatistiche();
	    return statistiche;
	}

	
	/**
     * Genera un report di attività tra due date specificate.
     *
     * @param dataInizio Data di inizio intervallo.
     * @param dataFine   Data di fine intervallo.
     * @return Una stringa contenente il report generato.
     */
	public static String generaReport(Date dataInizio, Date dataFine) {
		String report=FacadeUtenti.generaReport(dataInizio,dataFine);
		return report;
	}
	
}
