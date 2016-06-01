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
public class Knight extends Piece{

    public Knight(String modelPath, Colour colour, Position position) {
        super(modelPath, colour, position);
    }

    @Override
    public boolean validMove(Position pos) {
        boolean valid = false;
        //La posicion X del destino menos mi posicion X en valor absoluto es 2 y en la resta de la Y sea 1 o
        //La posicion X del destino menos mi posicion X en valor absoluto es 1 y en la resta de la Y sea 2 o
        if( (Math.abs(position.getX()-pos.getX())==2 && Math.abs(position.getY()-pos.getY())==1) ||
            (Math.abs(position.getX()-pos.getX())==1 && Math.abs(position.getY()-pos.getY())==2) )
            valid = true;
        return valid;    
    }
    
}
