package Productor_Consumidor;

public class ConsumidorMonitor implements Runnable{
	private BufferMonitor buffer;
	public Thread thr; // objeto hebra encapsulado
	private boolean termianan_cons;

	
	public ConsumidorMonitor (BufferMonitor b,String nombre,boolean termianan_cons){
		this.buffer = b;
		thr = new Thread(this,nombre);
		this.termianan_cons = termianan_cons;
	}
	
	@Override
	public void run(){
		try {
			do{
				buffer.consumir(thr);
			}while(!termianan_cons);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
