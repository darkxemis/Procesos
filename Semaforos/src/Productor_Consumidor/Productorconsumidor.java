package Productor_Consumidor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Component;

public class Productorconsumidor {

	private JFrame frame;
	private JTextField productoras;
	private JTextField consumidoras;
	private JTextField tamano;
	private JTextField terminan_prod;
	private JTextField terminan_con;
	static JTextArea mostrar;
	public Hilo hilo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Productorconsumidor window = new Productorconsumidor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the application.
	 */
	public Productorconsumidor() {
		initialize();
	}

	/**
	 * Clase hilo que lanzamos nuestra función de semáforos y monitores.
	 */
	
	public class Hilo extends Thread{
		private int tamano_buffer;
		private int n_hebras_prod;
		private int n_hebras_cons;
		private String productoras_terminan;
		private String consumidoras_terminan;
		private boolean lanzar_semaforo;
		
		public Hilo(int tamano_buffer,int n_hebras_prod,int n_hebras_cons, String productoras_terminan,String consumidoras_terminan,boolean lanzar_semaforo){
			this.tamano_buffer = tamano_buffer;
			this.n_hebras_prod = n_hebras_prod;
			this.n_hebras_cons = n_hebras_cons;
			this.productoras_terminan = productoras_terminan;
			this.consumidoras_terminan = consumidoras_terminan;
			this.lanzar_semaforo = lanzar_semaforo;
		}
		
		public void lanzarSemaforos(int tamano_buffer,int n_hebras_prod,int n_hebras_cons, String productoras_terminan,String consumidoras_terminan) throws InterruptedException{
			boolean terminan_prod,terminan_cons;
			terminan_prod = Boolean.parseBoolean(productoras_terminan);
			terminan_cons = Boolean.parseBoolean(consumidoras_terminan);
			
			Buffer buffer = new Buffer(tamano_buffer);	
			Productor[] productoras = new Productor[n_hebras_prod] ; // crea vector de hebras
			Consumidor[] consumidoras = new Consumidor[n_hebras_cons] ; // crea vector de hebras
			// Creación de hebras productoras
			for (int i =0 ; i < n_hebras_prod ; i++){ 
				String nombre = "Hebra productora número " + i;
				productoras[i] = new Productor(buffer,nombre,terminan_prod); // crear hebra i.
				productoras[i].thr.start(); // comienza su ejec.
	        }
			// Creación de hebras consumidoras
			for (int i =0 ; i < n_hebras_cons ; i++){ 
				String nombre = "Hebra consumidora número " + i;
				consumidoras[i] = new Consumidor(buffer,nombre,terminan_cons); // crear hebra i.
				consumidoras[i].thr.start(); // comienza su ejec.
		    }
			
			 // esperar a que terminen todas las hebras creadas
	        for( int i = 0 ; i < n_hebras_prod ; i++ ) {
	        	productoras[i].thr.join();
	        }
	        
	        // esperar a que terminen todas las hebras creadas
	        for( int i = 0 ; i < n_hebras_cons ; i++ ) {
	        	consumidoras[i].thr.join();
	        }
	       
		}
		
