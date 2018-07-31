import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.Cursor;

/*
 This class implements the game board for playing wari.
 */

public class Board implements Serializable, KeyListener
{
  private int playTurn;
  private int moveCount = 1;
  private Cup[][] gameBoard = new Cup[3][9];
  private Cup[] captured = new Cup[3];
  private JPanel boardPanel;
  
  private JTextArea actionRecordArea;
  private JScrollPane actionScrollPane;
  
  private Cup playerOneHome = null;
  private Cup playerTwoHome = null;
  
  Container contentPane;
  
  //Initialize game board.
  public Board()
  {
    JLabel cupLabel = null;
    playTurn = 1;
    
    
    //New stuff
    JPanel gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(6, 7));
    
    boardPanel = new JPanel();
    boardPanel.setLayout(new BorderLayout());
    boardPanel.setFocusable(true);
    
    JPanel subPanel = new JPanel();
    subPanel.setLayout(new BorderLayout());
    
    //This section for logs.
    JPanel recordPanel = new JPanel();
    recordPanel.setLayout(new BorderLayout());
    actionRecordArea = new JTextArea(6, 24);
    actionRecordArea.setEditable(false);
    actionRecordArea.append("It is Player 1's Turn.\n");
    actionScrollPane = new JScrollPane(actionRecordArea,
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    recordPanel.add(actionScrollPane);
    boardPanel.add(recordPanel, BorderLayout.NORTH);
    
    for( int i = 0; i < 7; i++)
    {
      //Make Label DONE
      String tempLabel = "Player 1, Cup " + (7 - i);
      JLabel cupLabel1 = new JLabel(tempLabel, JLabel.RIGHT);
      gridPanel.add(cupLabel1);
    }
    
    //Play Cup 1
    gameBoard[1][7] = new Cup(8, 1, 7, gameBoard[2][1], gameBoard[2][1]);
    
    //NEW LOCATION
    JPanel[] cupPanels1 = new JPanel[10];
    JPanel[] cupPanels2 = new JPanel[10];
    JPanel[] cupPanels3 = new JPanel[10];
    
    
    for(int i = 1; i <= 7; i++)
    {
      cupPanels1[i] = new JPanel();

      //Make Button.
      JButton myButton = new JButton("Play Cup");

      MouseHandler myButtonListener = new MouseHandler(1, i);
      myButton.addActionListener(myButtonListener);
      KeyHandler myKeyListener = new KeyHandler(1,i);
      myButton.addKeyListener(myKeyListener);
      
      //YOU WANT THIS 
      cupPanels1[i].add(myButton);
      
      //Make Seed Field
    }
    
    for(int i = 1; i <= 7; i++)
    {
      cupPanels2[i] = new JPanel();
      //Make Label.
      String tempLabel = "Player 2, Cup " + i;
      JLabel cupLabel2B = new JLabel(tempLabel, JLabel.RIGHT);
      //Make Button.
      JButton myButton = new JButton("Play Cup");
      MouseHandler myButtonListener = new MouseHandler(2, i);
      myButton.addActionListener(myButtonListener);
      KeyHandler myKeyListener = new KeyHandler(2,i);
      myButton.addKeyListener(myKeyListener);
      
      cupPanels2[i].add(myButton);
      
      //Make Seed Field
      
    }
    
    for(int i = 7; i >= 1; i--)
    {
      gridPanel.add(cupPanels1[i]);
    }
    
    for(int i = 6; i > 0; i--)
    {
      JButton myButton = new JButton("Play Cup");
      MouseHandler myButtonListener = new MouseHandler(1, i);
      myButton.addActionListener(myButtonListener);
      KeyHandler myKeyListener = new KeyHandler(1,i);
      myButton.addKeyListener(myKeyListener);
    }
    
    //Cup 1
    for(int i = 6; i > 0; i--)
    {
      gameBoard[1][i] = new Cup(8, 1, i, gameBoard[1][i + 1], gameBoard[1][i + 1]);
      
      //new stuff
      JButton myButton = new JButton("Play Cup");
      MouseHandler myButtonListener = new MouseHandler(1, i);
      myButton.addActionListener(myButtonListener);
      KeyHandler myKeyListener = new KeyHandler(1,i);
      myButton.addKeyListener(myKeyListener);
    }
    
    for(int i = 7; i > 0; i--)
    {
      //Added gameboard to gridPanel
      gridPanel.add(gameBoard[1][i].getMyField());
    }
    
    gameBoard[2][1] = new Cup(8, 2, 1, gameBoard[2][2], gameBoard[2][2]);
    gridPanel.add(gameBoard[2][1].getMyField());
    
    //cup 2
    for(int i = 2; i <= 7; i++)
    {
      gameBoard[2][i] = new Cup(8, 2, i, gameBoard[2][i - 1], gameBoard[2][i - 1]);
      gridPanel.add(gameBoard[2][i].getMyField());
      gridPanel.add(cupPanels2[i]);
    } 
    
    //NEW STUFF
      for(int i = 1; i <= 7; i++)
    {
      gridPanel.add(cupPanels2[i]);
    }
      
///////////////////////////////////////////////////////////////////////////////////////////      
    
    //player 2
    for(int i = 1; i <= 7; i++)
    {
      cupLabel = new JLabel(" Player 2, Cup " + i);
      gridPanel.add(cupLabel);
    }
    
    gameBoard[1][7].setNextCup(gameBoard[2][1]);
    gameBoard[2][7].setNextCup(gameBoard[1][1]);
    
    gameBoard[2][1].setNextCup(gameBoard[2][2]);
    gameBoard[2][2].setNextCup(gameBoard[2][3]);
    gameBoard[2][3].setNextCup(gameBoard[2][4]);
    gameBoard[2][4].setNextCup(gameBoard[2][5]);
    gameBoard[2][5].setNextCup(gameBoard[2][6]);
    gameBoard[2][6].setNextCup(gameBoard[2][7]);
    
    subPanel.add(gridPanel, BorderLayout.CENTER);

//////////////////////////////////////////////////////////////////////////////////////////    
    
    //PassFocus
    gameBoard[1][7].setNextCup(gameBoard[2][1]);
    gameBoard[2][7].setNextCup(gameBoard[1][1]);
    
    gameBoard[1][1].setPassCup(gameBoard[1][2]);
    gameBoard[1][7].setPassCup(gameBoard[2][1]);
    
    gameBoard[2][1].setPassCup(gameBoard[2][2]);
    gameBoard[2][2].setPassCup(gameBoard[2][3]);
    gameBoard[2][3].setPassCup(gameBoard[2][4]);
    gameBoard[2][4].setPassCup(gameBoard[2][5]);
    gameBoard[2][5].setPassCup(gameBoard[2][6]);
    gameBoard[2][6].setPassCup(gameBoard[2][7]);
    gameBoard[2][7].setPassCup(gameBoard[1][1]);
    
    gameBoard[1][7].setMyField("8");
    gameBoard[2][7].setMyField("8");      
    
///////////////////////////////////////////////////////////////////////////////////////////////    
    
    //Set up the capture cups.
    captured[1] = new Cup(0, 1);
    JPanel capturedPanel = new JPanel();
    capturedPanel.setLayout(new GridLayout(2, 1));
    JPanel captureCup1 = new JPanel();
    JLabel captureLabel1 = new JLabel("Player 1, Captured");  
    
    captureCup1.add(captureLabel1);
    capturedPanel.add(captureCup1);
    captureCup1 = new JPanel();
    captureCup1.add(getCapturedField(1));
    capturedPanel.add(captureCup1);
    
    captured[2] = new Cup(0, 2);
    JPanel capturedPanel2 = new JPanel();
    capturedPanel2.setLayout(new GridLayout(2, 1));
    JPanel captureCup2 = new JPanel();
    JLabel captureLabel2 = new JLabel("Player 2, Captured");
    
    captureCup2.add(getCapturedField(2));
    capturedPanel2.add(captureCup2);
    captureCup2 = new JPanel();
    captureCup2.add(captureLabel2);
    capturedPanel2.add(captureCup2);
    
    
    subPanel.add(capturedPanel, BorderLayout.NORTH);
    subPanel.add(capturedPanel2, BorderLayout.SOUTH);
    
    boardPanel.add(subPanel, BorderLayout.CENTER);
  }
  
/////////////////////////////////////////////////////////////////////////////////////////////  
  public void moveBoard(Cup playCup)
  {
    
    int handfull = playCup.removeSeeds();
    int whoseTurn = playCup.getPlayer();
    Cup tempReference = playCup.getNextCup();
    
    for(int i = 0; i < handfull; i++)
    {
      tempReference.addOneSeed();
      
      checkHome(whoseTurn, tempReference);
      
      if(whoseTurn == 1)
      {
        if(tempReference == playerOneHome)
        {
          captured[whoseTurn].addSomeSeeds(tempReference.captureSeeds());
        }
        else if(tempReference == playerTwoHome)
        {
          captured[2].addSomeSeeds(tempReference.captureSeeds());
        }
        else if(tempReference.getPlayer() != whoseTurn && i == (handfull - 1) 
                  && tempReference.getSeedCount() % 2 == 0)
        {
          captured[whoseTurn].addSomeSeeds(tempReference.captureSeeds());
        }
      }
      else
      {
        if(tempReference == playerTwoHome)
        {
          captured[whoseTurn].addSomeSeeds(tempReference.captureSeeds());
        }
        else if(tempReference == playerOneHome)
        {
          captured[1].addSomeSeeds(tempReference.captureSeeds());
        }
        else if(tempReference.getPlayer() != whoseTurn && i == (handfull - 1) 
                  && tempReference.getSeedCount() % 2 == 0)
        {
          captured[whoseTurn].addSomeSeeds(tempReference.captureSeeds());
        }
      }
      tempReference = tempReference.getNextCup();
    }
  }
  
//This method implements the player's moves on each turn.  This is the keystroke input version.
  public void moveBoard(int whoseTurn, int playedCup)
  {
    Cup playCup = gameBoard[whoseTurn][playedCup];
    int handfull = playCup.removeSeeds();
    Cup tempReference = playCup.getNextCup();
    
    for(int i = 0; i < handfull; i++)
    {
      tempReference.addOneSeed();
      
      checkHome(whoseTurn, tempReference);
      
      if(whoseTurn == 1)
      {
        if(tempReference == playerOneHome)
        {
          captured[whoseTurn].addSomeSeeds(tempReference.captureSeeds());
        }
        else if(tempReference == playerTwoHome)
        {
          captured[2].addSomeSeeds(tempReference.captureSeeds());
        }
        else if(tempReference.getPlayer() != whoseTurn && i == (handfull - 1) 
                  && tempReference.getSeedCount() % 2 == 0)
        {
          captured[whoseTurn].addSomeSeeds(tempReference.captureSeeds());
        }
      }
      else
      {
        if(tempReference == playerTwoHome)
        {
          captured[whoseTurn].addSomeSeeds(tempReference.captureSeeds());
        }
        else if(tempReference == playerOneHome)
        {
          captured[1].addSomeSeeds(tempReference.captureSeeds());
        }
        else if(tempReference.getPlayer() != whoseTurn && i == (handfull - 1) 
                  && tempReference.getSeedCount() % 2 == 0)
        {
          captured[whoseTurn].addSomeSeeds(tempReference.captureSeeds());
        }
      }
      tempReference = tempReference.getNextCup();
    }
    
  }
  
