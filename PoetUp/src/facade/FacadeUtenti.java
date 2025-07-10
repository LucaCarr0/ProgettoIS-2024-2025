package facade;

import entity.EntityUtente;
import java.sql.Date;

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
}
