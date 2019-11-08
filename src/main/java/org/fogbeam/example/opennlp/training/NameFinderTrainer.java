
package org.fogbeam.example.opennlp.training;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Collections;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/* NOTE: The training data should contain at least 15000 sentences 
 * to create a model which performs well. */

public class NameFinderTrainer
{
	public static void main( String[] args ) throws Exception
	{
		Charset charset = Charset.forName( "UTF-8" );
		ObjectStream<String> lineStream = new PlainTextByLineStream(
				new FileInputStream( "training_data/en-ner-person.train" ), charset );
		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(
				lineStream );
		TokenNameFinderModel model;
		try
		{
			model = NameFinderME.train( "en", "person", sampleStream,
					TrainingParameters.defaultParams(), (byte[]) null,
					Collections.<String, Object> emptyMap() );
		}
		finally
		{
			sampleStream.close();
		}
		
		
		BufferedOutputStream modelOut = null;
		try
		{
			String modelFile = "models/en-ner-person.model";
			modelOut = new BufferedOutputStream( new FileOutputStream( modelFile ) );
			model.serialize( modelOut );
		}
		finally
		{
			if( modelOut != null )
			{
				modelOut.close();
			}
		}
		
		System.out.println( "done" );
	}
}
