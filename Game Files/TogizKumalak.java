import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.Cursor;



public class TogizKumalak
{
  public static void main(String[] args)
  {
    TogizFrame myFrame = new TogizFrame();
    myFrame.setTitle("Togiz Kumalak, Final");
    myFrame.setVisible(true);
  }
}

class TogizFrame extends JFrame
{  
  private final int FRAMEW = 400;
  private final int FRAMEH = 150;
  private int subFrameLocation = 50;
  
  public TogizFrame()
  {
    setSize(FRAMEW, FRAMEH);
    addWindowListener(new WindowCloser());
    
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    
    JMenuItem makeSubFrameItem = new JMenuItem("MakeSubFrame");
    fileMenu.add(makeSubFrameItem);
    
    MakeSubFrameListener mySubFrameListener = new MakeSubFrameListener();
    makeSubFrameItem.addActionListener(mySubFrameListener);
    
    JMenuItem exitItem = new JMenuItem("Exit");
    fileMenu.add(exitItem);
    
    ExitListener myExitListener = new ExitListener();
    exitItem.addActionListener(myExitListener);
  }
  
  private class WindowCloser extends WindowAdapter
  {
    public void windowClosing(WindowEvent event)
    {
      System.exit(0);
    }
  }
  
  private class MakeSubFrameListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      TogizSubFrame myframe = new TogizSubFrame();
      myframe.setLocation(subFrameLocation, subFrameLocation);
      if(subFrameLocation >= 500)
      {
        subFrameLocation = 50;
      }
      else
      {
        subFrameLocation += 50;
      }
      myframe.setVisible(true);
    }
  }
  
  private class ExitListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      System.exit(0);
    }
  }
}

class TogizSubFrame extends JFrame
{
  private TogizPanel myPanel;
  private Board playBoard;
  private final int FRAMEW = 1000;
  private final int FRAMEH = 800;
  
  Container contentPane;
  
  JFileChooser myChooser;
  
  public TogizSubFrame()
  {
    setFocusable(true);
    
    playBoard = new Board();
    
    myChooser = new JFileChooser();
    setSize(FRAMEW, FRAMEH);
    
    myPanel = new TogizPanel();
    myPanel.setFocusable(true);
    contentPane = getContentPane();
    contentPane.setFocusable(true);
    
    myPanel.add(playBoard.getBoardPanel());
    contentPane.add(myPanel);
    
    addWindowListener(new WindowCloser());
    
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    
    JMenuItem restartItem = new JMenuItem("Restart");
    fileMenu.add(restartItem);
    
    RestartListener myRestartListener = new RestartListener();
    restartItem.addActionListener(myRestartListener);
    
    JMenuItem closeItem = new JMenuItem("Close");
    fileMenu.add(closeItem);
    
    CloseListener myCloseListener = new CloseListener();
    closeItem.addActionListener(myCloseListener);
    
    JMenuItem saveItem = new JMenuItem("Save");
    fileMenu.add(saveItem);
    
    SaveListener mySaveListener = new SaveListener();
    saveItem.addActionListener(mySaveListener);
    
    JMenuItem loadItem = new JMenuItem("Load");
    fileMenu.add(loadItem);
    
    LoadListener myLoadListener = new LoadListener();
    loadItem.addActionListener(myLoadListener);  
  }
  
  private class WindowCloser extends WindowAdapter
  {
    public void windowClosing(WindowEvent event)
    {
      dispose();
    }
  }
  
  private class RestartListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      contentPane.requestFocusInWindow();
      contentPane.remove(myPanel);
      playBoard = new Board();
      myPanel = new TogizPanel();
      myPanel.setFocusable(true);
      myPanel.add(playBoard.getBoardPanel());
      contentPane.add(myPanel, "Center");
      repaint();
      revalidate();
    }
  }
  
  private class CloseListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      dispose();
    }
  }
  
  private class SaveListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      String fileName;
      myChooser.setCurrentDirectory(new File("."));
      myChooser.showSaveDialog(TogizSubFrame.this);
      fileName = myChooser.getSelectedFile().getPath();
      
      try
      {
        contentPane.requestFocusInWindow();
        ObjectOutputStream objOut = new ObjectOutputStream(
                                                           new FileOutputStream(fileName));
        objOut.writeObject(myPanel);
        objOut.writeObject(myPanel.getBoard());
        objOut.writeObject(myPanel.getText());
        objOut.writeObject(myPanel.getMoveCount());
      }
      
      catch(IOException e)
      {
        System.out.println("Problem making or writing to an output stream.");
        System.out.println(e);
      }
    }
  }
  
  private class LoadListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      String fileName;
      myChooser.setCurrentDirectory(new File("."));
      myChooser.showOpenDialog(TogizSubFrame.this);
      fileName = myChooser.getSelectedFile().getPath();
      
      try
      {
        contentPane.requestFocusInWindow();
        Container contentPane = getContentPane();
        contentPane.remove(myPanel);
        
        ObjectInputStream objIn = new ObjectInputStream(
                                                        new FileInputStream(fileName));
        myPanel = new TogizPanel();
        myPanel = (TogizPanel) objIn.readObject();
        myPanel.setFocusable(true);
        
        myPanel.setBoard((Board) objIn.readObject());
        myPanel.setText((String) objIn.readObject());
        Integer localMoveCountObject = (Integer) objIn.readObject();
        int localMoveCount = localMoveCountObject.intValue();
        myPanel.setMoveCount(localMoveCount);
        
        contentPane.add(myPanel, "Center");
        setVisible(true);
        repaint();
        revalidate();
      }
      catch(IOException e)
      {
        System.out.println("Problem making an input stream.");
      }
      catch(ClassNotFoundException e)
      {
        System.out.println("Class not found problem when reading objects.");
      }
    }
  }
}