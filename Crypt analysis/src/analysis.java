
public class analysis 
{
	public static void main(String[] args) 
	{
		Thread waitThread = new Thread(new WaitThread());
		waitThread.start();
		
	}


}
