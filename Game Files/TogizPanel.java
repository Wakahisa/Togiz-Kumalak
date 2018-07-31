import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.Cursor;

//import Board.KeyHandler;
//import Board.MouseHandler;




public class TogizPanel extends JPanel
{
  private Board playBoard;
  private int playTurn;
  private JTextArea actionRecordArea;
  private JScrollPane actionScrollPane;
  private int moveCount = 1;
  
  public TogizPanel()
  {
    super();
    playTurn = 1;
    playBoard = new Board();
    
    KeyHandler myKeyListener1 = new KeyHandler(playBoard);
    addKeyListener(myKeyListener1);
    setFocusable(true);
    //setLayout(new GridLayout(1, 3));
  }
  
  public void renewBoard()
  {
    playBoard.renewBoard();
  }
  
  public void renewTextArea()
  {
    actionRecordArea.setText("");
  }
  
  public String getText()
  {
    return playBoard.getRecordArea().getText();
  }
  
  public void setText(String textIn)
  {
    //actionRecordArea.setText(textIn);
    playBoard.setText(textIn);
  }
  
  public int getMoveCount()
  {
    return playBoard.getMoveCount();
  }
  
  public void setMoveCount(int countIn)
  {
    playBoard.setMoveCount(countIn);
  }
  
  public Board getPlayBoard()
  {
    return playBoard;
  }
  
  public int getPlayTurn()
  {
    return playTurn;
  }
  
  public void setPlayTurn(int playTurnIn)
  {
    playTurn = playTurnIn;
  }
  
  public void writeRecord(String recordIn)
  {
    actionRecordArea.append(recordIn);
  }
  
  
  public void setBoard(Board boardIn)
  {
    playBoard = boardIn;
    setFocusable(true);
  }
  
  public Board getBoard()
  {
    return playBoard;
  }
  
  private class KeyHandler implements KeyListener 
  {
    int player;
    int cupSlot;
    KeyHandler()
    {
      player = 0;
      cupSlot = 0;
    }
    KeyHandler(Board boardIn)
    {
      player = 0;
      cupSlot= 0;
    }
    KeyHandler(int i)
    {
      player = i;
      cupSlot = 0;
    }
    
    KeyHandler(int playerIn, int cupSlotIn)
    {
      player = playerIn;
      cupSlot = cupSlotIn;
      
    }
    public void keyTyped(KeyEvent event)
    {
      char keyChar = event.getKeyChar();
      int keyCharAsInt;
      
      if(keyChar == '0' || keyChar == '1' || keyChar == '2' || keyChar == '3' || keyChar == '4' || keyChar == '5' || keyChar == '6' ||keyChar == '7' ||keyChar == '8' ||keyChar == '9')
      {
        keyCharAsInt = (int) keyChar - 48;
        
        playBoard.moveBoard(playTurn, keyCharAsInt);
        /*  Version 11, 23.3  Text area scroll pane support.  */
        
        actionRecordArea.append("Move:  " + moveCount + ".  Player:  " + playTurn + ".  Cup:  " + keyCharAsInt + ".\n");
        moveCount++;
        
        if(playTurn == 1)
          playTurn = 2;
        else
          playTurn = 1;
        repaint();
        
      }
      else
      {
      }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
      
    }
  }
  
  class MouseHandler implements ActionListener
  {
    int player;
    int cupSlot;
    MouseHandler()
    {
      player = 0;
      cupSlot = 0;
    }
    
    MouseHandler(int playerIn, int cupSlotIn)
    {
      player = playerIn;
      cupSlot = cupSlotIn;
    }
    
    public void actionPerformed(ActionEvent event)
    {
      Cup returnedCup;
      
      returnedCup = playBoard.getCup(player, cupSlot);
      
      if(returnedCup != null)
      {
        if(returnedCup.getPlayer() == playTurn && returnedCup.getSeedCount() != 0)
        {
          playBoard.moveBoard(returnedCup);
          
          int whatCupNumber = cupSlot+1;
          actionRecordArea.append("Move:  " + moveCount + ".  Player:  " + playTurn + ".  Cup:  " + whatCupNumber + ".\n");
          moveCount++;
          
          
          if(playTurn == 1)
          {
            playTurn = 2;
            actionRecordArea.append("It is player 2's turn.\n");
          }
          else
          {
            playTurn = 1;
            actionRecordArea.append("It is player 1's turn.\n");
          }
        }
      }      
    }
  }
}