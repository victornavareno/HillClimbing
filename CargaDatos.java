package RobotPackage;
import java.util.*;
import java.io.*;

/**
 * Esta clase se encarga de interpretar los datos proprocionados en los ficheros
 * carga el laberinto, con el robot y las monedas en una short Matriz[10][10]
 * he elegido Short como tipo de dato almacenado al ser más eficiente y rápido que almacenar Int
 */
public class CargaDatos {
	private int[][] matriz = new int[10][10]; // la matriz SIEMPRE será de 10x10 en esta versión base
	private int monedasObjetivo;
	
	public CargaDatos() {
		this.monedasObjetivo= CargarMatrizDeFichero();
	}
	
	private int CargarMatrizDeFichero()
	{
		System.out.println("\u001B[1m_______________________________");
		System.out.println();
		System.out.println("     CARGANDO FICHERO...     ");
		System.out.println("       "+Config.ficheroElegido);
		System.out.println("_______________________________");
		System.out.println();
		try {
			File archivo = new File(Config.ficheroElegido); //defino un archivo desde donde leer
			Scanner scanner = new Scanner(archivo);
			int numLinea = 0;

			//leo el valor del precio objetivo, almacenado en la primera linea del fichero:
			monedasObjetivo = (int)Short.valueOf(scanner.nextLine()); // le hago un casting a la primera linea
			System.out.println(" NUMERO DE MONEDAS OBJETIVO: " + monedasObjetivo);
			
			//a continuación, paso a leer cada linea de la matriz en el fichero
			//convierto cada carácter de esa linea a tipo Short y lo almaceno en la matriz
			String linea = scanner.nextLine();
			while(scanner.hasNextLine()) {
				for(int i = 0 ; i < 10; i++) {
					String[] numeros = linea.split(","); // almaceno los valores en un array de string[]
					matriz[numLinea][i] = (int)Short.valueOf(numeros[i]); // casting de String a int
				}
				linea = scanner.nextLine();
				numLinea++;
			} 
			scanner.close();

		} catch (Exception ex) {
			System.out.println("Ha habido un error en la carga de datos: ");
			ex.printStackTrace();
		}
		return monedasObjetivo;
	}
	
	public int[][] getMatriz(){
		return matriz;
	}
	
	public int getMonedasObjetivo() {
		return monedasObjetivo;
	}

	
	//metodo test para imprimir la matriz inicial con numeros enteros
	public void imprimirMatrizNumeros() {
		System.out.println();
		System.out.println("Esta es la matriz leida:");
		System.out.println();
		for(int i = 0; i < 10; i++) {
			System.out.print("     ");
			for (int j = 0; j < 10; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}
}
