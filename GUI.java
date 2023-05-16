import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*The GUI class represents a graphical user interface */
public class GUI{
  private static final int HEIGHT = 750, WIDTH = 1000; // Constants for the frame dimensions

  private JComboBox[] comboBoxes;
  //Getter for comboBoxes
  public synchronized JComboBox[] GetComboBoxes(){return comboBoxes;}
  //Setter for comboBoxes
  public synchronized void SetComboBoxes(JComboBox[] combobxses){comboBoxes = combobxses;}

  private JLabel informationTextRef;
  //Getter for informationTexrRef
  public synchronized JLabel GetInformationText(){return informationTextRef;} 
  //Setter for informationTexrRef
  public synchronized void SetInfromationText(String text){informationTextRef.setText(text);}

  private JLabel _resultText;
  //Getter for _resultText  
  public synchronized JLabel GetResultText(){return _resultText;}
  //Setter for _resultText  
  public synchronized void SetResultText(String text){_resultText.setText(text);}

  private Client myClient;
  //Getter for myClient  
  public synchronized Client GetClient(){return myClient;}
  //Setter for myClient
  public synchronized void SetClient(Client myCLIENT){myClient = myCLIENT;}
  // Constructor for the GUI class
  public GUI(String name,Client myCl){synchronized(this){myClient = myCl; MyFrame(name);}}

  // Method to create JComboBoxes with given count and options
  private synchronized <T> void MyCombobox(int count, T[] arr,JFrame frame){
    comboBoxes = new JComboBox[count];
    for(int i = 0; i < count; i++){
      comboBoxes[i] = new JComboBox<>(arr);
      comboBoxes[i].setBackground(Color.BLACK);
      comboBoxes[i].setForeground(Color.WHITE);
      comboBoxes[i].setMaximumRowCount(arr.length);
      comboBoxes[i].setPreferredSize(new Dimension(300, 50));
      frame.add(comboBoxes[i]);
    }
  }
  // Method to create the main JFrame and its components
  public synchronized void MyFrame(String name){
    JLabel informationText = new JLabel(" "); 
    informationTextRef = informationText;
    JFrame frame = new JFrame(name);
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    JLabel nameOfApp = new JLabel("STUDY ABROAD");
    JLabel resultText = new JLabel(" ");
    _resultText = resultText;
    JLabel informationLabel = new JLabel("List all the countries you would most like to go for study.");
    OPTIONS[] options = OPTIONS.values();
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(new SendButtonListener(this));
    JButton requestButton = new JButton("Request");
    requestButton.addActionListener(new RequestButtonListener(this));

    requestButton.setBackground(Color.BLACK);
    requestButton.setForeground(Color.WHITE);
    sendButton.setBackground(Color.BLACK);
    sendButton.setForeground(Color.WHITE);
    grid.gridx = 0;
    grid.gridy = 0;
    grid.insets = new Insets(0, 0, 0, 150);
    panel.add(requestButton, grid);
    grid.gridx = 2;
    grid.gridy = 0;
    grid.insets = new Insets(0, 150, 0, 0);
    panel.add(sendButton, grid);
    grid.gridx = 1;
    grid.gridy = 0;
    grid.insets = new Insets(0, 90, 0, 90);
    panel.add(resultText, grid);
    requestButton.setPreferredSize(new Dimension(200, 50));
    sendButton.setPreferredSize(new Dimension(200, 50));
    informationLabel.setFont(new Font("info", Font.ITALIC, 25));
    informationText.setFont(new Font("infoText", Font.ITALIC, 25));
    informationText.setForeground(Color.RED);
    resultText.setFont(new Font("resultInfo", Font.BOLD, 15));
    nameOfApp.setFont(new Font("name", Font.BOLD, 50));
    frame.setLayout(new FlowLayout(FlowLayout.CENTER,1000,30));
    
    frame.add(nameOfApp);
    frame.add(informationLabel);
    frame.add(informationText);
    MyCombobox(5, options, frame);
    frame.add(panel);
    frame.setSize(WIDTH, HEIGHT);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    frame.setVisible(true);
  }
}