  public int getCupNumber(Cup cupIn)
  {
    int retval = 1;
    for(int i = 1; i <= 7; i++)
    {
      if(gameBoard[1][i] == cupIn || gameBoard[2][i] == cupIn)
        retval = i;
    }
    return retval;
  }
  
  public Cup getCup(int playerIn, int numberIn)
  {
    return gameBoard[playerIn][numberIn];
  }
  
  public boolean hasSeeds(int whoseTurn, int moveIn)
  {
    if(gameBoard[whoseTurn][moveIn].getSeedCount() != 0)
      return true;
    else
      return false;
  }
  
////////////////////////////////////////////////////////////////////////////////  
  
  public int getSeedCount(int playerIn, int cupSlotIn)
  {
    return gameBoard[playerIn][cupSlotIn].getSeedCount();
  }
  
  
  public JTextField getTextField(int playerIn, int cupSlotIn)
  {
    return gameBoard[playerIn][cupSlotIn].getMyField();
  }
  
  
  public JTextField getCapturedField(int playerIn)
  {
    return captured[playerIn].getMyField();
  }
  
  public void checkHome(int whichPlayer, Cup checkCup)
  {
    if(whichPlayer == 1)
    {
      if(checkCup.getSeedCount() == 3 && playerOneHome == null && checkCup.getPlayer() == 2)
      {
        playerOneHome = checkCup;
        checkCup.setColor();
      }
    }
    else //player 2
    {
      if(checkCup.getSeedCount() == 3 && playerTwoHome == null && checkCup.getPlayer() == 1)
      {
        playerTwoHome = checkCup;
        checkCup.setColor();
      }
    }
  } 
  
