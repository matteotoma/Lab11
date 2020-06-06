package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Simulator {
	
	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;
	
	// PARAMETRI DELLA SIMULAZIONE
	private double foutMin;
	private List<Flow> flows;
	private double probFout;
	
	// STATO DEL SISTEMA
	private double C;
	private double Q;
	
	// OUTPUT DA CALCOLARE
	private Set<Double> Cgiornaliere;
	private int giorniSenzaErogazioneMinima;
	
	public void init(int k, float fmed, List<Flow> flowsList) {
		this.queue = new PriorityQueue<>();
		
		this.foutMin = 0.8 * fmed;
		this.flows = new ArrayList<>(flowsList);
		this.probFout = 0.05;
		
		this.Q = k * fmed * 30;
		this.C = this.Q / 2;
		
		this.Cgiornaliere = new HashSet<>();
		this.giorniSenzaErogazioneMinima = 0;
		
		Collections.sort(this.flows);
		
		for(Flow f: flows) {
			double prob = Math.random();
			double fout;
			// probabilità pari al 5% di avere un flusso richiesto in uscita più elevato
			if(this.probFout > prob)
				fout = 10 * foutMin;
			else
				fout = foutMin;
			
			Event e = new Event(f.getDay(), f, fout);
			this.queue.add(e);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}
	
	private void processEvent(Event e) {
		double fin = e.getFlow().getFlow();
		double fout = e.getFout();
		
		// è garantita l'erogazione minima
		if(fin > fout) {
			C += fin - fout;
			// flusso in ingresso eccessivo, questo deve essere scaricato in uscita
			if(C > Q)
				C = Q;
		}
		
		if(fin < fout) {
			if((C - (fout - fin)) < 0) {
				// non è garantita l'erogazione minima
				giorniSenzaErogazioneMinima++;
				C = 0;
			}
			else
			    C = C - (fout - fin);
		}
		Cgiornaliere.add(C);
	}

	public int getGiorniSenzaErogazioneMinima() {
		return giorniSenzaErogazioneMinima;
	}

	public double getCmed() {
		double somma = 0.0;
		for(Double d: Cgiornaliere)
			somma += d;
		return somma / Cgiornaliere.size();
	}
	
}
