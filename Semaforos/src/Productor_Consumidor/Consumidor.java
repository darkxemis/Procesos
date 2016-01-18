package Productor_Consumidor;


public class Consumidor implements Runnable {
	private Buffer buffer;
	public Thread thr; // objeto hebra encapsulado
	private boolean termianan_cons;

	
	public Consumidor (Buffer b,String nombre,boolean termianan_cons){
		this.buffer = b;
		thr = new Thread(this,nombre);
		this.termianan_cons = termianan_cons;
	}
	
	@Override
	public void run(){
		do{
			try {
				
					buffer.consumir(thr);
					//Thread.sleep(2000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!termianan_cons);
	}
}
	