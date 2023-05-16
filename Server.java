import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/*This class represent server. Students destinations will store here. According to the
students destinations this class calculate all students final destination. And send back
result to students.*/
public class Server implements Runnable{
  private DataOfStudents optimizedData;
  private DataOfStudents dataOfStudents;

  //Constructor for server which create instance of dataOfStudents;
  public Server(){
    dataOfStudents = new DataOfStudents(40);
  }
  //Thread's run function
  public void Run(){
    try{
      ServerSocket serverSocket = new ServerSocket(2000); // Server will launch at socket 2000 
      while(true){
        Socket socket = serverSocket.accept();
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        Object[] data = (Object[]) inputStream.readObject();
        if(data.length == 3){ // this validation help to us use server without crashing
          dataOfStudents.Add((int)data[0], (OPTIONS[])data[1]); //add student destinations and ID to array
          inputStream.close();
          socket.close();
          //We used thread to calculate final destinations, because it can take some times and 
          //while this time we should listen other students requests.
          Thread calculateThread = new Thread(new CalculateResultThread(this, dataOfStudents));
          calculateThread.start();
          if(optimizedData != null){ optimizedData.showArray();}
        }
        if(data.length == 2){ // this validation help to us use server without crashing
          if(optimizedData != null){
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            Object outputData = chooseResultForStudent((int)data[0]); //this object hold students final destination 
            outputStream.writeObject(outputData);
            outputStream.close();
            inputStream.close();
            socket.close();
          }
        }
      }
    }catch(IOException e){e.printStackTrace();}catch(ClassNotFoundException e){e.printStackTrace();}
  }
  // This datas help us to domanstrate how our result calculation method work.
  public void DummyDatas(){
    OPTIONS[] options = {OPTIONS.AZERBAIJAN,OPTIONS.FINLAND,OPTIONS.GERMANY,OPTIONS.FRANCE,OPTIONS.ENGLAND};
    dataOfStudents.Add(0,options);
    options = new OPTIONS[] {OPTIONS.FINLAND,OPTIONS.POLAND,OPTIONS.SWITZERLAND,OPTIONS.SPAIN,OPTIONS.SWEEDEN};
    dataOfStudents.Add(1, options);
    options = new OPTIONS[] {OPTIONS.FRANCE,OPTIONS.AZERBAIJAN,OPTIONS.SWITZERLAND,OPTIONS.SPAIN,OPTIONS.SWEEDEN};
    dataOfStudents.Add(2, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN,OPTIONS.NORWAY,OPTIONS.FRANCE,OPTIONS.ENGLAND,OPTIONS.SWEEDEN};
    dataOfStudents.Add(3, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN,OPTIONS.SWEEDEN,OPTIONS.GERMANY,OPTIONS.SPAIN,OPTIONS.FINLAND};
    dataOfStudents.Add(4, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN,OPTIONS.SWITZERLAND,OPTIONS.FINLAND,OPTIONS.FRANCE,OPTIONS.SWEEDEN};
    dataOfStudents.Add(5, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.SWITZERLAND,OPTIONS.SPAIN,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(6, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.POLAND,OPTIONS.SPAIN,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(7, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.ENGLAND,OPTIONS.POLAND,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(8, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.SWITZERLAND,OPTIONS.POLAND,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(9, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.POLAND,OPTIONS.SPAIN,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(10, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.ENGLAND,OPTIONS.POLAND,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(11, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.POLAND,OPTIONS.SPAIN,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(12, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.SWITZERLAND,OPTIONS.SPAIN,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(13, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.ENGLAND,OPTIONS.SPAIN,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(14, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.POLAND,OPTIONS.SPAIN,OPTIONS.GERMANY,OPTIONS.SWEEDEN};
    dataOfStudents.Add(15, options);
    options = new OPTIONS[] {OPTIONS.NORWAY,OPTIONS.POLAND,OPTIONS.SWITZERLAND,OPTIONS.SPAIN,OPTIONS.SWEEDEN};
    dataOfStudents.Add(16, options);
    options = new OPTIONS[] {OPTIONS.POLAND, OPTIONS.FRANCE, OPTIONS.NORWAY, OPTIONS.SWEEDEN, OPTIONS.AZERBAIJAN};
    dataOfStudents.Add(17, options);
    options = new OPTIONS[] {OPTIONS.GERMANY, OPTIONS.SWITZERLAND, OPTIONS.AZERBAIJAN, OPTIONS.ENGLAND, OPTIONS.POLAND};
    dataOfStudents.Add(18, options);
    options = new OPTIONS[] {OPTIONS.NORWAY, OPTIONS.ENGLAND, OPTIONS.AZERBAIJAN, OPTIONS.SPAIN, OPTIONS.SWEEDEN};
    dataOfStudents.Add(19, options);
    options = new OPTIONS[] {OPTIONS.FRANCE, OPTIONS.POLAND, OPTIONS.NORWAY, OPTIONS.AZERBAIJAN, OPTIONS.FINLAND};
    dataOfStudents.Add(20, options);
    options = new OPTIONS[] {OPTIONS.SWEEDEN, OPTIONS.SPAIN, OPTIONS.GERMANY, OPTIONS.NORWAY, OPTIONS.AZERBAIJAN};
    dataOfStudents.Add(21, options);
    options = new OPTIONS[] {OPTIONS.POLAND, OPTIONS.FINLAND, OPTIONS.SWEEDEN, OPTIONS.AZERBAIJAN, OPTIONS.GERMANY};
    dataOfStudents.Add(22, options);
    options = new OPTIONS[] {OPTIONS.SWITZERLAND, OPTIONS.FRANCE, OPTIONS.NORWAY, OPTIONS.AZERBAIJAN, OPTIONS.GERMANY};
    dataOfStudents.Add(23, options);
    options = new OPTIONS[] {OPTIONS.SPAIN, OPTIONS.AZERBAIJAN, OPTIONS.NORWAY, OPTIONS.FINLAND, OPTIONS.SWITZERLAND};
    dataOfStudents.Add(24, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN, OPTIONS.FRANCE, OPTIONS.SWEEDEN, OPTIONS.NORWAY, OPTIONS.FINLAND};
    dataOfStudents.Add(25, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN, OPTIONS.NORWAY, OPTIONS.SWITZERLAND, OPTIONS.ENGLAND, OPTIONS.FINLAND};
    dataOfStudents.Add(26, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN, OPTIONS.SPAIN, OPTIONS.POLAND, OPTIONS.NORWAY, OPTIONS.GERMANY};
    dataOfStudents.Add(27, options);
    options = new OPTIONS[] {OPTIONS.FINLAND, OPTIONS.NORWAY, OPTIONS.AZERBAIJAN, OPTIONS.GERMANY, OPTIONS.SWEEDEN};
    dataOfStudents.Add(28, options);
    options = new OPTIONS[] {OPTIONS.POLAND, OPTIONS.SWEEDEN, OPTIONS.FRANCE, OPTIONS.AZERBAIJAN, OPTIONS.SPAIN};
    dataOfStudents.Add(29, options);
    options = new OPTIONS[] {OPTIONS.POLAND, OPTIONS.FINLAND, OPTIONS.SWITZERLAND, OPTIONS.GERMANY, OPTIONS.AZERBAIJAN};
    dataOfStudents.Add(30, options);
    options = new OPTIONS[] {OPTIONS.NORWAY, OPTIONS.SWEEDEN, OPTIONS.POLAND, OPTIONS.FRANCE, OPTIONS.GERMANY};
    dataOfStudents.Add(31, options);
    options = new OPTIONS[] {OPTIONS.SPAIN, OPTIONS.SWITZERLAND, OPTIONS.POLAND, OPTIONS.FRANCE, OPTIONS.NORWAY};
    dataOfStudents.Add(32, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN, OPTIONS.SWEEDEN, OPTIONS.POLAND, OPTIONS.GERMANY, OPTIONS.FRANCE};
    dataOfStudents.Add(33, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN, OPTIONS.FINLAND, OPTIONS.SPAIN, OPTIONS.SWEEDEN, OPTIONS.NORWAY};
    dataOfStudents.Add(34, options);
    options = new OPTIONS[] {OPTIONS.POLAND, OPTIONS.FRANCE, OPTIONS.AZERBAIJAN, OPTIONS.NORWAY, OPTIONS.SWITZERLAND};
    dataOfStudents.Add(35, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN, OPTIONS.SWITZERLAND, OPTIONS.FINLAND, OPTIONS.NORWAY, OPTIONS.ENGLAND};
    dataOfStudents.Add(36, options);
    options = new OPTIONS[] {OPTIONS.AZERBAIJAN, OPTIONS.POLAND, OPTIONS.SWEEDEN, OPTIONS.SPAIN, OPTIONS.FRANCE};
    dataOfStudents.Add(37, options);
  }
  
  /* */
  public void CalculateAllResults(DataOfStudents data, boolean isEvolved){
    boolean isResultFound = true;
    int[][] counts = new int[5][10];
    int tier,bestMatch = 0, bestTier = 0;
    int[] pointsPerTier = new int[5];
    OPTIONS[] options = OPTIONS.values();
    // Count the occurrences of each option per tier
    if(!isEvolved){
      // Initial calculation (not evolved)
      // Iterate through the data and count the occurrences of each option in each tier
      System.out.println("----------------------------------------------------------");
      for(tier = 0;tier<5;tier++){
        for(int i = 0; data.mainArray[i][1] != null; i++){
          for(int j = 0; j < 11; j++){if(data.GetOptions(i)[(tier+data.tiersOfMainArray[i])%5] == options[j]){counts[tier][j-1]++;}}}
        System.out.println(Arrays.toString(counts[tier]));
      }
      System.out.println("----------------------------------------------------------");
    }else{
      // Evolution calculation
      // Iterate through the data and count the occurrences of each option in each tier (evolved)
      System.out.println("----------------------------------------------------------");
      for(tier = 0;tier<5;tier++){
        for(int i = 0; data.mainArray[i][1] != null; i++){
          for(int j = 0; j < 11; j++){if(data.GetOptions(i)[(data.tiersOfMainArray[i])%5] == options[j]){counts[tier][j-1]++;}}}
        System.out.println(Arrays.toString(counts[tier]));
      }
      System.out.println("----------------------------------------------------------");
    }
    // Calculate points per tier based on the counts    
    for(tier = 0; tier < 5; tier++){
      for(int i = 0; i < 10; i++){if(counts[tier][i] <= 10) pointsPerTier[tier]++;}
      pointsPerTier[tier] += (5-tier)*2;
    }
    System.out.println(Arrays.toString(pointsPerTier));
    // Find the best tier with the highest points
    for(tier = 0; tier < 5; tier++){
      if(tier == 0) bestMatch = pointsPerTier[tier];
      if(pointsPerTier[tier] > bestMatch){ bestMatch = pointsPerTier[tier]; bestTier = tier;}
    }
    // Update the tiers of the main array based on the best tier 
    for(int i = 0;data.mainArray[i][1] != null; i++){data.tiersOfMainArray[i] += bestTier;}
    // Check if any option in the best tier has more than 10 occurrences and perform evolution
    for(int i = 0; i < 10; i++){
      if(counts[bestTier][i] > 10){ 
        evolution(data,bestTier,counts[bestTier]); isResultFound = false; break;}}
    if(isResultFound){
      optimizedData = data;
    }
  }
  //evolve datas to find best results
  private void evolution(DataOfStudents data,int tier,int[] counts){
    DataOfStudents evolvedData = new DataOfStudents(data);
    OPTIONS[] options = OPTIONS.values();
    //Checks if there are more than 10 students in single country. If yes,
    //then all of this students goes to their second destination. if there are students in one 
    //country who's selected country first or second, in this stuation, only first tier students,
    //will send to their second tier destination. 
    for(int i = 0; i < 10; i++){
      if(counts[i] > 10){
        for(int j = 0; j < data.Length(); j++){
          if(data.mainArray[j][1]!=null)
          if(data.GetOptions(j)[(data.tiersOfMainArray[j])%5] == options[i+1]){
            if(smallestTier(data, i+1) == data.tiersOfMainArray[j]) evolvedData.tiersOfMainArray[j] += 1;}}}}
    //After evolving we check if everyting all right or not.
    CalculateAllResults(evolvedData,true);
  }
  
  //Help to us find smallestTier for each student.When smallestTier is  1 it means student 
  // will go first tier.
  private int smallestTier(DataOfStudents data, int countryIndex){
    int smallestTier = 0;
    boolean firstTime = true;
    OPTIONS[] options = OPTIONS.values();
    for(int i = 0; data.mainArray[i][1] != null; i++){
      if(data.GetOptions(i)[(data.tiersOfMainArray[i]%5)] == options[countryIndex]){
        if(firstTime){smallestTier = data.tiersOfMainArray[i]; firstTime = false; continue;}
        if(smallestTier < data.tiersOfMainArray[i]) smallestTier = data.tiersOfMainArray[i];}
    }
    return smallestTier;
  }

  //This method help to us send results back to srudents. 
  private String chooseResultForStudent(int ID){
    int match = 0;
    boolean isFound = false;
    String result = " ";
    for(int i = 0; optimizedData.mainArray[i][1] != null; i++){
      if(ID == (int)optimizedData.mainArray[i][0]){ match = i; isFound = true; break;}}
    if(isFound) result = optimizedData.GetOptions(match)[(optimizedData.tiersOfMainArray[match])%5].name();
    return result;
  }

  public void run(){
    // DummyDatas();
    Run();
  }

}
