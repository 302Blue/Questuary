import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Controller{
	Model model;
	View view;
	JFrame frame;
	Timer timer;
	ArrayList<Integer> keys=new ArrayList();
	

	public Controller() {
    
    //create the Model and View
		model = new Model();
		view = new View(model.getXBoundary(), model.getYBoundary(), model.getPlayerCharacter());    
    //get the ground from model
		view.setGroundImage(model.getGround());
    //update the view of the player's location
		view.updateView(model.getPlayerX(), model.getPlayerY(), model.getDirection(),model.getPlayerCharacter());
    //set the frame
		frame = view.getFrame();
		
		//add KeyListeners
		frame.addKeyListener(new ArrowKeyListener());
		
		timer = new Timer(45, new UpdateView());
		timer.start();		
	}
	//******************************
	
	
	//KeyListener class
	public class ArrowKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
=======
			// add key code in arrayList if pressed and not already there
		  if(!keys.contains(e.getKeyCode())) {
		        keys.add(e.getKeyCode());
		    }
          
			// if only 1 key is pressed 
			if (keys.size() == 1) {
				// checks if game is not in Change player mode
				if (!model.getChangeCharacterMode()) {
          
					switch (keys.get(0)) {
              
           case (KeyEvent.VK_UP):
              // jump function           
              if(!(model.isPlayerFalling())) {
                model.makePlayerJump();
                view.setAnimation(true);
                System.out.println("makePlayerJump is executed");
              }
              System.out.println("Up key is pressed");
              System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")" + model.getPlayerDirectionString());
            break;
              
					case (KeyEvent.VK_RIGHT):
              // move player right
              model.changeRoom();
              model.playerMoveRight();
              view.setAnimation(true);
              System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")" + model.getPlayerDirectionString());
              break;
              
					case (KeyEvent.VK_LEFT):
              // move player left
              model.playerMoveLeft();
              System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")" + model.getPlayerDirectionString());
              break;
              
					case (KeyEvent.VK_DOWN):
              // if q is press then quit
              //System.exit(0);
              break;
              
          case (KeyEvent.VK_ESCAPE):
          case (KeyEvent.VK_Q):
              //if q or escape are pressed then quit
              System.exit(0);
              break;
					}
          
				} else {
					// game is in change player mode 
					switch (keys.get(0)) {
					case (KeyEvent.VK_RIGHT):
						// increment the player selector loop
						model.incrementChangeCharacterCount();
						view.updateView(model.getPlayerX(), model.getPlayerY(), model.getDirection(), model.getPlayerCharacter());
						break;
					case (KeyEvent.VK_LEFT):
						// increment the player selector loop
						model.decrementChangeCharacterCount();
						view.updateView(model.getPlayerX(), model.getPlayerY(), model.getDirection(), model.getPlayerCharacter());
						break;
					}
				}
				
			} else if (keys.contains(KeyEvent.VK_DOWN) & keys.contains(KeyEvent.VK_RIGHT)) {
				//changes game mode to switching player
				model.setChangePlayerMode();
			}
	  }
      
		@Override
		public void keyTyped(KeyEvent e){
				
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode())
			{
				//when right arrow is released the dx is 0
				case (KeyEvent.VK_RIGHT):
					model.setPlayerDxOff();
					view.setAnimation(false);
					System.out.println("Right key is released");
					break;
          
				case (KeyEvent.VK_LEFT):
					//make player go left in model
					model.setPlayerDxOff();
					view.setAnimation(false);
					System.out.println("Left key is released");	
					break;
			}
		}
	}	
	
	public class UpdateView implements ActionListener{
			// removes key code from arrayList once released
			if(keys.contains(e.getKeyCode())){
		        keys.remove(keys.indexOf(e.getKeyCode()));
		    }

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//test the player on falling and jumping
			model.gravity();
			//increment the player's x and y
			model.movePlayer();
			view.setPicNum();
			//update the view and draw the image
			view.updateView(model.getPlayerX(), model.getPlayerY(), (int)model.getPlayerWidth(), (int)model.getPlayerHeight(),model.getPlayerDirection());
			
		}
		
	}
	
	//main method
	public static void main(String[] args){
		System.out.println("Hello World");
		Controller controller = new Controller();
	}	
	
}
