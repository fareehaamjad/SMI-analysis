import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.microedition.io.StreamConnection;


public class ProcessConnectionThread implements Runnable{

	BufferedWriter writer = null;
	private StreamConnection mConnection;
	
	static long startA = 0;
	static long startB = 0;
	static long b1 = 0;
	static long endA = 0;
	static long a1 = 0;
	static long endB = 0;
	
	static long time1 = 0;
	static long time2 = 0;
	static long time3 = 0;
	
	static long startEnc = 0;
	static long endEnc = 0;
	static long sentMsg = 0;
	static long receivedMsg = 0;
	static long startDec = 0;
	static long endDec = 0;
	
	/*
	 * This is for image tranfer app
	 */
	static long encryptStart = 0;// = "encryptStart";
	static long encryptEnd = 0;//  = "encryptEnd";
	static long uploadStart = 0;//  = "uploadStart";
	static long uploadEnd = 0;//  = "uploadEnd";
	static long sendNoce = 0;//  = "sendNonce";
	static long receiveNonce = 0;//  = "receiveNonce";
	static long downloadStart = 0;//  = "downloadStart";
	static long downloadEnd = 0;//  = "downloadEnd";

	static long timeToEncrypt = 0;
	static long timeToUpload = 0;
	static long timeToSendNonce = 0;
	static long timeToDownload = 0;

	// Constant that indicate command from devices
	public ProcessConnectionThread(StreamConnection connection)
	{
		mConnection = connection;
	}

	@Override
	public void run() 
	{
		try {

			// prepare to receive data
			InputStream inputStream = mConnection.openInputStream();

			OutputStream outStream=mConnection.openOutputStream();
			
			System.out.println("waiting for input.............");
        	
	      while(true)
	      {
	        	byte[] b = new byte[100];
	        
	        	BufferedReader bR= new BufferedReader (new InputStreamReader (inputStream));
	        	       	
	        	String h= bR.readLine();
	        	System.out.println("Message Received from : " +mConnection.toString() +"\t" +h);
	        	
	        	if (h.equals(Constants.startA))
	        	{
	        		startA = System.nanoTime();
	        		//System.out.println("Got startA");
	        	}
	        	else if (h.equals(Constants.startB))
	    		{
	    			startB = System.nanoTime();
	    			//System.out.println("Got startB");
	    		}
	    		else if (h.equals(Constants.b1))
	    		{
	    			b1 = System.nanoTime();
	    			//System.out.println("Got b1");
	    		}
	    		else if (h.equals(Constants.endA))
	    		{
	    			endA = System.nanoTime();
	    			//System.out.println("Got endA");
	    		}
	    		else if (h.equals(Constants.a1))
	    		{
	    			a1 = System.nanoTime();
	    			//System.out.println("Got a1");
	    		}
	    		else if (h.equals(Constants.endB))
	    		{
	    			endB = System.nanoTime();
	    			//System.out.println("Got endB");
	    			
	    			
	    			//calculateTime();
	    		}
	    		else if (h.equals(Constants.startEnc))
	    		{
	    			startEnc = System.nanoTime();
	    			//System.out.println("Got a1");
	    		}
	        	if (h.equals(Constants.endEnc))
	    		{
	        		endEnc = System.nanoTime();
	    			//System.out.println("Got a1");
	    		}
	        	if (h.equals(Constants.sentMsg))
	    		{
	        		sentMsg = System.nanoTime();
	    			//System.out.println("Got a1");
	    		}
	        	if (h.equals(Constants.receivedMsg))
	    		{
	        		receivedMsg = System.nanoTime();
	    			//System.out.println("Got a1");
	    		}
	        	if (h.equals(Constants.startDec))
	    		{
	        		startDec = System.nanoTime();
	    			//System.out.println("Got a1");
	    		}
	        	if (h.equals(Constants.endDec))
	    		{
	        		endDec = System.nanoTime();
	        		
	        		calculateTime();
	    			//System.out.println("Got a1");
	    		}
	    		else if (h.equals(Constants.encryptStart))
	    		{
	    			encryptStart = System.nanoTime();
	    		}
	    		else if (h.equals(Constants.encryptEnd))
	    		{
	    			encryptEnd = System.nanoTime();
	    		}
	    		else if (h.equals(Constants.uploadStart))
	    		{
	    			uploadStart = System.nanoTime();
	    		}
	    		else if (h.equals(Constants.uploadEnd))
	    		{
	    			uploadEnd = System.nanoTime();
	    		}
	    		else if (h.equals(Constants.sendNoce))
	    		{
	    			sendNoce = System.nanoTime();
	    		}
	    		else if (h.equals(Constants.receiveNonce))
	    		{
	    			receiveNonce = System.nanoTime();
	    			
	    		}
	    		else if (h.equals(Constants.downloadStart))
	    		{
	    			downloadStart = System.nanoTime();
	    		}
	    		else if (h.equals(Constants.downloadEnd))
	    		{
	    			downloadEnd = System.nanoTime();
	    			
	    			calculateTimeForImageTransfer();
	    		}
	        	
	    		
	    		
	        	//System.out.println("Text sending: " + WalletPoint.encoded);
	            // PrintWriter pWriter=new PrintWriter(new OutputStreamWriter(outStream));
	            // pWriter.write(WalletPoint.encoded);
	             //pWriter.write(h +"\n");
	             //pWriter.flush();
	      }
	             //outStream.close();

        } catch (Exception e) {
    		e.printStackTrace();
    	}
	}

	private void calculateTimeForImageTransfer() throws IOException 
	{
		timeToEncrypt = encryptEnd - encryptStart;
		timeToUpload = uploadEnd - uploadStart;
		timeToSendNonce = receiveNonce - sendNoce;
		timeToDownload = downloadEnd - downloadStart;
		
		
		long total = timeToEncrypt + timeToUpload + timeToSendNonce + timeToDownload;
		
		writer = new BufferedWriter(new FileWriter(WaitThread.logFile, true));
		writer.write(timeToEncrypt + "," + timeToUpload + "," + timeToSendNonce + "," + timeToDownload + "," + total + "\n");
		writer.close();
	}

	private void calculateTime() throws IOException 
	{
		/*time1 = startB - startA;
		time2 = endA - b1;
		time3 = endB - a1;
		
		long total = time1 + time2 + time3;
		
		writer = new BufferedWriter(new FileWriter(WaitThread.logFile, true));
		writer.write(time1 + "," + time2 + "," + time3 + "," + total + "\n");
		writer.close();
		System.out.println("time1:" + time1);
		System.out.println("time2:" + time2);
		System.out.println("time3:" + time3);
		System.out.println("total time:" + total);
		System.out.println("_____________________________________");*/
		
		long timeToEncrypt = endEnc - startEnc;
		long timeToSendMsg = receivedMsg - sentMsg;
		long timeToDecrypt = endDec - startDec;
		
		long total = timeToEncrypt + timeToSendMsg + timeToDecrypt;
		
		writer = new BufferedWriter(new FileWriter(WaitThread.logFile, true));
		writer.write(timeToEncrypt + "," + timeToSendMsg + "," + timeToDecrypt + "," + total + "\n");
		writer.close();
		
		
		
	}

}
