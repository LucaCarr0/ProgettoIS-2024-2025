package controller;

import java.util.ArrayList;

import dto.PoesiaDTO;
import entity.EntityPoetUp;

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
	
	public static ArrayList<PoesiaDTO> visualizzaFeed() {
		// TODO Auto-generated method stub
		ArrayList<PoesiaDTO> feed = EntityPoetUp.visualizzaFeed();
		return feed;
	}

	public static ArrayList<PoesiaDTO> ricercaPoesie(String termineRicerca, String filtro) {
	    return EntityPoetUp.ricercaPoesie(termineRicerca, filtro);
	}
	
	public static void logout() {
		EntityPoetUp.logout();
	}
	

}
