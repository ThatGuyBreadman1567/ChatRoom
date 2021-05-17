import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatRoomServer 
{
	
	private static int numclients = 0;

	public static void main(String[] args) throws IOException 
	{
		int port = 9999;
		ServerSocket server = new ServerSocket(port);
		System.out.println("clients: " + numclients);
		
		while(true)//for(;;)
		{
			try
			{
				
				System.out.println("Server listening...");
				Socket client = server.accept();
				System.out.println("server accepted client");
				
				Handler clientThread = new Handler(client);
				new Thread(clientThread).start();
				add_client();
				
				
				
			}
			catch(Exception e)
			{
				System.out.println("there was an issue");
			}
		}

	}
	
	public static void add_client()
	{
		System.out.println("clients: "+ (++numclients));
	}

	public static void remove_client()
	{
		System.out.println("clients: "+ (--numclients));
	}
}

class Handler implements Runnable
{
	private Socket client;
	private String name;
	
	public Handler(Socket s)
	{
		client = s;
	}
	
	public void run() 
	{
		try
		{
					
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(),true);
			
			String message;
			
			name =in.readLine();
			
						
			while((message = in.readLine()) != null)
			{
				System.out.println(name +": " + message);
				out.println(name + ": " + message);
			}
			client.close();
			ChatRoomServer.remove_client();
		}
		catch(Exception e)
		{
			System.out.println("there was an issue");
		}
		
	}
	
}

