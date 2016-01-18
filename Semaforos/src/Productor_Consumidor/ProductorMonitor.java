package Productor_Consumidor;

public class ProductorMonitor implements Runnable{
	private BufferMonitor buffer;
	public Thread thr; // objeto hebra encapsulado
	private boolean terminan_prod;
	
	public ProductorMonitor (BufferMonitor b,String nombre,boolean terminan_prod){
		this.buffer = b;
		thr = new Thread(this,nombre);
		this.terminan_prod = terminan_prod;
	}
	@Override
	public void run(){
		try {
			do{
				buffer.producir(thr);
			}while(!terminan_prod);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
