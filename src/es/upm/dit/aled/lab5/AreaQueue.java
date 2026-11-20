package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {
	Queue<Patient> waitQueue = new LinkedList<Patient>();
	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position);
	}
	
	@Override
	public synchronized void enter (Patient p) {
		System.out.println("Patient: " + p.getNumber() + " trying to enter " + this.name);
		this.waiting++;
		this.waitQueue.add(p);
		while(numPatients >= this.capacity || (p != this.waitQueue.peek())) {
			System.out.println("Patient: " + p.getNumber() + " waiting for " + this.name);
			try {
				wait();
			} catch(InterruptedException e) {}
		}
		this.waitQueue.remove();
		this.waiting--;
		this.numPatients++;
		System.out.println("Patient: " + p.getNumber() + " has entered " + this.name);
		notifyAll();
	}

	
}
