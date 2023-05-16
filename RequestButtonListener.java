import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*When user click to request button this class's method will execute. */
public class RequestButtonListener implements ActionListener{

  private GUI GUI;

  public RequestButtonListener(GUI gui){
    GUI = gui;
  }

  public void actionPerformed(ActionEvent e){
    System.out.println("Request Button Pressed!");
    GUI.GetClient().SendMessageToServer(null, "request");
  }
}
