package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSocket {
	
	static public class SocketReceivePrinter implements Runnable
    {
        private Socket clientSocket;
        
        public SocketReceivePrinter( Socket clientSocket )
        {
            this.clientSocket = clientSocket;
        }
        
        @Override
        public void run()
        {
        	DataInputStream input = null;
        	try {
				input = new DataInputStream( clientSocket.getInputStream() );
	            String receive = null;
	            while ( this.clientSocket!=null && !this.clientSocket.isClosed()) 
	            {
	            	String receiving=null;
	            	try {
	            		receiving = input.readUTF();
	            	}catch(SocketException e) {
	            		return;
	            	}catch(java.io.EOFException e) {
	            		return;
	            	}
	                System.out.println( "You're receiving from server: "+receiving );
	                System.out.flush();
	            }
			} catch (IOException e) {
				e.printStackTrace();
			} 
            
        }
    }

    public static void main( String[] args ) throws IOException
    {
        String host = "";
        int port = 5987;
        Socket socket = null;
        Scanner consoleInput = new Scanner( System.in );
        System.out.println("Please type in the server you want to connect to (e.g. localhost):");
        host = consoleInput.nextLine();
        if("".equals(host)) {
        	host="localhost";
        }
        try
        {
            socket = new Socket( host, port );
            ExecutorService threadExecutor = Executors.newCachedThreadPool();
            threadExecutor.execute(new SocketReceivePrinter(socket));
            DataOutputStream out  = new DataOutputStream( socket.getOutputStream() );;
            try
            {
                while ( true) 
                {
                	if(consoleInput.hasNextLine()) {
	                	String send = consoleInput.nextLine();
	                    System.out.println( "You're sending to server: "+send );
	                    out.writeUTF(send);
	                    out.flush();
	                    if("close".equals(send)) {
	                    	System.out.println("Server now might closing the socket");
	                    	try {
	                    		while(!socket.isClosed())
	                    			Thread.sleep(1000); // wait until server close the socket.
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	                    	break;
	                    }
                	}
                }
            }
            finally 
            {
                if ( consoleInput != null )
                	consoleInput.close();
                if(out!=null)out.close();
              
            }
        }
        catch ( IOException e )
        {
        	System.out.println("Oops, it seems the connection between you and "+host+" cannot be built...");
            e.printStackTrace();
        }
        finally
        {
            if ( socket != null )
                socket.close();
            if ( consoleInput != null )
                consoleInput.close();
        }
        System.out.println("Client Finished...");
    }
    
}
