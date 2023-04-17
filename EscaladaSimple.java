package RobotPackage;

import java.util.*;

public class EscaladaSimple extends Robot{

	public EscaladaSimple(int[][] matrizLaberinto, int _objetivoMonedas) {
		super(matrizLaberinto, _objetivoMonedas);
	}

	@Override
	public void buscarMonedas() {
		Random rand = new Random();
		boolean encontrado = false;

		List<int[]> direccion = getListaMovimientosDisponibles();

		while((direccion.size() > 0) && !encontrado) {
			this.nodosGenerados++; // estamos generando otro nodo de nuestro arbol de exploracion
			int random = rand.nextInt(direccion.size()); // genero un numero aleatorio entre entre 0 y numero de nodos a explorar desde este estado (tamaño del Array)
			
			int posX = posicionRobot[0] + direccion.get(random)[0];
			int posY = posicionRobot[1] + direccion.get(random)[1];

			if((estadoActual[posX][posY] < 7) && estadoActual[posX][posY] >= 0) {
				estadoActual[posicionRobot[0]][posicionRobot[1]] = 0; // ya lo hemos visitado, la casilla pasa a valer 0
				this.monedasConseguidas += estadoActual[posX][posY]; // le sumo las monedas de la nueva posicion
				estadoActual[posX][posY] = 8; // muevo el robot
				encontrarRobot(); // actualizo la posicion del robot
				traducirMovimiento(direccion.get(random)[0], direccion.get(random)[1]);
				encontrado = true; 
			}
			direccion.remove(random); // borramos el nodo fallido para no volver a visitarlo
		}
		
		if(encontrado == false) {
			System.out.println( "NO HAY SOLUCION POSIBLE" );
			this.haySolucion = false; // para parar el programa
		}
	}
}
