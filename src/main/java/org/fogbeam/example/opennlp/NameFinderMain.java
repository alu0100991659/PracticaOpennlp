
package org.fogbeam.example.opennlp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;


public class NameFinderMain
{
	/**
	 * @param args
	 */
	public static void main( String[] args ) throws Exception
	{
		InputStream modelIn = new FileInputStream( "models/en-ner-person.model" );
		// InputStream modelIn = new FileInputStream( "models/en-ner-person.bin" );
		
		try
		{
			TokenNameFinderModel model = new TokenNameFinderModel( modelIn );
		
			NameFinderME nameFinder = new NameFinderME(model);
			
			String[] tokens = { //"A", "guy", "named",
								// "Mr.", 
								"Phillip", 
								"Rhodes",
								"is",
								"presenting",
								"at",
								"some",
								"meeting",
								"."};
			
			Span[] names = nameFinder.find( tokens );
		
			for( Span ns : names )
			{
				System.out.println( "ns: " + ns.toString() );
			
				// if you want to actually do something with the name
				// ...
				
			}
		
			nameFinder.clearAdaptiveData();
			
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
		
		
		System.out.println( "done" );
	}
}

/* 				
				StringBuilder sb = new StringBuilder();
				for( int i = ns.getStart(); i < ns.getEnd(); i++ )
				{
					sb.append( tokens[i] + " " );
				}
				
				System.out.println( "The name is: " + sb.toString() );
 */