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

		try {

			ResultSet rs = DBConnectionManager.selectQuery(query);

				while(rs.next()) { //finche ho un risultato

				ApprezzamentoDAO apprezzamento = new ApprezzamentoDAO();

				apprezzamento.setId_poesia(rs.getInt("poesia"));
				apprezzamento.setId_utente(rs.getInt("utente"));
				apprezzamento.setId(rs.getInt("id"));
				apprezzamentiPoesia.add(apprezzamento);
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return apprezzamentiPoesia;
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
