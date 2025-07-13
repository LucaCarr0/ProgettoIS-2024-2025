package entity;

import java.sql.Date;
import java.time.LocalDate;

import database.ReportDAO;
import session.SessioneUtente;

public class EntityReport {
	
	private String leadPoesie;
	private String leadAutori;
	private String leadTag;
	private int nPoesiePubblicate;
	
	
	
	
	public EntityReport() {
	}
	
	
	public int salvasuDB() {
		ReportDAO report=new ReportDAO();
		report.setAutore(SessioneUtente.getIdUtente());
		report.setData(Date.valueOf(LocalDate.now()));
		report.setLeadAutori(this.getLeadAutori());
		report.setLeadPoesie(this.getLeadPoesie());
		report.setLeadTag(this.getLeadTag());
		report.setnPoesiePubblicate(this.getnPoesiePubblicate());
		return report.scrivisuDB();
		
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
	
}
