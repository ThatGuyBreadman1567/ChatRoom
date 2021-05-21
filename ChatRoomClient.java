import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatRoomClient 
{
	
	private static int numclients = 0;

	public static void main(String[] args) throws IOException 
	{
		int port = 9999;
		Socket client = new Socket("127.0.0.1", port);
		
		while(true)//for(;;)
		{
			try
			{
				PrintWriter out = new PrintWriter(client.getOutputStream(),true);
				
				Scanner keyboard = new Scanner(System.in);
				String input;
				System.out.print("Name: ");
				input = keyboard.nextLine();
				out.println(input);
				while(!(input = keyboard.nextLine()).equals("quit"))
				{
					out.println(input);
					String response;
				}
				
				Handler clientThread = new Handler(client);
				new Thread(clientThread).start();
			
			}
			catch(Exception e)
			{
				System.out.println("there was an issue");
			}
		}
	}
}

class Handler implements Runnable
{
	private Socket server;
	private String name;
	
	public Handler(Socket s)
	{
		server = s;
	}
	
	@Override
	public void run() 
	{
		try
		{
					
			
			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			
			String response;
			
			while(!(response = in.readLine()).equals("quit"))
			{
				System.out.println("Message Recieved: " + response);
			}
			server.close();			
		}
		catch(Exception e)
		{
			System.out.println("there was an issue");
		}
		
	}
	
}

