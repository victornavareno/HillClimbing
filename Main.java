package RobotPackage;


public class Main {

	public static void main(String[] args) {
		CargaDatos testMatriz = new CargaDatos();

		EscaladaSimple testRobot = new EscaladaSimple(testMatriz.getMatriz(), testMatriz.getMonedasObjetivo());
		EscaladaMaximaPendiente testRobot2 = new EscaladaMaximaPendiente(testMatriz.getMatriz(), testMatriz.getMonedasObjetivo());

		//testMatriz.imprimirMatriz();
		System.out.println();
		double duracionAlgoritmo;

		if(Config.modoResolucion == 0) {
			duracionAlgoritmo = testRobot.encontrarSolucion();
			System.out.println(" MÉTODO UTILIZADO: ESCALADA SIMPLE");
		}

		if(Config.modoResolucion == 1) {
			duracionAlgoritmo = testRobot2.encontrarSolucion();	
			System.out.println("\u001B[1m MÉTODO UTILIZADO: ESCALADA MÁXIMA PENDIENTE");
		}

		 
	     System.out.println("  Tiempo de ejecución: " + duracionAlgoritmo + " segundos");
	}
}
