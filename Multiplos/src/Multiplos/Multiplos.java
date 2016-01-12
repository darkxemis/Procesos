package Multiplos;

public class Multiplos {

    public static void main(String[] args) {
        try {
            Contador contH = new Contador(0); // contador usado por la hebras
            Hebra[] vc = new Hebra[2] ;
            vc[0] = new Hebra("Contador2 ", 2, contH);
            vc[1] = new Hebra("Contador3 ", 3, contH);
            vc[0].thr.start(); vc[1].thr.start(); // lanza hebras
            vc[0].thr.join(); vc[1].thr.join(); // espera terminación
            
            System.out.println("Resultado hebras : "+contH.getvalor());
            
            long contV = 0 ; // contador de verificacion
            
            for ( int i = 1 ; i <= 1000 ; i++ ) {
                if ( i % 2 == 0 ) contV = contV + 1 ;
                if ( i % 3 == 0 ) contV = contV + 1 ;
            }
            
            System.out.println("Resultado correcto: " + contV);
            
        } catch (InterruptedException e){
            System.out.println ("ha ocurrido una excepcion.");
        }
    }
    
}
