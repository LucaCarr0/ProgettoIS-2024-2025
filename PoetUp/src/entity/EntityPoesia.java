package entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.ApprezzamentoDAO;
import database.CommentoDAO;
import database.PoesiaDAO;
import database.RaccoltaDAO;
import database.UtenteDAO;
import dto.CommentoDTO;
import dto.PoesiaCompletaDTO;
import email.EmailConfig;
import email.EmailMessage;
import email.EmailService;
import jakarta.mail.MessagingException;
import session.SessioneUtente;

public class EntityPoesia implements Comparable<EntityPoesia>{
	private String titolo,testo,tag;
	private boolean visibilita;
	private String autore, raccolta;
	private int id;
	private Date datapubblicazione;
	private int contatoreLike;
	private ArrayList<EntityCommento> commenti;
	private ArrayList<EntityApprezzamento> apprezzamenti;

	public EntityPoesia() {

	}

	public int salvaSuDB(int id_raccolta) {

		PoesiaDAO poesiaDAO= new PoesiaDAO();

		poesiaDAO.setTitolo(this.titolo);
		poesiaDAO.setAutore(SessioneUtente.getIdUtente());
		poesiaDAO.setContatoreLike(this.contatoreLike);
		poesiaDAO.setDatapubblicazione(datapubblicazione);
		poesiaDAO.setRaccolta(id_raccolta);
		poesiaDAO.setTag(tag);
		poesiaDAO.setTesto(testo);
		poesiaDAO.setVisibilita(visibilita);
		int i = poesiaDAO.ScriviSuDB();

		return i;
	}


	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public boolean isVisibilita() {
		return visibilita;
	}
	public void setVisibilita(boolean visibilita) {
		this.visibilita = visibilita;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getDatapubblicazione() {
		return datapubblicazione;
	}

	public void setDatapubblicazione(Date datapubblicazione) {
		this.datapubblicazione = datapubblicazione;
	}

	public int getContatoreLike() {
		return contatoreLike;
	}

	public void setContatoreLike(int contatoreLike) {
		this.contatoreLike = contatoreLike;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getRaccolta() {
		return raccolta;
	}

	public void setRaccolta(String raccolta) {
		this.raccolta = raccolta;
	}
	
	@Override
	public int compareTo(EntityPoesia o) {
	    if (this.datapubblicazione == null && o.datapubblicazione == null) {
	        return 0;
	    } else if (this.datapubblicazione == null) {
	        return -1;
	    } else if (o.datapubblicazione == null) {
	        return 1;
	    } else {
	    	//uso il compareTo di Date
	        return this.datapubblicazione.compareTo(o.getDatapubblicazione());
	    }
	}

	public PoesiaCompletaDTO visualizzaPoesia(int id_poesia, String autore) {
	    System.out.println(">>> Inizio visualizzaPoesia per ID: " + id_poesia + " | Autore: " + autore);
	    System.out.println("prima set" + this.id);
	    this.setId(id_poesia);
	    this.setAutore(autore);
	    System.out.println("dopo set " + this.id);
	    this.commenti = new ArrayList<>();
	    CommentoDAO commento = new CommentoDAO();
	    commento.setId_poesia(id_poesia);

	    System.out.println(">>> Caricamento commenti da DB...");
	    ArrayList<CommentoDAO> lista_commentiDAO = commento.caricadaDB();
	    System.out.println(">>> Numero commenti trovati: " + lista_commentiDAO.size());

	    costruisciUltimiCommenti(lista_commentiDAO);

	    PoesiaDAO poesiaDAO = new PoesiaDAO();
	    poesiaDAO.setId(this.id);
	    poesiaDAO.caricadaDB();

	    this.contatoreLike = poesiaDAO.getContatoreLike();
	    this.titolo = poesiaDAO.getTitolo();
	    this.testo = poesiaDAO.getTesto();
	    this.tag = poesiaDAO.getTag();
	    this.visibilita = poesiaDAO.isVisibilita();

	    System.out.println(">>> Dettagli poesia caricati:");
	    System.out.println("Titolo: " + titolo);
	    System.out.println("Testo: " + testo);
	    System.out.println("Tag: " + tag);
	    System.out.println("Contatore Like: " + contatoreLike);
	    System.out.println("Visibilità: " + (visibilita ? "pubblica" : "privata"));

	    ArrayList<CommentoDTO> ultimiCommenti = new ArrayList<>();
	    for (int i = 0; i < 3 && i < this.commenti.size(); i++) {
	        EntityUtente utente_temp = new EntityUtente();
	        utente_temp.setId(this.commenti.get(i).getId_autore());

	        CommentoDTO comm_temp = new CommentoDTO();
	        String nickname = utente_temp.getNickdaDB();

	        comm_temp.setAutore(nickname);
	        comm_temp.setData(this.commenti.get(i).getDataPubblicazione().toString());
	        comm_temp.setTesto(this.commenti.get(i).getTesto());

	        System.out.println(">>> Commento " + (i + 1) + ": " + nickname + " - " + comm_temp.getTesto());

	        ultimiCommenti.add(comm_temp);
	    }

	    String stato = visibilita ? "pubblica" : "privata";
	    System.out.println("prima di esiste app" + this.id);
	    boolean alreadyLiked = esisteApprezzamentoUtente(SessioneUtente.getIdUtente(), this.id);
	    System.out.println(">>> L'utente ha già apprezzato questa poesia? " + alreadyLiked);

	    PoesiaCompletaDTO poesia = new PoesiaCompletaDTO(
	            id, titolo, autore, testo, tag, contatoreLike, stato,
	            ultimiCommenti, alreadyLiked
	    );

	    System.out.println(">>> Completato caricamento poesia.");

	    return poesia;
	}


	private void costruisciUltimiCommenti(ArrayList<CommentoDAO> lista_commentiDAO) {
		for (CommentoDAO commento_i : lista_commentiDAO) {

			EntityCommento commento_temp=new EntityCommento();
			commento_temp.setTesto(commento_i.getTesto());
			commento_temp.setDataPubblicazione(commento_i.getDataPubblicazione());
			commento_temp.setId_autore(commento_i.getId_autore());
			commento_temp.setId_poesia(commento_i.getId_poesia());
			this.commenti.add(commento_temp);
		}
		this.commenti.sort(Collections.reverseOrder());


	}

	private boolean esisteApprezzamentoUtente(int id_utente,int id_poesia) {

		this.apprezzamenti = new ArrayList<>();
		boolean trovato=false;
		ApprezzamentoDAO like=new ApprezzamentoDAO();
		like.setId_poesia(id_poesia);
		ArrayList<ApprezzamentoDAO>apprezzamentiPoesiaDTO= like.caricaLikePoesia();

		for(ApprezzamentoDAO a : apprezzamentiPoesiaDTO) {
			EntityApprezzamento like_temp=new EntityApprezzamento();
			like_temp.setId_autore(a.getId_utente());
			like_temp.setId_poesia(a.getId_poesia());
			apprezzamenti.add(like_temp);
		}
		for(EntityApprezzamento a : apprezzamenti) {
			if(a.getId_autore()==(id_utente) && a.getId_poesia()==(id_poesia)) {
				trovato=true;
			}

		}
		return trovato;

	}


	public boolean like(boolean liked, int idPoesia) {
		boolean like_stato = false;
		int idUtente = SessioneUtente.getIdUtente();
		this.setId(idPoesia);
		
		
		EntityApprezzamento Like = new EntityApprezzamento();
		
		if (liked==false) {
			//aggiunge al DB
			System.out.println(liked);
			Like.setId_autore(idUtente);
			Like.setId_poesia(idPoesia);
			int res_query1 = Like.salvaSuDB(this.getId());
			
			if (res_query1 != -1) {
				this.aggiungiApprezzamento(Like, this.getId());
				like_stato = true;
			} 
			
		} else {
			//rimuove da DB
			System.out.println(liked);
			Like.setId_autore(idUtente);
			Like.setId_poesia(idPoesia);
			int res_query2 = Like.eliminaDaDB();
			if (res_query2 != -1) {
				this.rimuoviApprezzamento(Like, this.getId());
				like_stato = false;
			} 
			
		} 
		
		return like_stato;
	}
	
		
	private int rimuoviApprezzamento(EntityApprezzamento like, int idPoesia2) {
		PoesiaDAO poesiaDao = new PoesiaDAO();
		poesiaDao.setId(idPoesia2);
		int ret = poesiaDao.aggiornaSuDB_rimozione();
		return ret;
	}

	private int aggiungiApprezzamento(EntityApprezzamento apprezzamento, int idPoesia) {
		PoesiaDAO poesiaDao = new PoesiaDAO();
		poesiaDao.setId(idPoesia);
		int ret = poesiaDao.aggiornaSuDB_aggiunta();
		return ret;
	}

	public int commenta(String testo, int idPoesia) {
		
		id=idPoesia;
		EntityCommento commento= new EntityCommento();
		UtenteDAO u_temp = new UtenteDAO();
		EntityUtente uEntity_temp = new EntityUtente();
		PoesiaDAO p_temp = new PoesiaDAO();

		
		Date dataOdierna = Date.valueOf(LocalDate.now());
		
		commento.setDataPubblicazione(dataOdierna);
		commento.setId_autore(SessioneUtente.getIdUtente());
		commento.setId_poesia(id);
		commento.setTesto(testo);
		int id_comm=commento.salvasuDB();
		
		//Recupero nick autore commento
		uEntity_temp.setId(SessioneUtente.getIdUtente());
		String nickAutore = uEntity_temp.getNickdaDB();
		
		// Recupero autore poesia
		p_temp.setId(idPoesia);
		p_temp.caricadaDB();
		int autorePoesia = p_temp.getAutore();
		
		// Recupero email autore poesia
		u_temp.setId(autorePoesia);
		String rec_email = u_temp.ottieniEmail();
		//System.out.println("Email del ricevente: "+ rec_email); 
		
		EmailConfig config= new EmailConfig("poetup.noreply@gmail.com","lgdwkmxhcskgxeqh");

		String body= "Hai ricevuto un commento da " + nickAutore + ".\n" 
				+ "'" + testo + "'" 
				+ "\nA presto \n"
				+ "Il team di PoetUp";
		
		//System.out.println("Corpo della mail: " + body);

		EmailMessage mess=new EmailMessage(List.of(rec_email),"Nuovo commento ricevuto!",body);
		EmailService service= new EmailService(config);
		try {
			service.sendEmail(mess);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		
		return id_comm;
		
	}

	public Integer eliminaPoesia(int idPoesia) {
		this.setId(idPoesia);
		int res = this.eliminaDaDB();
		return res;
	}
	
	public int eliminaDaDB() {
		PoesiaDAO poesiaDao = new PoesiaDAO();
		poesiaDao.setId(this.id);
		int res = poesiaDao.deletePoesia();
		return res;
	}

	public Integer spostaPoesia(String titolo_raccolta, int idPoesia) {
		
		RaccoltaDAO raccoltaDao = new RaccoltaDAO();
		raccoltaDao.setTitolo(titolo_raccolta);
		int idRaccolta_dest = raccoltaDao.getIdRaccolta();
		System.out.println(idRaccolta_dest + "idPoesia: " + this.getId());
		
		PoesiaDAO poesiaDao = new PoesiaDAO();
		poesiaDao.setId(idPoesia);
		poesiaDao.setRaccolta(idRaccolta_dest);
		
		int res = poesiaDao.aggiornaSuDB_raccolta();
		
		return res;
	}

	



	

}
