package it.polito.tdp.genes.model;

public class Neighbor implements Comparable<Neighbor> {

	private Genes neighbor;
	private Double peso;
	
	public Neighbor(Genes neighbor, Double peso) {
		super();
		this.neighbor = neighbor;
		this.peso = peso;
	}

	public Genes getNeighbor() {
		return neighbor;
	}

	public void setNeighbor(Genes neighbor) {
		this.neighbor = neighbor;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	@Override
	public String toString() {
		return this.neighbor + " " + this.peso;
	}

	@Override
	public int compareTo(Neighbor o) {
		return -this.getPeso().compareTo(o.getPeso());
	}

	
}
