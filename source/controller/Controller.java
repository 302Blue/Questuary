import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Controller{
	Model model;
	View view;
	JFrame frame;
	Timer timer;
	
	//*************
	//Constructor
	public Controller(){
		//create the Model and View
		model = new Model();
		view = new View();
		
		//get the ground from model
		view.setGroundImage(model.getGround());
		
		//update the view of the player's location
		view.updateView(model.getPlayerX(), model.getPlayerY(), (int)model.getPlayerWidth(), (int)model.getPlayerHeight(),model.getPlayerDirection());
		
		//set the frame to add on key event listeners
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
			switch(e.getKeyCode())
			{
				case (KeyEvent.VK_RIGHT):
					//make player go right in model
					model.playerMoveRight();
					view.setAnimation(true);
					System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")" + model.getPlayerDirectionString());
					break;
				
				case (KeyEvent.VK_LEFT):
					//make player go left in model
					model.playerMoveLeft();
					view.setAnimation(true);
					System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")" + model.getPlayerDirectionString());	
					break;
					
				case (KeyEvent.VK_UP):
					if(!(model.isPlayerFalling()))
					{
						model.makePlayerJump();
						System.out.println("makePlayerJump is executed");
					}
					System.out.println("Up key is pressed");
					System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")" + model.getPlayerDirectionString());
					break;
						
				case (KeyEvent.VK_Q):
					//if q is press then quit
					System.exit(0);
					break;
			}
					
		}
		@Override
		public void keyTyped(KeyEvent e){
				
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
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
	
	//main class
	public static void main(String[] args){
		System.out.println("Hello World");
		Controller controller = new Controller();
	}
	
	
	
	
	
}
