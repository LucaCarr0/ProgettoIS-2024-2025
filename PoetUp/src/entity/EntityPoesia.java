package entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;

import database.ApprezzamentoDAO;
import database.CommentoDAO;
import database.PoesiaDAO;
import dto.CommentoDTO;
import dto.PoesiaCompletaDTO;
import session.SessioneUtente;

public class EntityPoesia implements Comparable<EntityPoesia>{
	private String titolo,testo,tag;
	private boolean visibilita;
	private String autore;
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

	    this.setId(id_poesia);
	    this.setAutore(autore);

	    this.commenti = new ArrayList<EntityCommento>();
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

	    ArrayList<CommentoDTO> ultimiCommenti = new ArrayList<CommentoDTO>();
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

	    boolean alreadyLiked = esisteApprezzamentoUtente(SessioneUtente.getIdUtente(), this.id);
	    System.out.println(">>> L'utente ha già apprezzato questa poesia? " + alreadyLiked);

	    PoesiaCompletaDTO poesia = new PoesiaCompletaDTO(
	            titolo, autore, testo, tag, contatoreLike, stato,
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
		
		this.apprezzamenti = new ArrayList<EntityApprezzamento>();
		boolean trovato=false;
		ApprezzamentoDAO like=new ApprezzamentoDAO();
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








}
