package dto;

public class StatisticheDTO {
    private int totaleApprezzamenti;
    private int totaleCommenti;
    private String titoloPoesiaPiuApprezzata;
    private String testoPoesiaPiuApprezzata;
    private int likePoesiaPiuApprezzata;

    
    public StatisticheDTO() {}
    
    public StatisticheDTO(int totaleApprezzamenti, int totaleCommenti, String titoloPoesiaPiuApprezzata, String testoPoesiaPiuApprezzata, int likePoesiaPiuApprezzata) {
        this.totaleApprezzamenti = totaleApprezzamenti;
        this.totaleCommenti = totaleCommenti;
        this.titoloPoesiaPiuApprezzata = titoloPoesiaPiuApprezzata;
        this.testoPoesiaPiuApprezzata = testoPoesiaPiuApprezzata;
        this.likePoesiaPiuApprezzata = likePoesiaPiuApprezzata;
    }
    
    public int getTotaleApprezzamenti() { return totaleApprezzamenti; }
    public int getTotaleCommenti() { return totaleCommenti; }
    public String getTitoloPoesiaPiuApprezzata() { return titoloPoesiaPiuApprezzata; }
    public String getTestoPoesiaPiuApprezzata() { return testoPoesiaPiuApprezzata; }
    public int getLikePoesiaPiuApprezzata() { return likePoesiaPiuApprezzata; }
    
    public void setTotaleApprezzamenti(int totaleApprezzamenti) { this.totaleApprezzamenti = totaleApprezzamenti; }
    public void setTotaleCommenti(int totaleCommenti) { this.totaleCommenti = totaleCommenti; }
    public void setTitoloPoesiaPiuApprezzata(String titoloPoesiaPiuApprezzata) { this.titoloPoesiaPiuApprezzata = titoloPoesiaPiuApprezzata; }
    public void setTestoPoesiaPiuApprezzata(String testoPoesiaPiuApprezzata) { this.testoPoesiaPiuApprezzata = testoPoesiaPiuApprezzata; }
    public void setLikePoesiaPiuApprezzata(int likePoesiaPiuApprezzata) { this.likePoesiaPiuApprezzata = likePoesiaPiuApprezzata; }
}