/*This class help us to generate ID for each client in our case for each student  */
public class IDGenerator {
  public int ID;

  public IDGenerator(){
    ID = 0;
  }
  //synhronized help us to make this method thread safe. 
  public synchronized int GenerateID(){
    ID++;
    return ID;
  }
}