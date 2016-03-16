/*
* @author Jose Antonio Adriano Muñoz
* @version 1.0
*/


import java.net.*;
import java.io.*;

public class FTP 
	implements Runnable
{


  private URLConnection cx;
  private String remoteFile;
  private String erMesg;
  private String succMesg;
  
  
	// atributos privados de la case
	private String servidor;
	private String usuario;
	private String clave;
	private CString p= new CString();
	
	// constructor nulo
	public FTP () {}
	
	// constructor sobrecargado
	public FTP ( String servidor, String usuario, String clave ) {  
		this.servidor= servidor;
		this.usuario= usuario;
		this.clave= clave;
	}
	
	// metodo run
	public void run () {
	p.escribir("Run..");
		if (conectar()) listar();
	desconectar();	
	}
	
	// metodo para realizar la conexion al sitio ftp
	public boolean conectar () {
	p.escribir("Conectando..");
		try {
		URL url = new URL("ftp://"+this.usuario+":"+this.clave+"@"+this.servidor+"/new/"+remoteFile+";type=i");
		cx = url.openConnection();
		cx.connect();
		return true;
		} catch(Exception ex) {
		StringWriter sw0= new StringWriter ();
		PrintWriter p0= new PrintWriter ( sw0, true );
		ex.printStackTrace ( p0 );
		erMesg = sw0.getBuffer().toString ();
		return false;
		}
	}
	
	// metodo para realizar la desconexion del sitio ftp
	public void desconectar () {
	p.escribir("Desconectado..");
	//cx.logout();
	//cx.disconnect();
	}
	
	// metodo para listar los ficheros del sitio
	public String[] listar () {
	String[] enlaces= { "" };
	p.escribir("Listando elementos..");
	try {
	cx.getContent();
	} catch( IOException e ) {}
	return enlaces;
	}
	
	// metodo para entrar en un directorio del sitio
	public void cd () {
	p.escribir("Cambiando directorio..");
	}
	
	// metodo para descargar un fichero del sitio
	public void descargar () {
	p.escribir("Descargando..");
	}
	
	// metodo para subir un fichero al sitio
	public void subir () {
	p.escribir("Subiendo..");
	}


	public synchronized String getLastSuccessMessage()
	{
		if (succMesg==null ) return ""; return succMesg;
	}
  
	public synchronized String getLastErrorMessage()
	{
		if (erMesg==null ) return ""; return erMesg;
	}
    
	public synchronized boolean uploadFile (String localfilename)
	{
		try{
		InputStream is = new FileInputStream(localfilename);
		BufferedInputStream bis = new BufferedInputStream(is);
		OutputStream os =cx.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		byte[] buffer = new byte[1024];
		int readCount;

		while( (readCount = bis.read(buffer)) > 0)
		{
			bos.write(buffer, 0, readCount);
		}
		bos.close();
		this.succMesg = "Uploaded!";
		return true;
		}
		catch(Exception ex)
		{
		StringWriter sw0= new StringWriter ();
		PrintWriter p0= new PrintWriter ( sw0, true );
		ex.printStackTrace ( p0 );
		erMesg = sw0.getBuffer().toString ();
		return false;
		}
	}
  
	public synchronized boolean downloadFile (String localfilename)
	{
		try{
		InputStream is = cx.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		OutputStream os = new FileOutputStream(localfilename);
		BufferedOutputStream bos = new BufferedOutputStream(os);

		byte[] buffer = new byte[1024];
		int readCount;

		while( (readCount = bis.read(buffer)) > 0)
		{
			bos.write(buffer, 0, readCount);
		}
		bos.close();
		is.close ();
		this.succMesg = "Downloaded!";
		return true;
		}catch(Exception ex)
		{
		StringWriter sw0= new StringWriter ();
		PrintWriter p0= new PrintWriter ( sw0, true );
		ex.printStackTrace ( p0 );
		erMesg = sw0.getBuffer().toString ();
		return false;
		}
	}
	
}