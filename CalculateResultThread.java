/*This class help to us calculate students final destionation. It is thread, because we want
from server to listen all sockets and calculate at same time results. For not to slow down server 
main thread we used another child thread */  

public class CalculateResultThread implements Runnable{ 
  private Server _server; // declare private Server object
  private DataOfStudents _data; // declare private DataOfStudents object

  //this construct of class define _server and _data object
  public CalculateResultThread(Server server, DataOfStudents data){
    _server = server;
    _data = data;
  }

  // Thread's run method 
  public void run(){
    _server.CalculateAllResults(_data, false);
  }
}