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
public class King extends Piece{
    private boolean allowCastling;

    public King(String modelPath, Colour colour, Position position) {
        super(modelPath, colour, position);
        
        allowCastling =  true;
    }
    
    public boolean getAllowCasting(){
        return allowCastling;
    }
    
    public void setAllowCastling(boolean allow){
        allowCastling = allow;
    }    
    

    @Override
    public boolean validMove(Position pos) {
        boolean valid = false;
        int steps;
        
        steps = (int) (Math.pow((pos.getX() - position.getX()), 2) + Math.pow((pos.getY() - position.getY()), 2));
        
        // si nos movemos a una casilla colindante, moviemiento válido
        if(steps == 1 || steps == 2)
            valid = true;
        
        // si todavía está permitido el enroque, permitimos el movimiento extra
        if(allowCastling)
            if(pos.getX() - position.getX() == 0 && Math.abs(pos.getY() - position.getY()) == 2)
                valid = true;
    
        return valid;
    
        

    }
    
}
