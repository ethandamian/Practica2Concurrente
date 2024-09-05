import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class ColaSynchronized {
    private Nodo head;
    private Nodo tail;

    // Constructor inicializa la cola con nodos centinela.
    public ColaSynchronized() {
        this.head = new Nodo("hnull");
        this.tail = new Nodo("tnull");
        this.head.next = this.tail;
    }

    // Encola un nuevo elemento al final de la cola.
    public synchronized Boolean enq(String x) {
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

    // Desencola el primer elemento de la cola o retorna "empty" si la cola está
    // vacía.
    public synchronized String deq() {
        if (this.head.next == this.tail) {
            return "empty";
        }
        Nodo first = this.head.next;
        this.head.next = first.next;
        return first.item;
    }

    // Imprime los elementos de la cola.
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

    // Método principal que crea un pool de hilos, encola y desencola elementos,
    // e imprime los resultados usando Futures.
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

// Nodo class for the queue
class Nodo {
    String item;
    Nodo next;

    Nodo(String item) {
        this.item = item;
        this.next = null;
    }
}
