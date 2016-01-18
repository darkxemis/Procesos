package Productor_Consumidor;

public class Productor implements Runnable {
	private Buffer buffer;
	public Thread thr; // objeto hebra encapsulado
	private boolean terminan_prod;
	
	public Productor (Buffer b,String nombre,boolean terminan_prod){
		this.buffer = b;
		thr = new Thread(this,nombre);
		this.terminan_prod = terminan_prod;
	}
	@Override
	public void run(){
		do{
			try {
				buffer.producir(thr);
				//Thread.sleep(2000);
				
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!terminan_prod);
	}
}
