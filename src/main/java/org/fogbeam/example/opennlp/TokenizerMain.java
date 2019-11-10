
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

/// Clase Tokenizer
/** Clase para tokenizar una serie de archivos de entrada y exportar el resultado en un fichero de salida. */
public class TokenizerMain
{
	public static void main( String[] args ) throws Exception
	{
		
		// the provided model
		// InputStream modelIn = new FileInputStream( "models/en-token.bin" );
 
		// the model we trained
		InputStream modelIn = new FileInputStream( "models/en-token.model" ); ///< Modelo entrenado
		FileOutputStream outputStream = null;
		
		try
		{
			TokenizerModel model = new TokenizerModel( modelIn ); ///< Modelo del tokenizador
			
			Tokenizer tokenizer = new TokenizerME(model); ///< Tokenizador
			
			/* 
			 * Creo una lista con todos los archivos del diretorio donde
			 * se encuentran los ficheros de entrada
			 */
			Stream<Path> walk = Files.walk(Paths.get(args[0])); ///< Stream con las path del directorio de entrada
			List<String> inputFiles = walk.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList()); ///< Lista con los ficheros de entrada
			walk.close();
			
			/*
			 * Abro el fichero en dónde voy a imprimir los resultados de 
			 * la tokenización
			 */
			outputStream = new FileOutputStream(args[1]); ///< Fichero de salida
			
			/* Recorro cada archivo de entrada */
			for (int i = 0; i < inputFiles.size(); i++) {
				
				String currentString = new String(Files.readAllBytes(Paths.get(inputFiles.get(i)))); ///< Fichero actual a tokenizar
				
				String[] tokens = tokenizer.tokenize(currentString); ///< Array con los tokens del fichero de entrada
				
				byte [] strHeader = ("------------------------- Tokens of file: "+inputFiles.get(i)+"\n").getBytes(); ///< Cabecera de cada archivo
				outputStream.write(strHeader);
				
				/* Imprimo en el archivo de salida todos los tokens de la tokenizacion */
				for( String token : tokens )
				{
					byte[] strToBytes = (token+"\n").getBytes();
					outputStream.write(strToBytes);
				}
			}
			
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		finally
		{
			if(outputStream != null)
				outputStream.close();
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
