package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RaccoltaDAO {

	private String titolo;
	private String descrizione;
	private int id_utente;
	private int id;


	public RaccoltaDAO() {

	}

	public ArrayList<RaccoltaDAO> getRaccoltedaDB(){

		//creo il la lista di appoggio
		ArrayList<RaccoltaDAO> lista_raccolte_db = new ArrayList<>();
		String query = "SELECT * FROM Raccolte WHERE utente = "+id_utente+";";

		try {

			ResultSet rs = DBConnectionManager.selectQuery(query);

				while(rs.next()) { //finche ho un risultato

				RaccoltaDAO raccolta_DAO = new RaccoltaDAO();

				raccolta_DAO.setTitolo(rs.getString("titolo"));
				raccolta_DAO.setDescrizione(rs.getString("descrizione"));
				raccolta_DAO.setId_utente(rs.getInt("utente"));
				raccolta_DAO.setId(rs.getInt("id"));
				lista_raccolte_db.add(raccolta_DAO);
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return lista_raccolte_db;
	}

	public int ScriviSuDB() {

		int ret = 0;

		String query = "INSERT INTO Raccolte(titolo, descrizione, utente) VALUES ('" + this.titolo + "', '" + this.descrizione + "', '" + this.id_utente +"')";
		System.out.println(query);
		try {

			ret = DBConnectionManager.insertQueryReturnGeneratedKey(query);


		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			ret = -1; //per segnalare l'errore di scrittura
		}

		return ret;
	}

	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public int getId_utente() {
		return id_utente;
	}


	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}


	public String getTitolo() {
		return titolo;
	}


	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public int updateRaccolta() {
	    int ret = 0;

	    String query = "UPDATE Raccolte SET " +
	                   "titolo = '" + this.getTitolo() + "', " +
	                   "descrizione = '" + this.getDescrizione() + "' " +
	                   "WHERE id = " + this.getId();

	    System.out.println(query);
	    try {
	        ret = DBConnectionManager.UpdateQuery(query);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        ret = -1; // per segnalare l'errore di scrittura
	    }

	    return ret;
	}

	public int deleteRaccolta() {
	    int ret = 0;

	    String query = "DELETE FROM Raccolte WHERE id = " + this.getId();

	    System.out.println(query);
	    try {
	        ret = DBConnectionManager.deleteQuery(query);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        ret = -1; // errore durante l'esecuzione
	    }

	    return ret;
	}

	public int getIdRaccolta() {
	    int id = -1;

	    String query = "SELECT id FROM Raccolte WHERE titolo = '" + this.getTitolo() + "'";

	    System.out.println(query);

	    try {
	        ResultSet rs = DBConnectionManager.selectQuery(query);

	        if (rs.next()) {
	            id = rs.getInt("id");
	        }

	        rs.close();
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }

	    return id;
	}




}
