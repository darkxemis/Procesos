package HebrasDormilonas;

import java.util.Random;

public class Dormilonas implements Runnable {
    int vueltas = 0; // número de veces que duerme (0 == infinitas)
    int siesta = 0; // tiempo máximo que duerme cada vez
    public Thread thr; // objeto hebra encapsulado

    public Dormilonas(String p_nombre, int p_vueltas, int p_siesta){
        siesta = p_siesta ;
        vueltas = p_vueltas ;
        thr = new Thread(this, p_nombre) ;
    }
     
    public void run() { 
        try { 
            Random random = new Random(); // crea generador de números aleatorios
            // dormir un numero de veces igual a "vueltas"
            for (int i=0 ; i < vueltas || vueltas == 0 ; i++) { // imprimir nombre
                System.out.println( "Vuelta numero: " + i + ", de " +thr.getName());
                // duerme un tiempo aleatorio entre 0 y siesta-1 milisegundos
                
                if (siesta > 0){
                    Thread.sleep(random.nextInt(siesta));
                }
                
            }
        } catch(InterruptedException e) { 
            System.out.println( Thread.currentThread().getName() + ": me fastidiaron la siesta!");
        }
    } 
}
