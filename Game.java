package Game;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Stack;

import javax.swing.*;

public class Game implements ActionListener{
	int[][] MAP = new int [8][8];
	JFrame window  = new JFrame("Minesweeper"); 
	JButton [][]buttons  = new JButton[8][8];
	boolean gameEnd = false;
	public Game() {
		setupBoard();
		displayBoard();
		window.setSize(500,500);
		window.setLayout(new GridLayout(8,8,0,0));
		for(int i =0; i < 8; i++) {
			for (int j = 0; j<8; j++) {
				buttons[i][j] = new JButton(" ");
				buttons[i][j].setActionCommand(i+" "+j);
				buttons[i][j].addActionListener(this);
				
				window.add(buttons[i][j]);
			}
		}
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void displayBoard(){
	       int y =-1;
	       int x = 0;
	       for(int[] i: MAP){
	            y++;
	            x = 0;
	           System.out.print("\n");
	           for(int tile: i){
	                       System.out.print(MAP[y][x]+ " ");
	                       x++;
	           }
	      }
	}
	private void setupBoard() {
        int pos;
        int x,y;
        for(int i = 0; i < 10; i++){
        	
        	do {
        		pos = (int) (Math.random()*64);
        		x = pos%8;
        		y = (pos - x) / 8;
        	}while(MAP[x][y] == -1);
              MAP[x][y] = -1;
        } 
        y = -1;
      
        System.out.print("\n");
        for(int[] count:MAP){
        	y += 1;
        	x =-1;
               for(int z : count){
            	   x++; 
            	   if(MAP[y][x] == -1) {
            		  if(y-1 != -1 && MAP[y-1][x] !=-1) {
            			  MAP[y-1][x] ++;
            		  }
            		  if(y+1 != 8 && MAP[y+1][x] !=-1) {
            			  MAP[y+1][x] ++;
            		  }
            		  if(x-1 != -1 && MAP[y][x-1] !=-1 ) {
            			  MAP[y][x-1] ++;
            		  }
            		  if(x+1 != 8 && MAP[y][x+1]!= -1) {
            			  MAP[y] [x+1] ++;
            		  }
            		  if(y-1 != -1 && x-1 != -1 && MAP[y-1][x-1] !=-1) {
            			  MAP[y-1][x-1] ++;
            		  }
            		  if(y+1 != 8 && x-1 != -1 && MAP[y+1][x-1] !=-1) {
            			  MAP[y+1][x-1] ++;
            		  }
            		  if(y-1 != -1 && x+1 != 8 && MAP[y-1][x+1] !=-1 ) {
            			  MAP[y-1][x+1] ++;
            		  }
            		  if(y+1 != 8 && x+1 != 8   && MAP[y+1][x+1]!= -1) {
            			  MAP[y+1] [x+1] ++;
            		  }
            	   }
                }
               
        }
        x = -1;
        y = -1;
}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new Game();
			}
		});
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String loc = ae.getActionCommand();	
		int x = Integer.valueOf(loc.substring(0,1));
		int y = Integer.valueOf(loc.substring(2,3));
		int value = MAP[x][y];
		if(value == -1) {
			buttons[x][y].setText(String.valueOf(value));
			JOptionPane.showMessageDialog(null, "Game Over");
			System.exit(0);
		}else if (value > 0) {
			uncoverTile(x,y);
		}else if(value == 0) {
			Stack<String> tilesToVisit = new Stack<String>();
			HashSet<String> visited = new HashSet<String>();
			
			tilesToVisit.push(String.valueOf(x)+ String.valueOf(y));
			
			while(!tilesToVisit.isEmpty()) {
				String current = tilesToVisit.pop();
				if(!visited.contains(current)) {
					int r = Integer.valueOf(current.substring(0,1));
					int c = Integer.valueOf(current.substring(1,2));
					if(MAP[r][c]==0) {
						uncoverTile(r+1,c);
						tilesToVisit.push(String.valueOf(r+1)+ String.valueOf(c));
						uncoverTile(r-1,c);
						uncoverTile(r,c-1);
						uncoverTile(r,c+1);
						uncoverTile(r+1,c-1);
						uncoverTile(r+1,c+1);
						uncoverTile(r-1,c-1);
						uncoverTile(r-1,c+1);
						
					}
				}
				
			}
		}
	}
	private void uncoverTile(int x,int y) {
		if((x > 7||y > 7||x < 0||y < 0)) {
			return;
		}
		if(MAP[x][y]==-1) {
			return;
		}
		buttons[x][y].setText(String.valueOf(MAP[x][y]));
		buttons[x][y].setEnabled(false);
	}
}
