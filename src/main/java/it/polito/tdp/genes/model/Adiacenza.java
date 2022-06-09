package it.polito.tdp.genes.model;

public class Adiacenza {

	private String gene1;
	private String gene2;
	private double corr;
	
	public Adiacenza(String gene1, String gene2, double corr) {
		super();
		this.gene1 = gene1;
		this.gene2 = gene2;
		this.corr = corr;
	}

	public String getGene1() {
		return gene1;
	}

	public void setGene1(String gene1) {
		this.gene1 = gene1;
	}

	public String getGene2() {
		return gene2;
	}

	public void setGene2(String gene2) {
		this.gene2 = gene2;
	}

	public double getCorr() {
		return corr;
	}

	public void setCorr(double corr) {
		this.corr = corr;
	}
	
	
}
