package colaSecuencial;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class ColaSecuencial {
	private Nodo head;
	private Nodo tail;

	// The `public ColaSecuencial()` constructor in the `ColaSecuencial` class is
	// initializing the queue by
	// creating two sentinel nodes - `head` and `tail`. These sentinel nodes are
	// used to simplify the
	// implementation of the queue.
	public ColaSecuencial() {
		this.head = new Nodo("hnull");
		this.tail = new Nodo("tnull");
		this.head.next = this.tail;
	}

	/**
	 * The `enq` function adds a new node containing a given value to the end of a
	 * linked list.
	 * 
	 * @param x The `enq` method you provided seems to be an implementation of an
	 *          enqueue operation in a
	 *          queue data structure. The parameter `x` represents the data that you
	 *          want to enqueue into the queue.
	 *          In this method, a new node containing the data `x` is created and
	 *          added to the end
	 * @return The `enq` method is returning a Boolean value, specifically `true`.
	 */

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

	/**
	 * The `deq()` function removes and returns the item at the front of a queue, or
	 * returns "empty" if the
	 * queue is empty.
	 * 
	 * @return The method is returning the item stored in the first node of the
	 *         linked list.
	 */
	public String deq() {
		if (this.head.next == this.tail) {
			return "empty";
		}
		Nodo first = this.head.next;
		this.head.next = first.next;
		return first.item;
	}

	/**
	 * The `print` function in Java prints the items of a linked list starting from
	 * the head node.
	 */
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

	/**
	 * The main function creates a thread pool, enqueues and dequeues elements in a
	 * sequential queue, and
	 * prints the results using Futures in Java.
	 */
	public static void main(String[] args) {
		ColaSecuencial queue = new ColaSecuencial();
		ExecutorService executor = Executors.newFixedThreadPool(6);

		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
		List<Future<String>> futures2 = new ArrayList<Future<String>>();

		futures.add(executor.submit(() -> {
			Boolean result = queue.enq("x");
			queue.print();
			return result;
		}));

		futures.add(executor.submit(() -> {
			Boolean result = queue.enq("a");
			queue.print();
			return result;
		}));

		futures2.add(executor.submit(() -> {
			String result = queue.deq();
			queue.print();
			return result;
		}));

		futures.add(executor.submit(() -> {
			Boolean result = queue.enq("b");
			queue.print();
			return result;
		}));

		futures2.add(executor.submit(() -> {
			String result = queue.deq();
			queue.print();
			return result;
		}));

		futures2.add(executor.submit(() -> {
			String result = queue.deq();
			queue.print();
			return result;
		}));

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

		executor.shutdown();
	}
}
