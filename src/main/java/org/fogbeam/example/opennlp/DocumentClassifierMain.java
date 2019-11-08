package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

public class DocumentClassifierMain
{

	public static void main( String[] args ) throws Exception
	{
		
		InputStream is = null;
		try
		{
			is = new FileInputStream( "models/en-doccat.model" );
		
			DoccatModel m = new DoccatModel(is);
			
			
			String inputText = "What happens if we have declining bottom-line revenue?";
			DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
			double[] outcomes = myCategorizer.categorize(inputText);
			String category = myCategorizer.getBestCategory( outcomes );
			
			System.out.println( "Input classified as: " + category );
			
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
			
		}
		finally
		{
			if( is != null )
			{
				is.close();
			}
		}
		
		System.out.println( "done" );
	}
}
