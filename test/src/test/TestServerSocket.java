package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestServerSocket {
    public static final int LISTEN_PORT = 5987;
    
    public void listenRequest()
    {
        ServerSocket serverSocket = null;
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        try
        {
            serverSocket = new ServerSocket( LISTEN_PORT );
            System.out.println("Server listening requests...");
            while ( true )
            {
                Socket socket = serverSocket.accept(); // blocker
                System.out.println("I got one request!!!");
                threadExecutor.execute( new RequestThread( socket ) );
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( threadExecutor != null )
                threadExecutor.shutdown();
            if ( serverSocket != null )
                try
                {
                    serverSocket.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
        }
    }
    
    /**
     * @param args
     */
    public static void main( String[] args )
    {
    	TestServerSocket server = new TestServerSocket();
        server.listenRequest();
    }
    
    /**
     * The thread that response to Client side Request, i.e. dedicating to dealing with a socket IO.
     *
     * @version
     */
    class RequestThread implements Runnable
    {
        private Socket clientSocket;
        
        public RequestThread( Socket clientSocket )
        {
            this.clientSocket = clientSocket;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run()
        {
            System.out.printf("%s connection comes in!\n", clientSocket.getRemoteSocketAddress() );
            DataInputStream input = null;
            DataOutputStream output = null;
            
            try
            {
                input = new DataInputStream( this.clientSocket.getInputStream() );
                output = new DataOutputStream( this.clientSocket.getOutputStream() );
                SocketAddress remoteAddress = clientSocket.getRemoteSocketAddress();
                output.writeUTF( String.format("Hi, %s!\n", remoteAddress.toString()) );
                while ( true )
                {
                	String line = input.readUTF();
                	if(line!=null) {
                		System.out.println("From "+remoteAddress+": "+line);
                		output.writeUTF("We've receive from you("+remoteAddress+") : "+line);
                        if(line.indexOf("close")>=0) { // matches "close"
                        	System.out.println("It's a close message. Bye bye!!\n");
                        	output.writeUTF("It's a close message. Bye bye!!\n");
                        	break;
                        }
                        output.flush();
                	}
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            finally 
            {
                try
                {
                    if ( input != null )
                        input.close();
                    if ( output != null )
                        output.close();
                    if ( this.clientSocket != null && !this.clientSocket.isClosed() )
                        this.clientSocket.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
