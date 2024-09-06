package semaforo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tarea implements Runnable {
	int tiempoTarea;
	int task;
	final Semaphore smphre; // Para limitar a 3 tareas simultáneamente
	final Lock lock = new ReentrantLock(); // Para proteger una sección crítica

	public Tarea(int i, Semaphore smphre) {
		this.task = i;
		this.smphre = smphre;
	}

	@Override
	public void run() {
		try {
			// Adquirir el semáforo para limitar a tres tareas concurrentes
			smphre.acquire();

			try {
				lock.lock();
				Thread currentThread = Thread.currentThread();
				long id = currentThread.getId();

				int value = (int) (id % 6);
				System.out.println("Running Thread " + value + " task: " + this.task);

				switch (value) {
					case 0, 2:
						this.tiempoTarea = 500;
						break;
					case 1:
						this.tiempoTarea = 2000;
						break;
					default:
						this.tiempoTarea = 3000;
				}
			} finally {
				lock.unlock(); // Liberar el lock después de la sección crítica
			}

			// Simular el tiempo de la tarea
			Thread.sleep(this.tiempoTarea);
			System.out.println("Running Thread " + this.task + " time: " + this.tiempoTarea);

		} catch (InterruptedException e) {
			System.out.println(e);
		} finally {
			// Liberar el semáforo para permitir que otra tarea acceda
			smphre.release();
		}
	}
}