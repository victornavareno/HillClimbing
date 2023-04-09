package RobotPackage;

import java.util.*;

public class EscaladaMaximaPendiente extends Robot {


	//los parametros laberinto y objetivoMonedas se leeran en el fichero proporcionado
	public EscaladaMaximaPendiente(int[][] matrizLaberinto, int _objetivoMonedas) {
		super(matrizLaberinto, _objetivoMonedas);
	}

	@Override
	public void buscarMonedas() {
		List<int[]> direccion = getListaMovimientosDisponibles();

		int indiceMejorOpcion = 0;

		int valorMejorOpcion = 0;
		int posiX = 0;
		int posiY = 0;

		for(int i = 0; i < direccion.size(); i++) {
			this.nodosGenerados++; // estamos generando otro nodo de nuestro arbol de exploracion

			int posX = posicionRobot[0] + direccion.get(i)[0];
			int posY = posicionRobot[1] + direccion.get(i)[1];
			
			if((estadoActual[posX][posY] < 7) && estadoActual[posX][posY] >= 0) {
				if((estadoActual[posX][posY] >= valorMejorOpcion) && estadoActual[posX][posY] != -1) {
					indiceMejorOpcion = i;
					valorMejorOpcion = estadoActual[posX][posY];
					posiX = posX;
					posiY = posY;
				}
			}
			else if(i == 7 && (estadoActual[posX][posY] == -1 || estadoActual[posX][posY] == 9)) {
				this.haySolucion=false;
			}
		}
		//System.out.println( " ELEGIDO  " + indiceMejorOpcion);

		estadoActual[posicionRobot[0]][posicionRobot[1]] = -1; 
		this.monedasConseguidas += valorMejorOpcion; // le sumo las monedas de la mejor opcion posible
		estadoActual[posiX][posiY] = 8; // muevo el robot
		encontrarRobot(); // actualizo la posicion del robot
		traducirMovimiento(direccion.get(indiceMejorOpcion)[0], direccion.get(indiceMejorOpcion)[1]);
	}

}
