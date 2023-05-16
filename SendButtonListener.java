import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
/* ActionListener implementation for the send button.
This class's method will execute when the user clicks the send button. */
public class SendButtonListener implements ActionListener{

  private String message = " "; 
  private OPTIONS[] selectedArray = new OPTIONS[5];
  private GUI GUI;
  // Constructor that takes a GUI instance  
  public SendButtonListener(GUI gui){
    GUI = gui;
  }

  // Method to get the selected countries from the JComboBoxes  
  private void GetSelectedCountrys(JComboBox[] arr){
    for(int i =0; i < arr.length; i++){
      selectedArray[i] = (OPTIONS)arr[i].getSelectedItem();
    }
  }
  // Method to check the validity of the country selection list
  //If Client choose two same country or doesnt choose at least one country,
  //and want send datas to back, this method will prevent it.
  private boolean CheckList(OPTIONS[] arr){
    boolean isAllCountrysSelected = true;
    boolean isACountrySelectedMultipleTimes = true;
    for(int i = 0; i < arr.length;i++){if(arr[i] == OPTIONS.CHOOSE_COUNTRY){isAllCountrysSelected = false;break;}}
    if(!isAllCountrysSelected){message = "You must complete the entire list";return false;}
    for(int i = 0; i < arr.length; i++){
      for(int j = 0; j < arr.length; j++){if(arr[i] == arr[j] && i!=j){isACountrySelectedMultipleTimes = false; break;}}
      if(!isACountrySelectedMultipleTimes) break;
    }
    if(!isACountrySelectedMultipleTimes){message = "You can choose a country only one time"; return false;}
    return true;
  }
  // ActionListener method that gets executed when the send button is clicked
  public void actionPerformed(ActionEvent e){
    GetSelectedCountrys(GUI.GetComboBoxes());
    if(!CheckList(selectedArray)){
      GUI.GetInformationText().setForeground(Color.RED);
      GUI.SetInfromationText(message);}
    if(CheckList(selectedArray)){ 
      message = "Your list succesfully sent";
      GUI.GetInformationText().setForeground(Color.GREEN);
      GUI.SetInfromationText(message);
      GUI.GetClient().SendMessageToServer(selectedArray, "send");
    }
  }
}
