package assignment8server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Server extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ServerSocket serverSocket;
	static Server server;

    // GUI of server
    private JTextArea textArea = new JTextArea();

    // Constructor
    public Server(int portNum) {
        // set up of server's frame
    	this.setSize(400, 200);
		//this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialize textArea
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		//this.textArea.setPreferredSize(new Dimension(366,300));
		//不設定textArea的大小才能達到自動調整大小的效果（超過螢幕自動增加textArea行數）
		textArea.setBounds(0, 0 , 366, 300);
		JScrollPane scrollPane = new JScrollPane(this.textArea);
	    this.add(scrollPane);	    
	    	    
	    // Ask for nickname before showing client window
	    //this.nickname = JOptionPane.showInputDialog("Nickname", "Unknown");
		//this.welcome(this.nickname);
		
		this.setVisible(true);
    	
     

        // create server's socket (set port)
        try {

            this.serverSocket = new ServerSocket(portNum);
            InetAddress IP = InetAddress . getLocalHost();
            this.textArea.append("Server starts listening on IP " + IP + ".\n");
            this.textArea.append("Server starts listening on port " + portNum + ".\n");

        } catch (IOException e) {

        	this.textArea.append("Fail to create server's socket!");
            e.printStackTrace();
            System.exit(0);

        }

        // server's content field when working
        textArea.setEditable(false);
        textArea.setBounds(0, 0, 366, 300);    

    }
    
    public void runForever() {
		
		this.textArea.append("Server starts waiting for client.\n");
			
		// try to accept connect request of client and create connection channel
		try {
			
			Socket connectionToClient = this.serverSocket.accept();
			this.textArea.append("Server is connet!\n");
			ConnectionThread child = new ConnectionThread(connectionToClient, server);
			child.start();
			
		} catch (IOException e) {
			
			//e.printStackTrace();
			
		}
		
	}
    
    class ConnectionThread extends Thread {
		
		// client's socket and thread
		Socket socket;
		Server server;
		Thread thread = new Thread();
		
		// I/O of client
		BufferedReader reader;
		
		// information of player
		String num1,num2;
		String oper;
		String result;
		
		// Constructor: connect socket and reader/writer
		public ConnectionThread(Socket socket,Server server){
			
			// TODO Auto-generated constructor stub
			this.socket = socket;
			this.server = server;
			
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				
				System.out.println("Fail to establish I/O channel between server and client!");
				e.printStackTrace();
				
			}
			
		}

		
		// the program process of connection thread
		@Override
		public void run() {
			while (true) {//不斷讀入訊息 並執行對應指令
				try {
					num1 = reader.readLine();
					num2 = reader.readLine();
					oper = reader.readLine();
					result = reader.readLine();
					
					server.textArea.append("The result from App is "+num1+" "+oper+" "+num2+" "+"="+" "+result+"\n");					
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
    
    public static void main(String[] args) {

        // create server
        server = new Server(8000);
        //server.loadWindow.runFrame();
        server.runForever();

    }
}
