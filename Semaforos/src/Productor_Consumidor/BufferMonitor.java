package Productor_Consumidor;

public class BufferMonitor {
	private int[] buffer;
	private int entrada = 0; //Variable donde va a producir el productor
	private int salida = 0; //Variable donde va a consumir el consumidor
	private int contador = 0;

	
	public BufferMonitor (int tamano) {
		buffer = new int [tamano];
	}
	
	public synchronized void producir(Thread thr) throws InterruptedException{
		
		while(contador == buffer.length){
			wait();
		}
		System.out.println("Hebraproductora: " + thr.getName() + " produce en posición: " + entrada);
		buffer[entrada] = 1;
		System.out.println("Productor produce ");
		Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "Productor produce " + "\n");
		entrada = (entrada + 1) % buffer.length;
		contador = contador + 1;
		
		for (int i = 0; i<buffer.length;i++){
			System.out.print("[" + buffer[i] + "]");
		}
		
		for (int i = 0; i<buffer.length;i++){
			Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "[" + buffer[i] + "]");
		}
		Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "\n");
		System.out.print("\n");
		
		notifyAll();

		
	}
	
	public synchronized void consumir(Thread thr) throws InterruptedException{
		
		while(contador == 0){
			wait();
		}
		
		System.out.println("Hebraconsumidora: " + thr.getName() + " consume en posición: " + salida);
		buffer[salida] = 0;
		System.out.println("Consumidor consume ");
		Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "Productor consume " + "\n");
		salida = (salida + 1) % buffer.length;
		contador = contador - 1;
		
		for (int i = 0; i<buffer.length;i++){
			System.out.print("[" + buffer[i] + "]");
		}
		
		for (int i = 0; i<buffer.length;i++){
			Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "[" + buffer[i] + "]");
		}
		Productorconsumidor.mostrar.setText(Productorconsumidor.mostrar.getText() + "\n");
		System.out.print("\n");
		
		notifyAll();
	}
}
