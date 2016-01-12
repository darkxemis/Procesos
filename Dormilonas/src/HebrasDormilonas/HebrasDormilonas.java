package HebrasDormilonas;

public class HebrasDormilonas {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        try { 
             if ( args.length < 3 ){ 
                System.out.println("Falta numero de hebras, t_max_siesta, vueltas");
                System.exit( 1 );
            }
             
            int nHebras = Integer.parseInt(args[0]);
            int vueltas = Integer.parseInt(args[1]);
            int siesta = Integer.parseInt(args[2]);
         

            System.out.println("Número Hebras = " + nHebras + "| Vueltas = " + vueltas + "| Tiempo siesta = " + siesta);
            Dormilonas[] vhd = new Dormilonas[nHebras] ; // crea vector de hebras

            for (int i =0 ; i < nHebras ; i++){ 
                String nombre = "Dormilona número " + i;
                vhd[i] = new Dormilonas(nombre,vueltas,siesta); // crear hebra i.
                vhd[i].thr.start(); // comienza su ejec.
            }
            // la hebra principal duerme durante un segundo
            Thread.sleep(1000);
            System.out.println("main(): he terminado de dormir");

            // esperar a que terminen todas las hebras creadas
            for( int i = 0 ; i < nHebras ; i++ ) {
                vhd[i].thr.join();
            }
            
        } catch( InterruptedException e ){ 
             System.out.println("main(): me fastidiaron la siesta!");
        }
    }
 }
