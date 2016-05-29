/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Jose
 */
public class Pawn extends Piece{
    private boolean inStart;
    
    public Pawn(String modelPath, Colour colour, Position position) {
        super(modelPath, colour, position);
        
        inStart = true;
        // this.scale.setScale(1.5);
        // this.scale.setScale(new Vector3d(1.5, 2, 1.5));
    }
    
    public boolean getInStart() {
        return inStart;
    }    
    
    public void setInStart(boolean inStart) {
        this.inStart = inStart;
    }       

    @Override
    public boolean validMove(Position pos) {
        boolean valid = false;
        
        // blancas
        if(this.colour == Colour.White){
            if(inStart){  // si es el movimiento inicial permitimos dar 2 pasos hacia delante al peon
                if((pos.getX() - position.getX() == 1 || pos.getX() - position.getX() == 2) 
                        && (Math.abs(pos.getY() - position.getY()) == 1 || pos.getY() - position.getY() == 0))
                    valid = true;
            }else{
                if((pos.getX() - position.getX() == 1) && (Math.abs(pos.getY() - position.getY()) == 1 || pos.getY() - position.getY() == 0))
                    valid = true;
            }
        }else{
            if(inStart){  // si es el movimiento inicial permitimos dar 2 pasos hacia delante al peon
                if((pos.getX() - position.getX() == -1 || pos.getX() - position.getX() == -2) 
                        && (Math.abs(pos.getY() - position.getY()) == 1 || pos.getY() - position.getY() == 0))
                    valid = true;
            }else{
                if((pos.getX() - position.getX() == -1) && (Math.abs(pos.getY() - position.getY()) == 1 || pos.getY() - position.getY() == 0))
                    valid = true; 
            }
        }                       
        return valid;
    }
    
}
