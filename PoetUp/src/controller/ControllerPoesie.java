package controller;

import dto.PoesiaCompletaDTO;
import facade.FacadePoesie;

public class ControllerPoesie {

	
	/**
 	* Aggiunge un commento a una poesia identificata dal suo ID.
 	*
 	* @param idPoesia L'identificativo univoco della poesia.
 	* @param testo Il contenuto del commento da aggiungere.
 	* @return true se il commento è stato inserito con successo, false altrimenti.
 	*/
	public static boolean commenta(int idPoesia, String testo) {
		if (FacadePoesie.commenta(idPoesia,testo)==-1) return false;
		else return true;
	}


	/**
 	* Registra o rimuove un like da parte dell'utente sulla poesia indicata.
 	*
 	* @param liked true se l'utente ha già messo like (e lo vuole togliere), false se lo sta aggiungendo ora.
 	* @param idPoesia L'identificativo della poesia su cui eseguire l'azione.
 	* @return true se l'operazione è andata a buon fine, false in caso contrario.
 	*/
	public static boolean like(boolean liked, int idPoesia) {
		boolean like = FacadePoesie.like(liked, idPoesia);
		//System.out.println(idPoesia);
		return like;
	}

	/**
 	* Recupera i dettagli completi di una poesia, inclusi titolo, testo, autore, data e commenti.
 	*
 	* @param id_poesia L'identificativo della poesia.
 	* @param autore Il nome dell'autore della poesia.
 	* @return Un oggetto {@link PoesiaCompletaDTO} contenente tutte le informazioni della poesia.
 	*/
	public static PoesiaCompletaDTO visualizzaPoesia(int id_poesia,String autore) {
		PoesiaCompletaDTO poesia = FacadePoesie.visualizzaPoesia(id_poesia,autore);
		return poesia;

	}
	
	/**
 	* Elimina una poesia dal sistema in base al suo ID.
	*
 	* @param idPoesia L'identificativo univoco della poesia da eliminare.
	* @return Una stringa che descrive l'esito dell'operazione: successo o errore.
	*/
	public static String eliminaPoesia(int idPoesia)  {
		Integer result = FacadePoesie.eliminaPoesia(idPoesia);
		
		if(result == -1) {
			return "Errore durante l'eliminazione della Poesia.";
		} else {
			return "Poesia eliminata con successo!";
		}
	}

}
