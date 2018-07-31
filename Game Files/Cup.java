import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.Cursor;


public class Cup implements Cloneable, Serializable
{
 private int whoseCup;
 private Cup nextCup;
 private JTextField myField;
 private Cup cupToPassFocusTo;
 private int seedsInCup;
 //new
 private int cupIndex;
 private JButton myButton;

 //Constructor used for creating the capture cups.
 public Cup(int seedCountIn, int whoseCupin)
 {
  whoseCup = whoseCupin;
  nextCup = null;
  cupToPassFocusTo = null;
  seedsInCup = seedCountIn;
  myField = new JTextField("0", 8);
 }

 //Constructor used for gameplay cups.
 //Changed
 public Cup(int seedCountIn, int whoseCupin, int cupIndexIn, Cup nextCupin, Cup cupToPassFocusToIn)
 {
  seedsInCup = seedCountIn;
  whoseCup = whoseCupin;
  nextCup = nextCupin;
  //new stuff
  cupIndex = cupIndexIn;
  
  cupToPassFocusTo = cupToPassFocusToIn;
  myField = new JTextField("8", 8);
  TextFieldListener myListener = new TextFieldListener();
  myField.addActionListener(myListener);
  
  myButton = new JButton ("Play Cup");
 }

 public void setSeedCount(int seedsIn)
 {
  seedsInCup = seedsIn;
  String temp = "" + seedsIn;
  myField.setText(temp);
 }
 
 public int getSeedCount()
 {
  return seedsInCup;
 }

 public void addOneSeed()
 {
  seedsInCup = seedsInCup + 1;
  String temp = "" + seedsInCup;
  myField.setText(temp);
 }

 public void addSomeSeeds(int handfullIn)
 {
  seedsInCup = seedsInCup + handfullIn;
  String temp = "" + seedsInCup;
  myField.setText(temp);
 }
 
 public void setColor()
 {
  myField.setForeground(Color.RED);
 }

 public Cup getPassCup()
 {
  return cupToPassFocusTo;
 }
 
 public void setPassCup(Cup cuptemp)
 {
  cupToPassFocusTo = cuptemp;
 }
 
 public JTextField getMyField()
 {
  return myField;
 }
 
 public void setMyField(JTextField fieldIn)
 {
  myField = fieldIn;
 }
 
 public void setMyField(String fieldIn)
 {
  myField.setText(fieldIn);
 }
 
 public int getWhoseCup()
 {
  return whoseCup;
 }

 public Cup getNextCup()
 {
  return nextCup;
 }

 public void setNextCup(Cup nextCupin)
 {
  nextCup = nextCupin;
 }

 public int removeSeeds()
 { 
  if (seedsInCup == 1)
  {
   int temp = 1;
   seedsInCup = 0;
   String tempString = "" + seedsInCup;
   myField.setText(tempString);
   return temp;
  }
  else if (seedsInCup > 1)
  {
   int temp = seedsInCup - 1;
   seedsInCup = 1;
   String tempString = "" + seedsInCup;
   myField.setText(tempString);
   return temp;
  }
  else
  {
   return 0;
  }
 }

 public int getPlayer()
 {
  return whoseCup;
 }
 
 private class TextFieldListener implements ActionListener
 {
  public void actionPerformed(ActionEvent event)
  {
   String inputString = myField.getText();
   cupToPassFocusTo.getMyField().requestFocusInWindow();
  }
 }
 
 public int captureSeeds()
 {
  int capturedSeeds = seedsInCup;
  seedsInCup = 0;
  String tempString = "" + seedsInCup;
  myField.setText(tempString);
  return capturedSeeds;
  }
 
   public JButton getCupButton()
  {
   return myButton;
  }
}