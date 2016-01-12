package Multiplos;

class Hebra implements Runnable{
	int numero ; // cuenta múltiplos de este número
	public Thread thr ; // objeto encapsulado
	private Contador cont; // contador de número de múltiplos
	
	public Hebra( String nombre, int p_numero, Contador p_contador ){
		numero = p_numero;
		cont = p_contador;
		thr = new Thread( this, nombre );
	}
	
	public void run (){
		for ( int i = 1 ; i <= 1000 ; i++ )
			if (i % numero == 0)
				cont.incrementa();
	}	
}
