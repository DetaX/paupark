package fr.univpau.paupark.pojo;

public class Tip {
	private String titre;
	private String adresse;
	private String commune;
	private int capacite;
	private String commentaire;
	private String pseudo;
	private double fiabilite;
	private int id;
	
	
	public Tip(String titre, String adresse, String commune, int capacite,
			String commentaire, String pseudo, double fiabilite, int id) {
		this.titre = titre;
		this.adresse = adresse;
		this.commune = commune;
		this.capacite = capacite;
		this.commentaire = commentaire;
		this.pseudo = pseudo;
		this.fiabilite = fiabilite;
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public String getTitre() {
		return titre;
	}
	public String getAdresse() {
		return adresse;
	}
	public String getCommune() {
		return commune;
	}
	public int getCapacite() {
		return capacite;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public String getPseudo() {
		return pseudo;
	}
	public double getFiabilite() {
		return fiabilite;
	}
	public void setFiabilite(double fiabilite) {
		this.fiabilite = fiabilite;
	}
	public void setId(int id) {
		this.id = id;
	}
}
