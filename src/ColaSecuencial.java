//Programa 7: Cola Secuencial, utiliza la clase Nodo

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	public static void onlyDeq(ExecutorService executor, ColaSecuencial queue) {
		try {

			executor.submit(() -> queue.deq());
			Thread.sleep(2000);
			executor.submit(() -> queue.deq());
			Thread.sleep(2000);
			executor.submit(() -> queue.deq());
			executor.submit(() -> queue.deq());
			executor.submit(() -> queue.deq());
			Thread.sleep(3000);
			executor.submit(() -> queue.deq());
			System.out.println("End of onlyDeq");
		} catch (Exception e) {
			System.out.println("Error" + e);
		}

	}

	public static void onlyEnq(ExecutorService executor, ColaSecuencial queue) {
		try {
			executor.submit(() -> queue.enq("x"));
			Thread.sleep(2000);
			executor.submit(() -> queue.enq("a"));
			executor.submit(() -> queue.enq("b"));
			executor.submit(() -> queue.enq("c"));
			Thread.sleep(3000);
			executor.submit(() -> queue.enq("x"));
		} catch (Exception e) {
			System.out.println("Error" + e);
		}

		System.out.println("End of onlyEnq");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ColaSecuencial queue = new ColaSecuencial();
		ExecutorService executor = Executors.newFixedThreadPool(4);
		System.out.println("Start");
		// queue.deq();
		// queue.enq("x");
		// queue.enq("a");
		// queue.deq();
		// queue.enq("b");
		// queue.enq("c");
		// queue.deq();
		// queue.deq();
		// queue.deq();
		// queue.deq();
		// queue.enq("x");

		// Concurrent queue
		onlyEnq(executor, queue);
		onlyDeq(executor, queue);
		executor.shutdown();
		queue.print();
	}

}
