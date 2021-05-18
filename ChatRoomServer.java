import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoomServer 
{
	
	private static int numclients = 0;
	private static ArrayList<Socket> clients = new ArrayList<Socket>();

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
				add_client(client);
				
				
				
			}
			catch(Exception e)
			{
				System.out.println("there was an issue");
			}
		}

	}
	
	public static void add_client(Socket client)
	{
		System.out.println("clients: "+ (++numclients));
		clients.add(client);
		for(Socket a : clients)
		{
			System.out.println(a.toString());
		}
	}

	public static void remove_client(Socket client)
	{
		System.out.println("clients: "+ (--numclients));
		clients.remove(client);
		for(Socket a : clients)
		{
			System.out.println(a.toString());
		}
	}
	
	public static ArrayList<Socket> getClients()
	{
		return clients;
	}
}

class Handler implements Runnable
{
	private Socket client;
	private String name;
	private ArrayList<Socket> clients = ChatRoomServer.getClients();
	
	public Handler(Socket s)
	{
		client = s;
	}
	
	public void run() 
	{
		try
		{
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			String message;
			
			name =in.readLine();
			
						
			while((message = in.readLine()) != null)
			{
				System.out.println(name +": " + message);
				for(Socket a : clients)
				{
					PrintWriter out = new PrintWriter(a.getOutputStream(),true);
					out.println(name + ": " + message);
				}
			}
			client.close();
			ChatRoomServer.remove_client(client);
		}
		catch(Exception e)
		{
			System.out.println("there was an issue");
		}
		
	}
	
}

