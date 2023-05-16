import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*This class represent client. When we demonstrate our work we won't able to find 40 device 
for 40 students that's why we write Client class as thread to simulate 40 different device. 
It has a GUI object for user interaction and utilizes an IDGenerator object to create 
unique client IDs.*/

public class Client implements Runnable{
  private int ID; // private ID for each Client
  private IDGenerator idGen; //private IDGenerator class help to us generate different IDs for Clients 
  private GUI myGui; //private GUI class for user interaction
  public synchronized GUI GetGui(){return myGui;} // getter for myGui
  public synchronized void SetGui(GUI myGUI){myGui = myGUI;} // setter for myGui 

  //Constructor for the Client class. It initializes the IDGenerator instance variable.
  public Client(IDGenerator idGenerator){synchronized(this){ idGen = idGenerator;}}

  /*This method sends a message to the server and it is thread safe. 
  It takes an array of OPTIONS and a message as parameters. If the message is "send", 
  it constructs an Object array containing the client ID, the OPTIONS array, and the message, 
  and sends it to the server using an ObjectOutputStream. If the message is "request",
  it constructs an Object array containing the client ID and the message, sends it 
  to the server using an ObjectOutputStream, and reads the response from the server 
  using an ObjectInputStream. */
  public synchronized void SendMessageToServer(OPTIONS[] arr, String message){
  try{
    Socket socket = new Socket("localhost", 2000); // Socket for Client to connect server.
    // output stream of server
    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
    /*if client want to send its own destinations to server message will be send string and this 
    if statemnt will be execute */
    if(message == "send"){
      //Object will store Client ID, destination preferences, and message which is send or request
      Object[] data = {ID,arr,message};
      outputStream.writeObject(data); //write all data to server's outputstream. 
      outputStream.close();
      socket.close();
    }
    /*if client want to request its own destination from server this else if statemnt 
    will be execute */    
    else if(message == "request"){
      //Object will store Client ID and message which is send or request
      Object[] data = {ID,message};
      outputStream.writeObject(data); //write all data to server's outputstream.
      // input stream of server. It will help us to get data from server.
      ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
      // read data object from server's input stream.
      Object inputData = (Object)inputStream.readObject();
      myGui.SetResultText((String)inputData); //Set students final destination on GUI.
      inputStream.close();
      outputStream.close();
      socket.close();
    }
    /*if there any problem with message argument this else statemnt will be execute */
    else{ 
      outputStream.close();
      socket.close();
      return;
    }
  } catch(IOException e){e.printStackTrace();}catch(ClassNotFoundException e){e.printStackTrace();}
  }

  // Thread's run method 
  public synchronized void run(){
    ID = idGen.GenerateID(); // Generate unique ID for client
    myGui = new GUI("Student"+ID,this); // instantiate GUI for client to interact.
  }
  
}