  public void renewBoard()
  {
    playTurn = 1;
    moveCount = 1;
    actionRecordArea.setText("");
    captured[1].setSeedCount(0);
    captured[2].setSeedCount(0);
    
    for(int i = 1; i <=8; i++)
    {
      gameBoard[1][i].setSeedCount(8);
    }
    
    for(int i = 1; i <=11; i++)
    {
      gameBoard[2][i].setSeedCount(8);
    }
  }
  
  public boolean gameContinues()
  { 
    int player1Seeds = 0;
    int player2Seeds = 0;
    
    for(int i = 1; i <= 6; i++)
    {
      player1Seeds = player1Seeds + (gameBoard[1][i].getSeedCount());
    }
    
    for(int i = 1; i <= 6; i++)
    {
      player2Seeds = player2Seeds + gameBoard[2][i].getSeedCount();
    }
    
    if(player1Seeds == 0 || player2Seeds == 0)
    {
      if(player1Seeds == 0)
      {
        for(int i = 1; i <= player1Seeds; i++)
        {
          captured[2].addOneSeed();
        }
      }
      else
        for(int i = 1; i <= player2Seeds; i++)
      {
        captured[1].addOneSeed();
      }
      return false;
    }
    else
      return true;   
  }
  
///////////////////////////////////////////////wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
  public void keyTyped(KeyEvent e)
  {
    char KeyChar = e.getKeyChar();
    keyMove(e, KeyChar);
  }
  
