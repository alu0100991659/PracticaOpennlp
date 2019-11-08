
package org.fogbeam.example.opennlp.training;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import opennlp.tools.chunker.ChunkSample;
import opennlp.tools.chunker.ChunkSampleStream;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.chunker.DefaultChunkerContextGenerator;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;


public class ChunkerTrainer
{
	public static void main( String[] args ) throws Exception
	{
		Charset charset = Charset.forName( "UTF-8" );
		
		// without enough training data, you get a NPE...
		// ObjectStream<String> lineStream = new PlainTextByLineStream(
		//		new FileInputStream( "training_data/en-chunker.train" ), charset );
		
		
		ObjectStream<String> lineStream = new PlainTextByLineStream(
				new FileInputStream( "training_data/conll2000-chunker.train" ), charset );
		
		
		ObjectStream<ChunkSample> sampleStream = new ChunkSampleStream(
				lineStream );
		ChunkerModel model;
		try
		{
			model = ChunkerME.train( "en", sampleStream,
					new DefaultChunkerContextGenerator(),
					TrainingParameters.defaultParams() );
		}
		finally
		{
			sampleStream.close();
		}
		OutputStream modelOut = null;
		String modelFile = "models/en-chunker.model";
		try
		{
			modelOut = new BufferedOutputStream( new FileOutputStream(
					modelFile ) );
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
