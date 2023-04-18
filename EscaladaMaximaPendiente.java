package RobotPackage;

import java.util.*;

//AUTOR:    VICTOR ANDRES NAVAREÑO MOZA
//PROYECTO: LABECOIN, INTELIGENCIA ARTIFICIAL Y SISTEMAS INTELIGENTES

public class EscaladaMaximaPendiente extends Robot {

	public EscaladaMaximaPendiente(int[][] matrizLaberinto, int _objetivoMonedas) {
		super(matrizLaberinto, _objetivoMonedas);
	}

	@Override
	public void buscarMonedas() {
		List<int[]> direccion = getListaMovimientosDisponibles();
		List<Integer> posibilidades = new ArrayList<Integer>();
		int valorMejorOpcion = -1;
		Random rand = new Random();

		// guardan las coordenadas de la mejor opcion:
		int posiX = 0; 
		int posiY = 0;

		int mejorOpcionX = 0;
		int mejorOpcionY = 0;
		while(direccion.size() > 0) {
			this.nodosGenerados++; // estamos generando otro nodo de nuestro arbol de exploracion
			int random = rand.nextInt(direccion.size());

			int posX = posicionRobot[0] + direccion.get(random)[0];
			int posY = posicionRobot[1] + direccion.get(random)[1];


			if((estadoActual[posX][posY] < 7)) {
				posibilidades.add(estadoActual[posX][posY]);
				if((estadoActual[posX][posY] > valorMejorOpcion) && estadoActual[posX][posY] != -1) {
					mejorOpcionX = direccion.get(random)[0];
					mejorOpcionY = direccion.get(random)[1];
					valorMejorOpcion = estadoActual[posX][posY];
					posiX = posX;
					posiY = posY;
				}
			}
			direccion.remove(random); // borro este explorado
		}
		//Vemos si estamos rodeados de -1 y  muros:
		if(!hayPosibilidades(posibilidades)) {
			resetearVecinos(); // borramos los -1 para que el robot pruebe otra ruta si nos quedamos sin posibilidades
		}
		else {
			estadoActual[posicionRobot[0]][posicionRobot[1]] = -1; 
			this.monedasConseguidas += valorMejorOpcion; // le sumo las monedas de la mejor opcion posible
			estadoActual[posiX][posiY] = 8; // muevo el robot
			encontrarRobot(); // actualizo la posicion del robot
			direccion = getListaMovimientosDisponibles();
			traducirMovimiento(mejorOpcionX, mejorOpcionY); // añado el movimiento a nuestro vector solucion
		}
	}

	// Analiza los nodos generados en esta iteracion, si todas las posibilidades desplegadas son -1, devuelve false () 
	private boolean hayPosibilidades(List<Integer> posibilidades ) {
		for(int integer : posibilidades) {
			if(integer != -1) {
				return true;
			}
		}
		return false;
	}

	// Reseteo los vecinos, poniendolos a 0, se utiliza cuando nos quedamos 
	// sin posiblidades de exploracion (estamos rodeados de -1 y 9)
	// Sirve para salir de un callejon sin salida, haciendo que nuestra heuristica sea menos estricta
	private void resetearVecinos() {
		List<int[]> direccion = getListaMovimientosDisponibles();
		for (int i = 0; i < direccion.size(); i++) {
			int posX = posicionRobot[0] + direccion.get(i)[0];
			int posY = posicionRobot[1] + direccion.get(i)[1];
			if(estadoActual[posX][posY] < 7) {
				estadoActual[posX][posY] = 0;
			}
		}
	}

}
