
/*This class help to us run project easily all you need is to run this server
and that's all server anc clients will work perfectly */
public class Main {
  public static void main(String[] args){
    IDGenerator idGen = new IDGenerator();
    try{
      Thread server = new Thread(new Server());
      Thread thread1 = new Thread(new Client(idGen));
      Thread thread2 = new Thread(new Client(idGen));
      Thread thread3 = new Thread(new Client(idGen));
      Thread thread4 = new Thread(new Client(idGen));
      Thread thread5 = new Thread(new Client(idGen));
      server.start();
      thread1.start();
      thread2.start();
      thread3.start();
      thread4.start();
      thread5.start();
      thread1.join();
      thread2.join();
      thread3.join();
      thread4.join();
      thread5.join();

    }catch(InterruptedException e){e.printStackTrace();}
  }     
}
