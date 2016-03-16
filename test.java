/*
* @author Jose Antonio Adriano Muñoz
* @version 1.0
*/


import java.util.concurrent.*;

public class test 
{


	// metodo main
	public static void main ( String[] args ) 
		throws InterruptedException 
	{
	String servidor, usuario, clave;
		servidor= "miservidor.com";
		usuario= "usuarioservidor"; 
		clave= "claveusuario";
		int nNuc = Runtime.getRuntime().availableProcessors(); // numero de nucleos de la cpu
		float Cb = (float)-3; // coeficiente de bloqueo
		int tamPool = (int)(nNuc/(1-Cb)); // ecuacion de subramanian
		ThreadPoolExecutor ejecutor = new ThreadPoolExecutor( tamPool, tamPool, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		FTP cx= new FTP(servidor, usuario, clave);
		
		while(salir()){
			ejecutor.execute(new FTP()); // creamos el monitor
			Thread.sleep(1000);
		}
		
		ejecutor.shutdown(); 
		while(!ejecutor.isTerminated()){}
		
	}
	
	public static boolean salir() {
	return true;
	}
	
}