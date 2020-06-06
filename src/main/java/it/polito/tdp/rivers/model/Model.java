package it.polito.tdp.rivers.model;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Simulator sim;
	private Map<Integer, River> idMapRivers;
	
	public Model() {
		this.dao = new RiversDAO();
		this.sim = new Simulator();
		this.idMapRivers = new HashMap<>();
	}
	
	public LocalDate getDateStart(River r) {
		return dao.getDateStart(r);
	}
	
	public LocalDate getDateEnd(River r) {
		return dao.getDateEnd(r);
	}
	
	public int getTotMisurazioni(River r) {
		return dao.getFlows(idMapRivers, r).size();
	}
	
	public float getFmed(River r) {
		return dao.getFmed(r);
	}
	
	public List<River> getAllRivers(){
		return dao.getAllRivers(idMapRivers);
	}
	
	public List<Flow> getFlows(River r){
		return dao.getFlows(idMapRivers, r);
	}
	
	public void simula(int k, float fmed, List<Flow> f) {
		sim.init(k, fmed, f);
		sim.run();
	}
	
	public int getGiorniSenzaErogazioneMinima() {
		return sim.getGiorniSenzaErogazioneMinima();
	}
	
	public double getCmed() {
		return sim.getCmed();
	}
	
}
