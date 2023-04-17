package RobotPackage;


public class Main {

	public static void main(String[] args) {
		CargaDatos laberinto = new CargaDatos();
		EscaladaSimple EscaladaSimple = new EscaladaSimple(laberinto.getMatriz(), laberinto.getMonedasObjetivo());
		EscaladaMaximaPendiente EscaladaMaximaPendiente = new EscaladaMaximaPendiente(laberinto.getMatriz(), laberinto.getMonedasObjetivo());

		System.out.println();
		double duracionAlgoritmo;
		
		// EN LA CLASE Config.java, MODIFICAR PARAMETROS PARA ELEGIR ESTRATEGIA DE RESOLUCION
		if(Config.modoResolucion == 0) {
			duracionAlgoritmo = EscaladaSimple.encontrarSolucion();
			System.out.println("\u001B[1m    METODO UTILIZADO: ESCALADA SIMPLE");
		}

		if(Config.modoResolucion == 1) {
			System.out.println();

			duracionAlgoritmo = EscaladaMaximaPendiente.encontrarSolucion();	
			
			System.out.println("\u001B[1m METODO UTILIZADO: ESCALADA MAXIMA PENDIENTE");
		}

		 
	     System.out.println("   Tiempo de ejecucion: " + duracionAlgoritmo + " segundos");
	}
}
