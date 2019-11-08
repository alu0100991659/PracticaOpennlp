
package org.fogbeam.example.opennlp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;


public class PartOfSpeechTaggerMain
{
	public static void main( String[] args )
	{
		InputStream modelIn = null;
		try
		{
			// modelIn = new FileInputStream( "models/en-pos.model" );
			modelIn = new FileInputStream( "models/en-pos-maxent.bin" );
			
			POSModel model = new POSModel( modelIn );
			
			
			POSTaggerME tagger = new POSTaggerME(model);
			
			String sent[] = new String[]{"Most", "large", "cities", "in", "the", "US", "had",
                    "morning", "and", "afternoon", "newspapers", "."};		  
			String tags[] = tagger.tag(sent);
			
			double probs[] = tagger.probs();
			
			
			for( int i = 0; i < sent.length; i++ )
			{
				System.out.println( "Token [" + sent[i] + "] has POS [" + tags[i] + "] with probability = " + probs[i] );
			}
			
			
		}
		catch( IOException e )
		{
			// Model loading failed, handle the error
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
