package entity;

import java.sql.SQLException;
import java.util.ArrayList;

import database.UtenteDAO;

public class EntityPoetUp {
	
	private static ArrayList<EntityUtente> elencoUtenti;


	
	public static Integer registrazione(String nickname,String email,String pwd) {
		caricaListaDaDB();
		//da modificare serve considerare amministratore e profilo
		EntityUtente utente_da_registrare=new EntityUtente();
		utente_da_registrare.setEmail(email);
		utente_da_registrare.setPwd(pwd);
		
		if(esisteUtente(utente_da_registrare)) {
			return -1;
		} else {
			int id_utente=utente_da_registrare.scriviSuDB();
			if (id_utente==-1)
				return -1;
			else {
				
				//SE REGISTRO CORRETTAMENTE L'UTENTE NE INIZIALIZZO IL PROFILO CON IL NICKNAME
				EntityProfiloPersonale profilo= new EntityProfiloPersonale();
				profilo.setNickname(nickname);
				profilo.
		}
		
	}
	
	public static boolean esisteUtente(EntityUtente utente_da_registrare) {
        String email=utente_da_registrare.getEmail();
        for(EntityUtente u : elencoUtenti) {
            if(u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
	
	public static void caricaListaDaDB() { //metodo per caricare la lista degli studenti dal DB
		
		elencoUtenti = new ArrayList<>();
		UtenteDAO utente = new UtenteDAO();
		ArrayList<UtenteDAO> lista_db_utenti = utente.getListaUtenti();
		
		for(int i = 0; i<lista_db_utenti.size();i++) {
			EntityUtente utente_temp = new EntityUtente();
			utente_temp.setEmail(lista_db_utenti.get(i).getEmail());
			utente_temp.setPwd(lista_db_utenti.get(i).getPwd());
			
			System.out.println(utente_temp);
			
			elencoUtenti.add(utente_temp);
			
		}
	}
	
	
		
		
		
		
	

	
	
}
