package entity;

import java.util.ArrayList;
import java.util.Collections;

import database.PoesiaDAO;
import database.RaccoltaDAO;
import dto.PoesiaDTO;

public class EntityRaccolta {
	private int id;
	private String titolo;
	private String descrizione;
	private ArrayList<EntityPoesia> poesie;


	public EntityRaccolta() {

	}



	public int salvaSuDB(int id_utente) {

		RaccoltaDAO raccoltaDAO= new RaccoltaDAO();

		raccoltaDAO.setTitolo(this.titolo);
		raccoltaDAO.setDescrizione(this.descrizione);
		raccoltaDAO.setId_utente(id_utente);

		int i = raccoltaDAO.ScriviSuDB();

		return i;
	}

	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public void aggiornaRaccolta(String titolo, String descrizione, int id_raccolta) {
		this.setTitolo(titolo);
		this.setDescrizione(descrizione);
		this.setId(id_raccolta);
	}



	public int aggiornaSuDB(int idUtente) {
		RaccoltaDAO raccoltaDao = new RaccoltaDAO();
		raccoltaDao.setTitolo(this.titolo);
		raccoltaDao.setDescrizione(this.descrizione);
		raccoltaDao.setId_utente(idUtente);
		raccoltaDao.setId(this.id);
		int res = raccoltaDao.updateRaccolta();
		return res;
	}

	public ArrayList<PoesiaDTO> getPoesieByRaccolta(int id_raccolta) {
		this.poesie= new ArrayList<EntityPoesia>();
		PoesiaDAO poesiaDAO = new PoesiaDAO();
		 this.id=id_raccolta;
		 poesiaDAO.setRaccolta(id);
		 ArrayList<PoesiaDAO> lista_poesie=poesiaDAO.getPoesieRaccoltadaDB();
		 
		 for (PoesiaDAO poesia_d : lista_poesie) {
			 EntityPoesia poesia_temp = new EntityPoesia();
			 EntityUtente autore= new EntityUtente();

				//RECUPERO DA DB L'AUTORE DAL SUO ID
				int id_autore=poesia_d.getAutore();
				autore.setId(id_autore);
				String nick_autore=autore.getNickdaDB();

				poesia_temp.setTitolo(poesia_d.getTitolo());
				poesia_temp.setAutore(nick_autore);
				poesia_temp.setDatapubblicazione(poesia_d.getDatapubblicazione());
				poesia_temp.setContatoreLike(poesia_d.getContatoreLike());
				poesia_temp.setId(poesia_d.getId());
				this.poesie.add(poesia_temp);
		 }
		 	poesie.sort(Collections.reverseOrder());
		 	ArrayList<PoesiaDTO> risultato= new ArrayList<PoesiaDTO>();
		 	for (EntityPoesia e : poesie) {
		        PoesiaDTO dto = new PoesiaDTO();	
		        dto.setId(e.getId());
		        dto.setTitolo(e.getTitolo());
	            dto.setAutore(e.getAutore());
	            dto.setLike(e.getContatoreLike());
	            dto.setData(e.getDatapubblicazione().toString());
		        risultato.add(dto);
		    }
			return risultato;
		 
		 //PoesiaDTO(int id, String titolo, String autore, int like, String data, String tag, String testo) 
	}
	
	public int eliminaDaDB() {
		RaccoltaDAO raccoltaDao = new RaccoltaDAO();
		raccoltaDao.setId(this.id);
		int res = raccoltaDao.deleteRaccolta();
		return res;
	}



	public void eliminaRaccolta(int id_raccolta) {
		this.setId(id_raccolta);
	}

}