		public void lanzarMonitor(int tamano_buffer,int n_hebras_prod,int n_hebras_cons, String productoras_terminan,String consumidoras_terminan) throws InterruptedException{
			boolean terminan_prod,terminan_cons;
			terminan_prod = Boolean.parseBoolean(productoras_terminan);
			terminan_cons = Boolean.parseBoolean(consumidoras_terminan);
			
			BufferMonitor buffer = new BufferMonitor(tamano_buffer);	
			ProductorMonitor[] productoras = new ProductorMonitor[n_hebras_prod] ; // crea vector de hebras
			ConsumidorMonitor[] consumidoras = new ConsumidorMonitor[n_hebras_cons] ; // crea vector de hebras

			for (int i =0 ; i < n_hebras_prod ; i++){ 
				String nombre = "Hebra productora número " + i;
				productoras[i] = new ProductorMonitor(buffer,nombre,terminan_prod); // crear hebra i.
				productoras[i].thr.start(); // comienza su ejec.
	        }
			
			for (int i =0 ; i < n_hebras_cons ; i++){ 
				String nombre = "Hebra consumidora número " + i;
				consumidoras[i] = new ConsumidorMonitor(buffer,nombre,terminan_cons); // crear hebra i.
				consumidoras[i].thr.start(); // comienza su ejec.
		    }
			
			 // esperar a que terminen todas las hebras creadas
	        for( int i = 0 ; i < n_hebras_prod ; i++ ) {
	        	productoras[i].thr.join();
	        }
	        
	        // esperar a que terminen todas las hebras creadas
	        for( int i = 0 ; i < n_hebras_cons ; i++ ) {
	        	consumidoras[i].thr.join();
	        }
	       
		}
		@Override
		public void run(){
			try {
				if (lanzar_semaforo == true){
					lanzarSemaforos(tamano_buffer,n_hebras_prod,n_hebras_cons,productoras_terminan,consumidoras_terminan);
				}else{
					lanzarMonitor(tamano_buffer,n_hebras_prod,n_hebras_cons,productoras_terminan,consumidoras_terminan);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws InterruptedException 
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 625, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		productoras = new JTextField();
		productoras.setColumns(10);
		
		consumidoras = new JTextField();
		consumidoras.setColumns(10);
		
		tamano = new JTextField();
		tamano.setColumns(10);
		
		JLabel lblTamao = new JLabel("Tama\u00F1o");
		
		JLabel lblNProductoras = new JLabel("N\u00BA productoras");
		
		JLabel lblConsumidoras = new JLabel("N\u00BA Consumidoras");
		
		terminan_prod = new JTextField();
		terminan_prod.setColumns(10);
		
		JLabel lblTerminanProducir = new JLabel("Terminan producir");
		
		terminan_con = new JTextField();
		terminan_con.setColumns(10);
		
		JLabel terminan_cons = new JLabel("Terminan consumir");
		
		mostrar = new JTextArea();
		mostrar.setEditable(false);
		
		JButton semaforos = new JButton("Sem\u00E1foros");
		semaforos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int tamano_buffer = Integer.parseInt(tamano.getText().toString());
				int n_hebras_prod = Integer.parseInt(productoras.getText().toString());
				int n_hebras_cons = Integer.parseInt(consumidoras.getText().toString());
				String productoras_terminan = terminan_prod.getText().toString();
				String consumidoras_terminan = terminan_con.getText().toString();
				
				if (productoras_terminan.equals("true") || productoras_terminan.equals("false")){
					if(consumidoras_terminan.equals("true") || consumidoras_terminan.equals("false")){
						System.out.println("Ejecutando...");
						//lanzarSemaforos(tamano_buffer,n_hebras_prod,n_hebras_cons,productoras_terminan,consumidoras_terminan);
						hilo = new Hilo(tamano_buffer,n_hebras_prod,n_hebras_cons,productoras_terminan,consumidoras_terminan,true);
						hilo.start();
					}else{
						System.out.println("Consumidoras deben ser true o false");
					}
				}else{
					System.out.println("Productoras deben ser true o false");
				}
			}
		});
		
		JButton monitores = new JButton("Monitores");
		monitores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int tamano_buffer = Integer.parseInt(tamano.getText().toString());
				int n_hebras_prod = Integer.parseInt(productoras.getText().toString());
				int n_hebras_cons = Integer.parseInt(consumidoras.getText().toString());
				String productoras_terminan = terminan_prod.getText().toString();
				String consumidoras_terminan = terminan_con.getText().toString();
				
				if (productoras_terminan.equals("true") || productoras_terminan.equals("false")){
					if(consumidoras_terminan.equals("true") || consumidoras_terminan.equals("false")){
						System.out.println("Ejecutando...");
						//lanzarSemaforos(tamano_buffer,n_hebras_prod,n_hebras_cons,productoras_terminan,consumidoras_terminan);
						hilo = new Hilo(tamano_buffer,n_hebras_prod,n_hebras_cons,productoras_terminan,consumidoras_terminan,false);
						hilo.start();
					}else{
						System.out.println("Consumidoras deben ser true o false");
					}
				}else{
					System.out.println("Productoras deben ser true o false");
				}
			}
		});

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(50)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(terminan_cons, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
											.addGroup(groupLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblTerminanProducir, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addComponent(lblConsumidoras)))
									.addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(terminan_con, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(consumidoras, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(terminan_prod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTamao, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNProductoras, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
									.addGap(34)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(tamano, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(productoras, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addGap(96))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(85)
							.addComponent(semaforos)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(mostrar, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
						.addComponent(monitores, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
					.addGap(36))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(monitores, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(semaforos, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(78)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(tamano, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTamao, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(productoras, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNProductoras, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(terminan_prod, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTerminanProducir, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(consumidoras, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblConsumidoras, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(terminan_con, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(terminan_cons, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(74)
							.addComponent(mostrar, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
