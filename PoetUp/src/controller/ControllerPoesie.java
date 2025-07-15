package controller;

import dto.PoesiaCompletaDTO;
import facade.FacadePoesie;

public class ControllerPoesie {

	
	
	public static boolean commenta(int idPoesia, String testo) {
		if (FacadePoesie.commenta(idPoesia,testo)==-1) return false;
		else return true;
	}
	
	public static boolean like(boolean liked, int idPoesia) {
		boolean like = FacadePoesie.like(liked, idPoesia);
		System.out.println(idPoesia);
		return like;
	}
	
	public static PoesiaCompletaDTO visualizzaPoesia(int id_poesia,String autore) {
		PoesiaCompletaDTO poesia = FacadePoesie.visualizzaPoesia(id_poesia,autore);
		return poesia;

	}
	
	public static String eliminaPoesia(int idPoesia)  {
		Integer result = FacadePoesie.eliminaPoesia(idPoesia);
		
		if(result == -1) {
			return "Errore durante l'eliminazione della Poesia.";
		} else {
			return "Poesia eliminata con successo!";
		}
	}

}
