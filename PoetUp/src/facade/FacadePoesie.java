package facade;

import java.util.ArrayList;

import dto.PoesiaCompletaDTO;
import dto.PoesiaDTO;
import entity.EntityPoesia;
import entity.EntityRaccolta;

//Facade che fornisce un'interfaccia semplificata per le operazioni sulle poesie,
//delegando alle entità sottostanti la logica di business.
//Include funzionalità per visualizzare, commentare, mettere like, spostare ed eliminare poesie,
//oltre a recuperare poesie associate a una raccolta.

public class FacadePoesie {

	public static PoesiaCompletaDTO visualizzaPoesia(int id_poesia, String autore) {
		EntityPoesia poesia = new EntityPoesia();
		PoesiaCompletaDTO poesiaDTO = poesia.visualizzaPoesia(id_poesia, autore);
		return poesiaDTO;
	}

	public static ArrayList<PoesiaDTO> getPoesieByRaccolta(int id_raccolta) {
		EntityRaccolta raccolta = new EntityRaccolta();
		ArrayList<PoesiaDTO> poesie = raccolta.getPoesieByRaccolta(id_raccolta);
		return poesie;
	}

	public static boolean like(boolean liked, int idPoesia) {
		EntityPoesia poesia = new EntityPoesia();
		boolean like = poesia.like(liked, idPoesia);
		return like;
	}

	public static int commenta(int idPoesia, String testo) {
		EntityPoesia poesia = new EntityPoesia();
		int ritorno = poesia.commenta(testo, idPoesia);
		return ritorno;
	}

	public static Integer eliminaPoesia(int idPoesia) {
		EntityPoesia poesia = new EntityPoesia();
		Integer result = poesia.eliminaPoesia(idPoesia);
		return result;
	}

	public static Integer spostaPoesia(String titolo_raccolta, int idPoesia) {
		EntityPoesia poesia = new EntityPoesia();
		Integer result = poesia.spostaPoesia(titolo_raccolta, idPoesia);
		return result;
	}

}
