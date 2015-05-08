import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;


public class WaitThread implements Runnable{

	
	public static File logFile;// = new File("SMIanalysis.txt");
	
	/** Constructor */
	public WaitThread() {
	}

	@Override
	public void run() {
		waitForConnection();		
	}

	/** Waiting for connection from devices */
	private void waitForConnection() {
		// retrieve the local Bluetooth device object
		LocalDevice local = null;

		StreamConnectionNotifier notifier;
		StreamConnection connection = null;

		// setup the server to listen for connection
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			
			UUID uuid = new UUID("fa87c0d0afac11de8a390800200c9a66", false);
			System.out.println(uuid.toString());

            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";

            notifier = (StreamConnectionNotifier)Connector.open(url);
            
            logFile = new File("SMIanalysis.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(WaitThread.logFile, true));
            writer.write("time1,time2,time3,total\n");
    		writer.close();
		} catch (Exception e) {
            e.printStackTrace();
            return;
        }

		// waiting for connection
		while(true) {
			try {
				System.out.println("waiting for connection...");
	            connection = notifier.acceptAndOpen();
	            System.out.println(connection);
	            Thread processThread = new Thread(new ProcessConnectionThread(connection));
	            processThread.start();
	            //return;

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
