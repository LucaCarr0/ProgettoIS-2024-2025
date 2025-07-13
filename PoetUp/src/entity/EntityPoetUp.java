package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.PoesiaDAO;
import database.UtenteDAO;
import dto.PoesiaDTO;
import email.EmailConfig;
import email.EmailMessage;
import email.EmailService;
import jakarta.mail.MessagingException;
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
			if (id_utente==-1) {
				return -1;
			} else {

				//SE REGISTRO CORRETTAMENTE L'UTENTE NE INIZIALIZZO IL PROFILO CON IL NICKNAME
				utente_da_registrare.inizializzaProfilo(id_utente,nickname);
				EmailConfig config= new EmailConfig("poetup.noreply@gmail.com","lgdwkmxhcskgxeqh");

				String body= "Ciao "+ nickname+"! Ti sei registrato correttamente.\n"
						+ "Per modificare il tuo profilo e dirci qualcosa in più su di te ricorda di accedere alla sezione Profilo.\n"
						+ "Dai libero sfogo alla tua fantasia! \n"
						+ "A presto \n"
						+ "Il team di PoetUp";

				EmailMessage mess=new EmailMessage(List.of(email),"Registrazione PoetUp!",body);
				EmailService service= new EmailService(config);
				try {
					service.sendEmail(mess);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

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

	public static ArrayList<PoesiaDTO> visualizzaFeed() {
		// TODO Auto-generated method stub
		ArrayList<EntityPoesia> lista_poesie_pubbliche = new ArrayList<>();
		ArrayList<PoesiaDTO> feed=calcolaFeed(lista_poesie_pubbliche);
		return feed;
	}

	private static ArrayList<PoesiaDTO> calcolaFeed(ArrayList<EntityPoesia> lista_entity) {
		PoesiaDAO poesiaDAO = new PoesiaDAO();
		ArrayList<PoesiaDAO> lista_poesie_pubbliche = poesiaDAO.caricaPoesiePubblichedaDB();
		EntityUtente autore=new EntityUtente();
		for(int i = 0; i<lista_poesie_pubbliche.size();i++) {
			EntityPoesia poesia_temp = new EntityPoesia();

			//RECUPERO DA DB L'AUTORE DAL SUO ID
			int id_autore=lista_poesie_pubbliche.get(i).getAutore();
			autore.setId(id_autore);
			String nick_autore=autore.getNickdaDB();

			poesia_temp.setTitolo(lista_poesie_pubbliche.get(i).getTitolo());
			poesia_temp.setAutore(nick_autore);
			poesia_temp.setDatapubblicazione(lista_poesie_pubbliche.get(i).getDatapubblicazione());
			poesia_temp.setContatoreLike(lista_poesie_pubbliche.get(i).getContatoreLike());
			poesia_temp.setId(lista_poesie_pubbliche.get(i).getId());

			lista_entity.add(poesia_temp);

		}
		lista_entity.sort(Collections.reverseOrder());
		ArrayList<PoesiaDTO> feed=costruisciFeed(lista_entity);
		return feed;
	}

	private static ArrayList<PoesiaDTO> costruisciFeed(ArrayList<EntityPoesia> lista_entity) {
		ArrayList<PoesiaDTO> feed = new ArrayList<>();

		for (EntityPoesia e : lista_entity) {
	        PoesiaDTO dto = new PoesiaDTO();
	        dto.setId(e.getId());
	        dto.setTitolo(e.getTitolo());
            dto.setAutore(e.getAutore());
            dto.setLike(e.getContatoreLike());
            dto.setData(e.getDatapubblicazione().toString());
	        feed.add(dto);
	    }

	    return feed;
	}

	public static ArrayList<PoesiaDTO> ricercaPoesie(String termineRicerca, String filtro) {

	    ArrayList<PoesiaDAO> poesieDAO = filtraRisultati(termineRicerca, filtro);
	    ArrayList<PoesiaDTO> poesieDTO = new ArrayList<>();
	    EntityUtente temp = new EntityUtente();

	    for (PoesiaDAO dao : poesieDAO) {

	    	temp.setId(dao.getAutore());
	    	String nickAutore = temp.getNickdaDB();

	        PoesiaDTO dto = new PoesiaDTO();
	        	dto.setId(dao.getId());
	            dto.setTitolo(dao.getTitolo());
	            dto.setAutore(nickAutore);
	            dto.setLike(dao.getContatoreLike());
	            dto.setData(dao.getDatapubblicazione().toString());

	        poesieDTO.add(dto);
	    }

	    return poesieDTO;
	}

	public static ArrayList<PoesiaDAO> filtraRisultati(String termineRicerca, String filtro) {
		PoesiaDAO poesiaDAO = new PoesiaDAO();
		ArrayList<PoesiaDAO> tutteLePoesie = poesiaDAO.caricaPoesiePubblichedaDB();
	    ArrayList<PoesiaDAO> risultati = new ArrayList<>();

	    String ricerca = termineRicerca.toLowerCase();

	    for (PoesiaDAO poesia : tutteLePoesie) {
	        boolean match = false;

	        switch (filtro) {
	            case "Tag":
	                match = poesia.getTag() != null && poesia.getTag().toLowerCase().contains(ricerca);
	                break;
	            case "Titolo":
	                match = poesia.getTitolo() != null && poesia.getTitolo().toLowerCase().contains(ricerca);
	                break;
	            case "Testo":
	                match = poesia.getTesto() != null && poesia.getTesto().toLowerCase().contains(ricerca);
	                break;
	            default:
	                match = (poesia.getTitolo() != null && poesia.getTitolo().toLowerCase().contains(ricerca)) ||
	                        (poesia.getTag() != null && poesia.getTag().toLowerCase().contains(ricerca)) ||
	                        (poesia.getTesto() != null && poesia.getTesto().toLowerCase().contains(ricerca));
	                break;
	        }

	        if (match) {
	            risultati.add(poesia);
	        }
	    }

	    // Ordinamento per data di pubblicazione, dal più recente al meno recente
	    risultati.sort((p1, p2) -> p2.getDatapubblicazione().compareTo(p1.getDatapubblicazione()));

	    return risultati;
	}

	public static ArrayList<EntityUtente> getElencoUtenti() {
		return elencoUtenti;
	}

	public static void setElencoUtenti(ArrayList<EntityUtente> elencoUtenti) {
		EntityPoetUp.elencoUtenti = elencoUtenti;
	}









}
