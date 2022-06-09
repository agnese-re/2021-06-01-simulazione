package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private Graph<Genes,DefaultWeightedEdge> grafo;
	
	private GenesDao dao;
	private List<Genes> tuttiGeni;	// tutti i geni (essential e non-essential)
	private Map<String,Genes> idMap;
	
	public Model() {
		dao = new GenesDao();
		
		tuttiGeni = dao.getAllGenes();
		idMap = new HashMap<String,Genes>();
		for(Genes g: tuttiGeni)
			idMap.put(g.getGeneId(), g);
		
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiunta vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(idMap));
		
		// aggiunta archi
		List<Adiacenza> result = this.dao.getArchi();
		for(Adiacenza a: result) {
			int cromosoma1 = idMap.get(a.getGene1()).getChromosome();
			int cromosoma2 = idMap.get(a.getGene2()).getChromosome();
			
			if(cromosoma1 != cromosoma2)
				Graphs.addEdge(this.grafo, idMap.get(a.getGene1()), 
						idMap.get(a.getGene2()), Math.abs(a.getCorr()));
			else
				Graphs.addEdge(this.grafo, idMap.get(a.getGene1()), 
						idMap.get(a.getGene2()), 2*Math.abs(a.getCorr()));
		}
			
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Genes> getVertici() {
		List<Genes> vertici = new ArrayList<Genes>(this.grafo.vertexSet());
		return vertici;
	}
	
	public List<Neighbor> getVicini(Genes geneUtente) {
		List<Neighbor> result = new ArrayList<Neighbor>();
		
		for(Genes g: Graphs.neighborListOf(this.grafo, geneUtente)) {
			DefaultWeightedEdge e = this.grafo.getEdge(geneUtente, g);
			result.add(new Neighbor(g,this.grafo.getEdgeWeight(e)));
		}
		
		Collections.sort(result);
		return result;
	}
}