  public void keyPressed(KeyEvent e)
  {
    char KeyChar = e.getKeyChar();
    keyMove(e, KeyChar);
  }
  
  public void keyReleased(KeyEvent e)
  {
    char KeyChar = e.getKeyChar();
    keyMove(e, KeyChar);
  }
  
  public void keyMove(KeyEvent event, char keyMove)
  {
    int id = event.getID();
    if(id == KeyEvent.KEY_PRESSED)
    {
      int move = (int)keyMove - 48;
      if(move >= 1 && move <= 8)
      {
        if(gameBoard[playTurn][move].getSeedCount() != 0)
        {
          moveBoard(playTurn, move);
          actionRecordArea.append("Move:  " + moveCount + ".  Player:  " + playTurn + ".  Cup:  " + move + ".\n");
          moveCount++;
          
          if(playTurn == 1)
          {
            playTurn = 2;
          }
          else
          {
            playTurn = 1;
          }
        }
        else
        {
        }
      }
    }
  }
  
//////////////////////////////////////////////////////////////////////
  
  //Determines who wins.
  public int whoWins()
  {
    if(captured[1].getSeedCount() > captured[2].getSeedCount())
    {
      return 1;
    }
    else if(captured[1].getSeedCount() == captured[2].getSeedCount())
    {
      return 0;
    }
    else
    {
      return 2;
    }
    
  }
   
  public JPanel getBoardPanel()
  {
    return boardPanel;
  }
  
  public JTextArea getRecordArea()
  {
    return actionRecordArea;
  }
  
  
  public void setText(String textIn) 
  {
    actionRecordArea.setText(textIn);
  }
  
  public int getMoveCount()
  {
    return moveCount;
  }
  
  public void setMoveCount(int moveCountIn)
  {
    moveCount = moveCountIn;
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
      
      returnedCup = gameBoard[player][cupSlot];
      
      if(returnedCup != null)
      {
        if(returnedCup.getPlayer() == playTurn && returnedCup.getSeedCount() != 0)
        {
          moveBoard(returnedCup);
          
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
  
  private class KeyHandler implements KeyListener 
  {
    int player;
    int cupSlot;
    
    KeyHandler()
    {
      player = 0;
      cupSlot = 0;
    }
    
    KeyHandler(Cup A[][])
    {
      player =1;
      cupSlot =0;
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
        
        moveBoard(playTurn, keyCharAsInt);
        
        /*  Version 11, 23.3  Text area scroll pane support.  */
        
        actionRecordArea.append("Move:  " + moveCount + ".  Player:  " + playTurn + ".  Cup:  " + keyCharAsInt + ".\n");
        moveCount++;
        
        if(playTurn == 1)
          playTurn = 2;
        else
          playTurn = 1;
      }
      else
      {
      }
    }
    
    public void keyPressed(KeyEvent e) 
    { 
    }
    
    @Override
    public void keyReleased(KeyEvent e) 
    {
      // TODO Auto-generated method stub 
    }
  }
  
}
