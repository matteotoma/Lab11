package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{

	private LocalDate time;
	private Flow flow;
	private double fout;
	
	public Event(LocalDate time, Flow flow, double fout) {
		super();
		this.time = time;
		this.flow = flow;
		this.fout = fout;
	}

	public LocalDate getTime() {
		return time;
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public double getFout() {
		return fout;
	}

	public void setFout(double fout) {
		this.fout = fout;
	}

	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", flow=" + flow + ", fout=" + fout + "]";
	}
	
}
