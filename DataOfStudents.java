import java.util.Arrays;

/*This class help us to handle students data which is selected destinations of students, 
IDs of students and their index of result destination.  class has an array that can hold 
data for a certain number of students. When new data is added, the class checks if 
there is an existing entry for that student and updates it. Otherwise, it adds the new data 
to the end of the array, as long as there is still space left.*/

public class DataOfStudents {
  public Object[][] mainArray;  // 2D object array that holds student IDs and their option arrays
  
  // integer array that holds students reult destination index of option array  
  public int[] tiersOfMainArray;
  private int size,k; // size of the main array and counter for the new entries
  private boolean isNew; // boolean to check if a new entry was added

  // Constructor to initialize the main array and tier array
  public DataOfStudents(int SIZE){
    size = SIZE;
    mainArray = new Object[size][2];
    tiersOfMainArray = new int[size];
    k = 0;
    isNew = false;
  }

  // Constructor to copy existing DataOfStudents object.
  public DataOfStudents(DataOfStudents data){
    size = data.Length();
    mainArray = new Object[size][2];
    tiersOfMainArray = new int[size];
    k = 0;
    isNew = false;
    for(int i = 0; i < size;i++){
      mainArray[i][0] = data.mainArray[i][0];
      mainArray[i][1] = data.mainArray[i][1];
      tiersOfMainArray[i] = data.tiersOfMainArray[i];
    }
  }

  public int Length(){return size;} // return mainArray's size

  /* Adds a new student and their option array to the main array, 
  or updates their existing array if already present*/
  public synchronized void Add(int ID,OPTIONS[] optionsArray){
    for(int i = 0; i < size; i++){
      if(mainArray[i][0] != null && (int)mainArray[i][0] == ID){
        mainArray[i][1] = optionsArray;
        isNew = true;
        break;
      }
    }
    if(!isNew && k < 40){
      mainArray[k][0] = ID;
      mainArray[k][1] = optionsArray;
      k++;
    }
  }

  // Prints array to terminal.
  public void showArray(){
    for(int i = 0; i < size; i++){
      if(i == 0) System.out.println("--------------------------------------------------------------------");
      if(mainArray[i][0] != null)
      System.out.println("ID: "+(int)mainArray[i][0]+"; Array: "+Arrays.toString((OPTIONS[])mainArray[i][1])+"; Tier: "+tiersOfMainArray[i]);
    }
  }

  // it is usefull to get student's option array
  public synchronized OPTIONS[] GetOptions(int index){return (OPTIONS[])mainArray[index][1];}

}
