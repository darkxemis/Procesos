package Multiplos;

class Contador { 
	private long valor;
	
	public Contador( long inicial ){ 
		valor = inicial ;
	}
	// ocupa la CPU durante cierto tiempo
	void retrasoOcupado() { 
		
		long tmp = 0 ;
		
		for( int i = 0 ; i < 100000 ; i++ )
			tmp = tmp*i-tmp+i ;
		}
	// incrementa valor en 1
	
	public synchronized void incrementa () { 
		
		long aux = valor ; // hace copia local del valor actual
	
		retrasoOcupado() ; // permite entrelazado cuando no se hace en EM
		
		valor = aux+1 ; // escribe el valor compartido (incrementado)
		}
	// devuelve el valor actual
	public synchronized long getvalor () { 
		return valor;
	}
}
