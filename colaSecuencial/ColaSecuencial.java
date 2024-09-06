package colaSecuencial;
//Programa 7: Cola Secuencial, utiliza la clase Nodo

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class ColaSecuencial {
	private Nodo head;
	private Nodo tail;

	public ColaSecuencial() {
		this.head = new Nodo("hnull");
		this.tail = new Nodo("tnull");
		this.head.next = this.tail;
	}

	public Boolean enq(String x) {
		Nodo newnode = new Nodo(x);
		if (this.head.next == this.tail) {
			newnode.next = this.tail;
			this.head.next = newnode;
		} else {
			Nodo last = this.tail.next;
			newnode.next = tail;
			last.next = newnode;
		}
		tail.next = newnode;
		return true;
	}

	public String deq() {
		if (this.head.next == this.tail) {
			return "empty";
		}
		Nodo first = this.head.next;
		this.head.next = first.next;
		return first.item;
	}

	public void print() {
		System.out.println("Print ");
		Nodo pred = this.head;
		Nodo curr = pred.next;
		System.out.println(pred.item);
		while (curr.item != "tnull") {
			pred = curr;
			curr = curr.next;
			System.out.println(pred.item);
		}
	}

	public static void main(String[] args) {
		ColaSecuencial queue = new ColaSecuencial();
		ExecutorService executor = Executors.newFixedThreadPool(6);

		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
		List<Future<String>> futures2 = new ArrayList<Future<String>>();

		futures.add(executor.submit(() -> queue.enq("x")));
		futures.add(executor.submit(() -> queue.enq("a")));
		futures2.add(executor.submit(() -> queue.deq()));
		futures.add(executor.submit(() -> queue.enq("b")));
		futures2.add(executor.submit(() -> queue.deq()));
		futures2.add(executor.submit(() -> queue.deq()));

		for (Future<Boolean> future : futures) {
			try {
				System.out.println(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (Future<String> future : futures2) {
			try {
				System.out.println(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*
		 * try {
		 * System.out.println("Enqueue x: " + futures.get(0).get());
		 * System.out.println("Enqueue a: " + futures.get(1).get());
		 * System.out.println("Dequeue: " + futures2.get(0).get());
		 * System.out.println("Enqueue b: " + futures.get(2).get());
		 * System.out.println("Dequeue: " + futures2.get(1).get());
		 * System.out.println("Dequeue: " + futures2.get(2).get());
		 * } catch (Exception e) {
		 * e.printStackTrace();
		 * }
		 */

		executor.shutdown();
		queue.print();
	}

}
