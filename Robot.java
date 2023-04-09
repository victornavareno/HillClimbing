package RobotPackage;

import java.util.*;

/*This is the superclass that should be used to construct a hill climbing solution, adding your own 
heuristic search variation on one of the search algortithms*/
public abstract class Robot {

	protected int objetivoMonedas; // monedas a conseguir para salir del laberinto
	protected int monedasConseguidas;
	protected int nodosGenerados; 
	protected boolean haySolucion; 

	protected int[][] estadoActual; // nuestra matriz - laberinto
	protected int[] posicionRobot; // array 2 elementos, pos[0] = coordenada X del robot, pos[1] = coordenada Y del robot
	protected List<String> solucion;


	//los parametros laberinto y objetivoMonedas se leeran en el fichero proporcionado
	public Robot(int[][] matrizLaberinto, int _objetivoMonedas) {
		this.estadoActual = matrizLaberinto;
		this.objetivoMonedas = _objetivoMonedas;
		encontrarRobot();

		this.monedasConseguidas = 0;
		this.haySolucion = true;
		this.solucion = new ArrayList<String>(); // Guardaremos la secuencia de movimientos que llevan a nuestro robot a la salida con las monedas
		this.nodosGenerados = 0;
	}

	//Devuelve un array[2] con las coordenadas del robot en el estado actual
	protected void encontrarRobot() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (estadoActual[i][j] == 8) {
					posicionRobot =  new int[] {i, j};
				}
			}
		}
	}

	public abstract void buscarMonedas();

	protected boolean buscarSalida() {
		Random rand = new Random();

		boolean encontrado = false;
		List<int[]> direccion = getListaMovimientosDisponibles();

		while((direccion.size() > 0) && !encontrado) {
			int random = rand.nextInt(direccion.size()); 
			int posX = posicionRobot[0] + direccion.get(random)[0];
			int posY = posicionRobot[1] + direccion.get(random)[1];
			this.nodosGenerados++;

			if((estadoActual[posX][posY] < 8)) { // ahora la condicion sera que sea menor que 8, ya que la salida valdra 7
				estadoActual[posicionRobot[0]][posicionRobot[1]] = 0; 
				if(estadoActual[posX][posY] == 7) {
					estadoActual[posX][posY] = 8;
					encontrarRobot(); 
					traducirMovimiento(direccion.get(random)[0], direccion.get(random)[1]);
					return true;
				}
				estadoActual[posX][posY] = 8;
				encontrarRobot(); 
				traducirMovimiento(direccion.get(random)[0], direccion.get(random)[1]); // añado el movimiento correcto a nuestro array

				encontrado = true;
			}
			direccion.remove(random);  // borramos el nodo fallido para no volver a visitarlo
		}
		if(encontrado == false) {
			System.out.println( "NO HAY SOLUCION" );
			this.haySolucion = false; // para parar el programa
		}
		return false;
	}


	//Devuelve un ArrayList con los movimientos disponibles de nuestro robot
	protected List<int[]> getListaMovimientosDisponibles(){
		List<int[]> direccion = new ArrayList<>();

		direccion.add(new int[] {-1, 0});  // ARRIBA
		direccion.add(new int[] {1, 0});   // ABAJO
		direccion.add(new int[] {0, -1});  // IZQUIERDA
		direccion.add(new int[] {0, 1});   // DERECHA
		direccion.add(new int[] {1, 1});   // ABAJO DERECHA
		direccion.add(new int[] {-1, 1});  // ARRIBA DERECHA
		direccion.add(new int[] {1, -1});  // ABAJO IZQUIERDA
		direccion.add(new int[] {-1, -1}); // ARRIBA IZQUIERDA 

		return direccion;
	}

	// Añade nuestro movimiento correcto al array solucion
	protected void traducirMovimiento(int posX, int posY) {
		if (posX == 0 && posY == 1) {
			//	    	System.out.println("D");
			solucion.add("D"); // DERECHA
		} else if (posX == 0 && posY == -1) {
			//	    	System.out.println("I");
			solucion.add("I"); // IZQUIERDA
		} else if (posX == 1 && posY == 0) {
			//	    	System.out.println("B");
			solucion.add("B"); // ABAJO
		} else if (posX == -1 && posY == 0) {
			//	    	System.out.println("A");
			solucion.add("A"); // ARRIBA
		} else if (posX == 1 && posY == 1) {
			//	    	System.out.println("BD");
			solucion.add("BD"); // ABAJO DERECHA
		} else if (posX == 1 && posY == -1) {
			//	    	System.out.println("BI");
			solucion.add("BI"); // ABAJO IZQUIERDA
		} else if (posX == -1 && posY == -1) {
			//	    	System.out.println("AI");
			solucion.add("AI"); // ARRIBA IZQUIERDA
		} else if (posX == -1 && posY == 1) {
			//	    	System.out.println("AD");
			solucion.add("AD"); // ARRIBA-DERECHA
		} else {
			System.out.println("MOVIMIENTO INVÁLIDO: (" + posX + ", " + posY + ")");
		}
	}

	// Imprime el estado actual - el laberinto en esta situacion
	protected void imprimirMatriz()
	{
		for(int i = 0; i < 10; i++) {
			System.out.print("     ");
			for (int j = 0; j < 10; j++) { 

				if(estadoActual[i][j] == 8) { // IMPRIMO EL ROBOT EN AZUL CYAN
					//System.out.print("\033[1;36m\u25A0 \033[0m");
					System.out.print("\033[32m\u25A0\033[0m ");

				}
				else if((estadoActual[i][j] < 7) && (estadoActual[i][j] > 0)) { //IMPRIMO LAS MONEDAS DISPONIBLES EN AMARILLO
					System.out.print("\033[1;33m" + estadoActual[i][j] + " \033[0m");

				}
				else if(estadoActual[i][j] == 7) { // SALIDA - IMPRIMO UN CIRCULO ROJO PARA INDICAR LA SALIDA DEL LABERINTO 
					System.out.print("\033[1;31m" + "\u25CF" + " \033[0m");
				}
				else if(estadoActual[i][j] == 0) { // NO IMPRIMO LAS CASILLAS 0
					System.out.print("  ");
				}

				else System.out.print("\u25A0" + " "); // IMPRIMO UN CUADRADO BLANCO PARA LOS MUROS (CUIDADO CON TENER FONDO BLANCO EN EL IDE!!!)
			}
			System.out.println();
		}
		System.out.println();
	}

	protected void imprimirSolucion() {
		System.out.println("   LOS MOVIMIENTOS SOLUCION SON: ");
		int saltoLinea = 0;
		for(int i = 0;  i<solucion.size(); i++) {
			System.out.print(solucion.get(i));
			// Evita imprimir una coma despues del ultimo movimiento:
			if(i<solucion.size() -1 ) {
				System.out.print(", ");
			}
			// Hacer salto de linea para evitar chorro de movimientos
			saltoLinea ++;
			if(saltoLinea == Config.saltoLinea) {
				System.out.println();
				saltoLinea = 0;
			}
		}
		System.out.println();
		System.out.println();
	}

	// Metodo que articula la estrategia de escalada simple
	protected double encontrarSolucion() {
		long startTime = System.nanoTime();

		System.out.println("      LABERINTO INICIAL:");
		imprimirMatriz();
		while((monedasConseguidas < objetivoMonedas) && haySolucion) {
			buscarMonedas();
		}

		while(!buscarSalida() && (this.nodosGenerados < Config.limiteNodosGenerados));
		System.out.println();

		if(this.nodosGenerados >= Config.limiteNodosGenerados) {

			System.out.println("\033[1;31m NO HEMOS ENCONTRADO UNA SOLUCION GENERANDO " + nodosGenerados + " NODOS\033[0m");
			System.out.println("     Pruebe a aumentar el limite de nodos generados");
			System.out.println();
		}

		else {
			imprimirSolucion();
			System.out.println("     LABERINTO RESUELTO:");
			imprimirMatriz();
			System.out.println("    HEMOS GENERADO: " + this.nodosGenerados + " NODOS ");
			System.out.println();
		}

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  // in nanoseconds
		double seconds = (double) duration / 1_000_000_000.0; // convert to seconds
		double roundedDuration = Math.round(seconds * 10000.0) / 10000.0; // round to 3 decimal places

		
		return roundedDuration;
	}
}
