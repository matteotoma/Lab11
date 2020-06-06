package it.polito.tdp.rivers.db;

import java.util.LinkedList;

import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers(Map<Integer, River> idMapRivers) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(!idMapRivers.containsKey(res.getInt("id"))) {
					River r = new River(res.getInt("id"), res.getString("name"));
					rivers.add(r);
					idMapRivers.put(r.getId(), r);
				}
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public List<Flow> getFlows(Map<Integer, River> idMapRivers, River r) {
		
		final String sql = "SELECT river, day, flow "
						   +"FROM flow "
						   +"WHERE river=? "; 

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				River river = idMapRivers.get(res.getInt("river"));
				Flow f = new Flow(res.getDate("day").toLocalDate(), res.getFloat("flow"), river);
				flows.add(f);
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flows;
	}
	
	public LocalDate getDateStart(River r) {
		
		final String sql = "SELECT day "
						   +"FROM flow "
						   +"WHERE river=? "
						   +"ORDER BY day ASC "; 
		LocalDate dateStart;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();
			
			res.first();
			dateStart = res.getDate("day").toLocalDate();
			
			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return dateStart;
	}
	
	public LocalDate getDateEnd(River r) {
		
		final String sql = "SELECT day "
						   +"FROM flow "
						   +"WHERE river=? "
						   +"ORDER BY day DESC "; 
		LocalDate dateEnd;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();
			
			res.first();
			dateEnd = res.getDate("day").toLocalDate();
			
			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return dateEnd;
	}

	public float getFmed(River r) {
		final String sql = "SELECT AVG(flow) AS m "
						  +"FROM flow "
						  +"WHERE river=? "; 

		float fmed = 0;

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			if(res.next())
				fmed = res.getFloat("m");

			conn.close();
	
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return fmed;
	}

}
