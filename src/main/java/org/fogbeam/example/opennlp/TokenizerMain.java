
package org.fogbeam.example.opennlp;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.*;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import java.nio.file.*;


public class TokenizerMain
{
	public static void main( String[] args ) throws Exception
	{
		
		// the provided model
		// InputStream modelIn = new FileInputStream( "models/en-token.bin" );
 
		// the model we trained
		InputStream modelIn = new FileInputStream( "models/en-token.model" );
		
		try
		{
			TokenizerModel model = new TokenizerModel( modelIn );
			
			Tokenizer tokenizer = new TokenizerME(model);
			
			/* 
			 * Creo una lista con todos los archivos del diretorio donde
			 * se encuentran los ficheros de entrada
			 */
			Stream<Path> walk = Files.walk(Paths.get(args[0]));
			List<String> inputFiles = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());
			walk.close();
			
			/*
			 * Abro el fichero en dónde voy a imprimir los resultados de 
			 * la tokenización
			 */
			FileOutputStream outputStream = new FileOutputStream(args[1]);
			
			/* Recorro cada archivo de entrada */
			for (int i = 0; i < inputFiles.size(); i++) {
				
				/* Guardo en un string el contenido del archivo actual */
				String currentString = new String(Files.readAllBytes(Paths.get(inputFiles.get(i))));
				
				/* Tokenizo el string con el contenido del archivo actual */
				String[] tokens = tokenizer.tokenize(currentString);
				
				/* Escribo una cabecera con el nombre de cada archivo */
				byte [] strHeader = ("------------------------- Tokens of file: "+inputFiles.get(i)+"\n").getBytes();
				outputStream.write(strHeader);
				
				/* Imprimo en el archivo de salida todos los tokens de la tokenizacion */
				for( String token : tokens )
				{
					byte[] strToBytes = (token+"\n").getBytes();
					outputStream.write(strToBytes);
				}
			}
			outputStream.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			if( modelIn != null )
			{
				try
				{
					modelIn.close();
				}
				catch( IOException e )
				{
				}
			}
		}
		System.out.println( "\n-----\ndone" );
	}
}
