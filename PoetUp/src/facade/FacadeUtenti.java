package facade;

import entity.EntityUtente;
import java.sql.Date;
import java.util.ArrayList;

import dto.ProfiloPersonaleDTO;
import dto.RaccoltaDTO;

public class FacadeUtenti {


	public static Integer pubblicazionePoesia(String titolo, String testo, String tag, String raccolta,
			boolean visibilita) {
		EntityUtente utente= new EntityUtente();
		Integer result = utente.pubblicazionePoesia(titolo,testo,tag,raccolta,visibilita);
		return result;
	}

	public static Integer addRaccolta(String titolo, String descrizione) {
		EntityUtente utente= new EntityUtente();
		Integer result = utente.addRaccolta(titolo,descrizione);
		return result;
	}
	
	public static Integer modificaProfilo(String nome, String cognome, Date dataNascita, String biografia) {
		EntityUtente utente= new EntityUtente();
		Integer result = utente.modificaProfilo(nome, cognome, dataNascita, biografia);
        return result;
    }

	public static ProfiloPersonaleDTO getProfiloUtente() {
		EntityUtente utente= new EntityUtente();
		ProfiloPersonaleDTO profilo = utente.getProfiloUtente();
        return profilo;
	}

	public static ArrayList<RaccoltaDTO> getRaccolteByUtente() {
		EntityUtente utente= new EntityUtente();
		ArrayList<RaccoltaDTO> raccolte = utente.getRaccolteByUtente();
        return raccolte;
	}
}
