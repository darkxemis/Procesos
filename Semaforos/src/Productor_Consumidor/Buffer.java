package Productor_Consumidor;
import java.util.concurrent.*;

public class Buffer {
	private int[] buffer;
	private int entrada = 0; //Variable donde va a producir el productor
	private int salida = 0; //Variable donde va a consumir el consumidor
	private int contador = 0;
	private Semaphore mutex = new Semaphore(1,true);
	private Semaphore vacio = new Semaphore (0,true);
	private Semaphore lleno;
	
	public Buffer (int tamano) {
		buffer = new int [tamano];
		lleno = new Semaphore (buffer.length,true);
	}
	
	public void producir(Thread thr) throws InterruptedException{
		
		lleno.acquire();
		mutex.acquire();
		
		buffer[entrada] = 1;
		System.out.println("Hebraproductora: " + thr.getName() + " produce en posición: " + entrada);
		Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "Productor produce " + "\n");
		entrada = (entrada + 1) % buffer.length;
		contador = contador + 1;
		
		System.out.println("Productor produce ");
		for (int i = 0; i<buffer.length;i++){
			System.out.print("[" + buffer[i] + "]");
		}
		
		for (int i = 0; i<buffer.length;i++){
			Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "[" + buffer[i] + "]");
		}
		Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "\n");
		System.out.print("\n");
		
		mutex.release();
		vacio.release();

	}
	
	public void consumir(Thread thr) throws InterruptedException{
		
		vacio.acquire();
		mutex.acquire();
		
		buffer[salida] = 0;
		System.out.println("Hebraconsumidora: " + thr.getName() + " consume en posición: " + salida);
		Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "Productor consume " + "\n");
		salida = (salida + 1) % buffer.length;
		contador = contador - 1;
		System.out.println("Consumidor consume ");
		for (int i = 0; i<buffer.length;i++){
			System.out.print("[" + buffer[i] + "]");
		}
		
		for (int i = 0; i<buffer.length;i++){
			Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "[" + buffer[i] + "]");
		}	
		Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "\n");
		System.out.print("\n");
		
		mutex.release();
		lleno.release();
		
	}
}
