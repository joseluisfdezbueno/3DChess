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
public class Queen extends Piece{

    public Queen(String modelPath, Colour colour, Position position) {
        super(modelPath, colour, position);
    }

    @Override
    public boolean validMove(Position pos) {
        boolean valid = false;
        if( (position.getX()==pos.getX() || position.getY()==pos.getY()) || 
                ( Math.abs(pos.getX()-position.getX())==Math.abs(pos.getY()-position.getY()))){
            valid = true;
        }
        return valid;    
    }
    
}
