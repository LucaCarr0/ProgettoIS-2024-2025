package database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PoesiaDAO {
	private String titolo,testo,tag;
	private boolean visibilita;
	private int id;
	private Date datapubblicazione;
	private int contatoreLike;
	private int autore;
	private int raccolta;


	
	public void caricadaDB() {
				String query = "SELECT * FROM Poesie WHERE id = "+id+";";
				
				try {

					ResultSet rs = DBConnectionManager.selectQuery(query);

						while(rs.next()) { //finche ho un risultato

						

						setTitolo(rs.getString("titolo"));
						setAutore(rs.getInt("autore"));
						setContatoreLike(rs.getInt("contatoreLike"));
						setDatapubblicazione(rs.getDate("dataPubblicazione"));
						setRaccolta(rs.getInt("raccolta"));
						setTag(rs.getString("tag"));
						setTesto(rs.getString("body"));
						setVisibilita(rs.getBoolean("visibilita"));

					}
				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();
				}
		}
	
	
	public int ScriviSuDB() {

		int ret = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String dataFormattata = sdf.format(this.datapubblicazione);

	    // Costruzione query SQL
	    String query = "INSERT INTO Poesie (titolo, body, tag, visibilita, dataPubblicazione, contatoreLike, autore, raccolta) VALUES ('"
	                 + this.titolo + "', '"
	                 + this.testo + "', '"
	                 + this.tag + "', "
	                 + (this.visibilita ? 1 : 0) + ", '" // è un tinyint su MYSQL
	                 + dataFormattata + "', "
	                 + this.contatoreLike + ", "
	                 + this.autore + ", "
	                 + this.raccolta + ")";
	    System.out.println(query);
		try {

			ret = DBConnectionManager.insertQueryReturnGeneratedKey(query);


		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ret = -1; //per segnalare l'errore di scrittura
		}

		return ret;
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
	public int getAutore() {
		return autore;
	}
	public void setAutore(int autore) {
		this.autore = autore;
	}
	public int getRaccolta() {
		return raccolta;
	}
	public void setRaccolta(int raccolta) {
		this.raccolta = raccolta;
	}




	public ArrayList<PoesiaDAO> getPoesieUtentedaDB() {
		//creo il la lista di appoggio
				ArrayList<PoesiaDAO> lista_poesie_db_utente = new ArrayList<>();
				String query = "SELECT * FROM Poesie WHERE autore = "+autore+";";

				try {

					ResultSet rs = DBConnectionManager.selectQuery(query);

						while(rs.next()) { //finche ho un risultato

						PoesiaDAO poesiaDAO = new PoesiaDAO();

						poesiaDAO.setTitolo(rs.getString("titolo"));
						poesiaDAO.setAutore(rs.getInt("autore"));
						poesiaDAO.setContatoreLike(rs.getInt("contatoreLike"));
						poesiaDAO.setDatapubblicazione(rs.getDate("dataPubblicazione"));
						poesiaDAO.setRaccolta(rs.getInt("raccolta"));
						poesiaDAO.setTag(rs.getString("tag"));
						poesiaDAO.setTesto(rs.getString("body"));
						poesiaDAO.setVisibilita(rs.getBoolean("visibilita"));
						lista_poesie_db_utente.add(poesiaDAO);
					}
				} catch (ClassNotFoundException | SQLException e) {

					e.printStackTrace();
				}
				return lista_poesie_db_utente;
		}




	public ArrayList<PoesiaDAO> caricaPoesiePubblichedaDB() {
		ArrayList<PoesiaDAO> lista_poesie_pubbliche = new ArrayList<>();
		String query = "SELECT * FROM Poesie WHERE visibilita = "+1+";";

		try {

			ResultSet rs = DBConnectionManager.selectQuery(query);

				while(rs.next()) { //finche ho un risultato

				PoesiaDAO poesiaDAO = new PoesiaDAO();

				poesiaDAO.setTitolo(rs.getString("titolo"));
				poesiaDAO.setAutore(rs.getInt("autore"));
				poesiaDAO.setContatoreLike(rs.getInt("contatoreLike"));
				poesiaDAO.setDatapubblicazione(rs.getDate("dataPubblicazione"));
				poesiaDAO.setRaccolta(rs.getInt("raccolta"));
				poesiaDAO.setTag(rs.getString("tag"));
				poesiaDAO.setTesto(rs.getString("body"));
				poesiaDAO.setVisibilita(rs.getBoolean("visibilita"));
				poesiaDAO.setId(rs.getInt("id"));
				lista_poesie_pubbliche.add(poesiaDAO);
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return lista_poesie_pubbliche;
	}






}
