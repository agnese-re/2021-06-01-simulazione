package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Genes> getVertici(Map<String,Genes> idMap) {
		String sql = "SELECT DISTINCT(genes.GeneID) "
				+ "FROM genes "
				+ "WHERE genes.Essential = \"Essential\"";
		List<Genes> result = new ArrayList<Genes>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
				result.add(idMap.get(rs.getString("GeneID")));
				
			conn.close();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> getArchi() {
		String sql = "SELECT interactions.GeneID1, interactions.GeneID2, interactions.Expression_Corr "
				+ "FROM interactions "
				+ "WHERE interactions.GeneID1 IN (SELECT genes.GeneID "
				+ "										FROM genes "
				+ "										WHERE genes.Essential = \"Essential\") "
				+ "	AND interactions.GeneID2 IN(SELECT genes.GeneID "
				+ "										FROM genes "
				+ "										WHERE genes.Essential = \"Essential\") "
				+ " 	AND GeneID1 <> GeneID2";
				/* + "		AND Expression_Corr != 0"; */
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
				result.add(new Adiacenza(rs.getString("GeneID1"),rs.getString("GeneID2"),
						rs.getDouble("Expression_Corr")));
			
			conn.close();
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
