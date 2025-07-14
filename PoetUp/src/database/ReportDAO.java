package database;

import java.sql.Date;
import java.sql.SQLException;

public class ReportDAO {
	private String leadPoesie;
	private String leadAutori;
	private String leadTag;
	private Date data;
	private Date datainizio;
	private Date datafine;
	private int nPoesiePubblicate;
	private int id;
	private int autore;
	 
	
	
	public ReportDAO() {
		
	}
	
	public int scrivisuDB() {
	
			int ret = 0;

			String query = "INSERT INTO Report(data, numPoesiePubblicate, leadUtenti, leadTag, leadPoesie, autore, datainizio, datafine) VALUES ('" 
				    + this.data + "', " 
				    + this.nPoesiePubblicate + ", '" 
				    + this.leadAutori + "', '" 
				    + this.leadTag + "', '" 
				    +  this.leadPoesie.replace("'", "''") + "', '"  //i titolo di poesie accettano apostrofi
				    + this.autore + "', '" 
				    + this.datainizio + "', '" 
				    + this.datafine + "')";			System.out.println(query);
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

	public Date getDatafine() {
		return datafine;
	}

	public void setDatafine(Date datafine) {
		this.datafine = datafine;
	}

	public Date getDatainizio() {
		return datainizio;
	}

	public void setDatainizio(Date datainizio) {
		this.datainizio = datainizio;
	}
	
	
	
}
