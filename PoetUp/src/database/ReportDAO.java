package database;

import java.sql.Date;
import java.sql.SQLException;

public class ReportDAO {
	private String leadPoesie;
	private String leadAutori;
	private String leadTag;
	private Date data;
	private int nPoesiePubblicate;
	private int id;
	private int autore;
	
	
	
	public ReportDAO() {
		
	}
	
	public int scrivisuDB() {
	
			int ret = 0;

			String query = "INSERT INTO Report(data, numPoesiePubblicate, leadUtenti, leadTag, leadPoesie, autore) VALUES ('" + this.data + "', '" + this.nPoesiePubblicate + "', '" + this.leadAutori + "', '" + this.leadTag+ "', '" + this.leadPoesie+ "', '" + this.autore+"')";
			System.out.println(query);
			try {

				ret = DBConnectionManager.insertQueryReturnGeneratedKey(query);


			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				ret = -1; //per segnalare l'errore di scrittura
			}

			return ret;
		
	}
	
	public String getLeadPoesie() {
		return leadPoesie;
	}
	public void setLeadPoesie(String leadPoesie) {
		this.leadPoesie = leadPoesie;
	}
	public String getLeadAutori() {
		return leadAutori;
	}
	public void setLeadAutori(String leadAutori) {
		this.leadAutori = leadAutori;
	}
	public String getLeadTag() {
		return leadTag;
	}
	public void setLeadTag(String leadTag) {
		this.leadTag = leadTag;
	}
	public int getnPoesiePubblicate() {
		return nPoesiePubblicate;
	}
	public void setnPoesiePubblicate(int nPoesiePubblicate) {
		this.nPoesiePubblicate = nPoesiePubblicate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAutore() {
		return autore;
	}
	public void setAutore(int autore) {
		this.autore = autore;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	
	
}
