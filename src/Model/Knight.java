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
        boolean valid = true;

    
        return valid;    
    }
    
}
