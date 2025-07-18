package facade;

import java.sql.Date;
import java.util.ArrayList;

import dto.ProfiloPersonaleDTO;
import dto.RaccoltaDTO;
import dto.StatisticheDTO;
import entity.EntityAmministratore;
import entity.EntityRaccolta;
import entity.EntityUtente;

//Facade che fornisce un'interfaccia semplificata per le operazioni relative agli utenti e amministratori,
//nascondendo la complessità delle entità sottostanti (EntityUtente, EntityAmministratore).
//Include funzionalità per la gestione del profilo utente, pubblicazione poesie, raccolte e statistiche.

public class FacadeUtenti {

	public static Integer pubblicazionePoesia(String titolo, String testo, String tag, String raccolta,
			boolean visibilita) {
		EntityUtente utente = new EntityUtente();
		Integer result = utente.pubblicazionePoesia(titolo, testo, tag, raccolta, visibilita);
		return result;
	}

	public static Integer addRaccolta(String titolo, String descrizione) {
		EntityUtente utente = new EntityUtente();
		Integer result = utente.addRaccolta(titolo, descrizione);
		return result;
	}

	public static Integer modificaProfilo(String nome, String cognome, Date dataNascita, String biografia,
			String immaginePath) {
		EntityUtente utente = new EntityUtente();
		Integer result = utente.modificaProfilo(nome, cognome, dataNascita, biografia, immaginePath);
		return result;
	}

	public static ProfiloPersonaleDTO getProfiloUtente() {
		EntityUtente utente = new EntityUtente();
		ProfiloPersonaleDTO profilo = utente.getProfiloUtente();
		return profilo;
	}

	public static ArrayList<RaccoltaDTO> getRaccolteByUtente() {
		EntityUtente utente = new EntityUtente();
		ArrayList<RaccoltaDTO> raccolte = utente.getRaccolteByUtente();
		return raccolte;
	}

	public static Integer modificaRaccolta(String titolo, String descrizione, int id_raccolta) {
		EntityRaccolta raccolta = new EntityRaccolta();
		Integer result = raccolta.modificaRaccolta(titolo, descrizione, id_raccolta);
		return result;
	}

	public static Integer eliminaRaccolta(int id_raccolta) {
		EntityRaccolta raccolta = new EntityRaccolta();
		Integer result = raccolta.eliminaRaccolta(id_raccolta);
		return result;
	}

	public static StatisticheDTO getStatistiche() {
		EntityUtente utente = new EntityUtente();
		StatisticheDTO statistiche = utente.getStatistiche();
		return statistiche;
	}

	public static String generaReport(Date dataInizio, Date dataFine) {
		EntityAmministratore admin = new EntityAmministratore();
		String report = admin.generaReport(dataInizio, dataFine);
		return report;
	}
}
