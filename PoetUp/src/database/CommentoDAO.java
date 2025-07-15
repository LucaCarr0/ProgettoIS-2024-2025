package database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentoDAO {
	private String testo;
	private int id_poesia;
	private int id_autore;
	private Date dataPubblicazione;
	private int id;

	public CommentoDAO() {

	}


	public ArrayList<CommentoDAO> caricadaDB() {

		ArrayList<CommentoDAO> lista_commenti=new ArrayList<>();
		String query = "SELECT * FROM Commenti WHERE poesia = "+this.id_poesia+";";

		try {

			ResultSet rs = DBConnectionManager.selectQuery(query);

				while(rs.next()) { 

				CommentoDAO commento_temp=new CommentoDAO();

				 commento_temp.setId(rs.getInt("id"));
		         commento_temp.setTesto(rs.getString("testo"));
		         commento_temp.setDataPubblicazione(rs.getDate("data"));
		         commento_temp.setId_autore(rs.getInt("autore"));
		         commento_temp.setId_poesia(rs.getInt("poesia"));

		         lista_commenti.add(commento_temp);

			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return lista_commenti;
	}


	public int ScriviSuDB() {
	    int ret = 0;

	    String testoEsc = this.testo.replace("'", "''");
	    String query = "INSERT INTO Commenti(autore, poesia, testo, data) VALUES ("
	        + this.id_autore + ", "
	        + this.id_poesia + ", '"
	        + testoEsc + "', '"
	        + this.dataPubblicazione + "')";	    
	    try {
	        ret = DBConnectionManager.insertQueryReturnGeneratedKey(query);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        ret = -1; 
	    }

	    return ret;
	}


	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public int getId_poesia() {
		return id_poesia;
	}
	public void setId_poesia(int id_poesia) {
		this.id_poesia = id_poesia;
	}
	public int getId_autore() {
		return id_autore;
	}
	public void setId_autore(int id_autore) {
		this.id_autore = id_autore;
	}
	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


}
