package entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


import database.PoesiaDAO;

public class EntityAmministratore extends EntityUtente{

	
	public String generaReport(Date dataInizio, Date dataFine) {
		EntityReport report=new EntityReport();
	    EntityPoetUp.setElencoUtenti(new ArrayList<EntityUtente>());
	    EntityPoetUp.caricaListaDaDB();
	    ArrayList<EntityUtente> utenti = EntityPoetUp.getElencoUtenti(); 
	    ArrayList<EntityPoesia> poesieDB = new ArrayList<>();

	    PoesiaDAO poesiaDAO = new PoesiaDAO();

	    // Recupero poesie da DB per ogni utente
	    for (EntityUtente utente : utenti) {
	        poesiaDAO.setAutore(utente.getId());
	        ArrayList<PoesiaDAO> tutteLePoesie = poesiaDAO.getPoesieUtentedaDB();
	        ArrayList<EntityPoesia> tutteLePoesieEntity = convertiPoesieDAO(tutteLePoesie);
	        poesieDB.addAll(tutteLePoesieEntity);
	    }
	    
	    ArrayList<String> autore_tag=calcoloTageAutore(poesieDB,dataInizio,dataFine);
	    report.setLeadAutori(autore_tag.get(0));
	    report.setLeadTag(autore_tag.get(1));
	    
	    // Trova poesie con più like e calcolo contatorePoesie
	    calcolaTopPoesie(poesieDB,dataInizio,dataFine,report);
	    StringBuilder report_string= new StringBuilder();
	    if(report.salvasuDB()==-1) {
	    	report_string.append(">>> REPORT <<<\n");
	    	report_string.append("ERRORE NEL SALVATAGGIO DEL REPORT SUL DB");
	    }
	    else {
	    
	    // Costruzione del report
	    report_string.append(">>> REPORT <<<\n");
	    report_string.append("Periodo: " + dataInizio + " -> " + dataFine + "\n\n");
	    report_string.append("Poesie pubblicate: " + report.getnPoesiePubblicate() +"\n");
	    report_string.append("Tag più utilizzato: #" + report.getLeadTag() + "\n");
	    report_string.append("Autore più prolifico: " + report.getLeadAutori() + "\n\n");
	    report_string.append("Top 3 poesie con più like:\n");
	    report_string.append(report.getLeadPoesie());
	    
	    
	    }

	    return report_string.toString();
	}


	           
	    
	private void calcolaTopPoesie(ArrayList<EntityPoesia> poesieDB, Date dataInizio, Date dataFine, EntityReport report) {
	    ArrayList<EntityPoesia> poesieFiltrate = new ArrayList<>();
	    int contPoesie = 0;

	    for (EntityPoesia poesia : poesieDB) {
	        Date dataPoesia = poesia.getDatapubblicazione();

	        // Filtro per intervallo di date
	        if (dataPoesia != null && dataPoesia.compareTo(dataInizio) >= 0 && dataPoesia.compareTo(dataFine) <= 0) {
	            contPoesie++;
	            poesieFiltrate.add(poesia);
	        }
	    }

	    report.setnPoesiePubblicate(contPoesie);
	    poesieFiltrate.sort((p1, p2) -> Integer.compare(p2.getContatoreLike(), p1.getContatoreLike()));
	    ArrayList<EntityPoesia> topPoesie = (ArrayList<EntityPoesia>) poesieFiltrate.stream().limit(3).collect(Collectors.toList());

	    StringBuilder leadBuilder = new StringBuilder();
	    int posizione = 1;
	    for (EntityPoesia poesia : topPoesie) {
	        leadBuilder.append(posizione++ + ". \"" + poesia.getTitolo() + "\" di " + poesia.getAutore() + " (" + poesia.getContatoreLike() + " like)\n");
	    }

	    report.setLeadPoesie(leadBuilder.toString());
	}

	private ArrayList<EntityPoesia> convertiPoesieDAO(ArrayList<PoesiaDAO> tutteLePoesie) {
		ArrayList<EntityPoesia> poesie=new ArrayList<EntityPoesia>();
		EntityUtente autore = new EntityUtente();
		for (PoesiaDAO poesiaD : tutteLePoesie) {
			EntityPoesia poesia_temp=new EntityPoesia();
			poesia_temp.setDatapubblicazione(poesiaD.getDatapubblicazione());
			autore.setId(poesiaD.getAutore());
			poesia_temp.setAutore(autore.getNickdaDB());
			poesia_temp.setTag(poesiaD.getTag());
			poesia_temp.setContatoreLike(poesiaD.getContatoreLike());
			poesia_temp.setTitolo(poesiaD.getTitolo());
			poesie.add(poesia_temp);
		}
		return poesie;
		
	}
	public ArrayList<String> calcoloTageAutore(ArrayList<EntityPoesia> poesieDB,Date dataInizio,Date dataFine) {
	// Dati per il report
    Map<String, Integer> tagCount = new HashMap<>();
    Map<String, Integer> autoreCount = new HashMap<>();

    ArrayList<EntityPoesia> poesieFiltrate = new ArrayList<>();

    for (EntityPoesia poesia : poesieDB) {
        Date dataPoesia = poesia.getDatapubblicazione();
        
        // Filtro per intervallo di date
        if (dataPoesia != null && dataPoesia.compareTo(dataInizio) >= 0 && dataPoesia.compareTo(dataFine) <= 0) {
            
            poesieFiltrate.add(poesia);

            // Conteggio tag
            String[] tags = poesia.getTag().split("#");
            for (String tag : tags) {
                if (!tag.isBlank()) {
                    tag = tag.trim().toLowerCase();
                    tagCount.put(tag, tagCount.getOrDefault(tag, 0) + 1);
                }
            }

            // Conteggio poesie per autore
            String autore = poesia.getAutore();
            autoreCount.put(autore, autoreCount.getOrDefault(autore, 0) + 1);
        }
    }
 // Trova tag più utilizzato
    String tagPiuUsato = tagCount.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse("Nessun tag");
    System.out.println(tagPiuUsato);
    // Trova autore più prolifico
    String autoreTop = autoreCount.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse("Nessun autore");
    System.out.println(autoreTop);
    ArrayList<String> tagEautore=new ArrayList<String>();
    tagEautore.add(autoreTop);
    tagEautore.add(tagPiuUsato);
    return tagEautore;

	}
	
	
}
