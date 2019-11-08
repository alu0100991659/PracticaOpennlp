
package org.fogbeam.example.opennlp.training;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import opennlp.tools.tokenize.TokenSample;
import opennlp.tools.tokenize.TokenSampleStream;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;


public class TokenizerTrainer
{
	public static void main( String[] args ) throws Exception
	{
		Charset charset = Charset.forName( "UTF-8" );
		ObjectStream<String> lineStream = new PlainTextByLineStream(
				new FileInputStream( "training_data/en-token.train" ), charset );
		
		ObjectStream<TokenSample> sampleStream = new TokenSampleStream( lineStream );
		
		TokenizerModel model;
		
		try
		{
			model = TokenizerME.train( "en", sampleStream, true, TrainingParameters.defaultParams() );
		}
		finally
		{
			sampleStream.close();
		}
		
		OutputStream modelOut = null;
		try
		{
			modelOut = new BufferedOutputStream( new FileOutputStream( "models/en-token.model" ) );
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
