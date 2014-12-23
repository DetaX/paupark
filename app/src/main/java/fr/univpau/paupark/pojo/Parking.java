package fr.univpau.paupark.pojo;

public class Parking {
	private final boolean souterrain;
	private final boolean payant;
	private final String commune;
	private final String nom;
	private final int places;
	private final Double[] coord;
	
	public Parking(boolean souterrain, boolean payant, String commune, String nom, int places, Double[] coord) {
		this.souterrain = souterrain;
		this.payant = payant;
		this.commune = commune;
		this.nom = nom;
		this.places = places;
		this.coord = coord;
	}

	public boolean isSouterrain() {
		return souterrain;
	}

	public boolean isPayant() {
		return payant;
	}

	public String getCommune() {
		return commune;
	}

	public String getNom() {
		return nom;
	}

	public int getPlaces() {
		return places;
	}

	public Double[] getCoord() {
		return coord;
	}
	
}
