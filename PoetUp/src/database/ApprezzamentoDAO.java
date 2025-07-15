package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApprezzamentoDAO {
	private int id_utente;
	private int id;
	private int id_poesia;


	public ArrayList<ApprezzamentoDAO> caricaLikePoesia() {
		ArrayList<ApprezzamentoDAO> apprezzamentiPoesia = new ArrayList<>();
		String query = "SELECT * FROM Apprezzamenti WHERE poesia = "+id_poesia+";";
		System.out.println(query);
		try {

			ResultSet rs = DBConnectionManager.selectQuery(query);

				while(rs.next()) { 

				ApprezzamentoDAO apprezzamento = new ApprezzamentoDAO();

				apprezzamento.setId_poesia(rs.getInt("poesia"));
				apprezzamento.setId_utente(rs.getInt("autore"));
				apprezzamento.setId(rs.getInt("id"));
				apprezzamentiPoesia.add(apprezzamento);
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return apprezzamentiPoesia;
	}



	public int ScriviSuDB() {
	    int ret = 0;

	    String query = "INSERT INTO Apprezzamenti(autore, poesia) VALUES (" + this.id_utente + ", " + this.id_poesia + ")";
	    
	    try {
	        ret = DBConnectionManager.insertQueryReturnGeneratedKey(query);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        ret = -1; 
	    }

	    return ret;
	}


	public int eliminaDaDB() {
		int ret = 0;

	    String query = "DELETE FROM Apprezzamenti WHERE autore = " + this.getId_utente() + " AND poesia = " + this.getId_poesia();

	    System.out.println(query);

	    try {
	        ret = DBConnectionManager.UpdateQuery(query);
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        ret = -1; 
	    }

	    return ret;
	}





	public int getId_utente() {
		return id_utente;
	}


	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getId_poesia() {
		return id_poesia;
	}


	public void setId_poesia(int id_poesia) {
		this.id_poesia = id_poesia;
	}

}
