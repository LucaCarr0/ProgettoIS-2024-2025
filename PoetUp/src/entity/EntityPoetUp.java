package entity;

import java.sql.SQLException;
import java.util.ArrayList;

import database.UtenteDAO;
import session.SessioneUtente;

public class EntityPoetUp {
	
	private static ArrayList<EntityUtente> elencoUtenti;


	
	public static Integer registrazione(String nickname,String email,String pwd) {
		caricaListaDaDB();
		EntityUtente utente_da_registrare=new EntityUtente();
		utente_da_registrare.setEmail(email);
		utente_da_registrare.setPwd(pwd);
		
		if(esisteUtente(utente_da_registrare)) {
			return -1;
		} else {
			int id_utente=utente_da_registrare.salvaSuDB();
			if (id_utente==-1)
				return -1;
			else {
				
				//SE REGISTRO CORRETTAMENTE L'UTENTE NE INIZIALIZZO IL PROFILO CON IL NICKNAME
				utente_da_registrare.inizializzaProfilo(id_utente,nickname);
				return 0;
		}
		}	
	}
	
	public static Integer autenticazione(String email,String pwd) {
		caricaListaDaDB();
		EntityUtente utente_da_autenticare=new EntityUtente();
		utente_da_autenticare.setEmail(email);
		utente_da_autenticare.setPwd(pwd);
		if (corrispondenzaUtente(utente_da_autenticare)) {
			return 0;
		}
		return -1;
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
	
	public static boolean corrispondenzaUtente(EntityUtente utente_da_autenticare) {
		String password = utente_da_autenticare.getPwd();
		String email=utente_da_autenticare.getEmail();
        for(EntityUtente u : elencoUtenti) {
            if(u.getEmail().equals(email) && u.getPwd().equals(password)) {
            	boolean amministratore = u.isAmministratore();
            	int id = u.getId();
            	SessioneUtente.login(id, amministratore);
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
			utente_temp.setAmministratore(lista_db_utenti.get(i).isAmministratore());
			utente_temp.setId(lista_db_utenti.get(i).getId());
			
			
			elencoUtenti.add(utente_temp);
			
		}
	}
	
	
		
		
		
		
	

	
	
}
