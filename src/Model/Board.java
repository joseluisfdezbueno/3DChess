/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.vecmath.Vector3d;

/**
 *
 * @author Jose
 */
public class Board extends BranchGroup{
    
    private Model myModel;
    private ArrayList<Piece> myPieces;
    private Scale scale;
    private Translate translate;
        
    private Colour turn;
    private Piece[][] board = new Piece[8][8];
    
    public Board (String modelPath, Vector3d position){
        this.setCapability(Node.ENABLE_PICK_REPORTING);
        this.setPickable(true);
        
        myModel = new Model(modelPath, 0);
        scale = new Scale(10d);
        translate = new Translate(position);
        translate.setCapability(Group.ALLOW_CHILDREN_WRITE);
        
        scale.addChild(myModel);
        translate.addChild(scale);
        this.addChild(translate);        
        
        myPieces = new ArrayList<>();     
        
        // ------
        
        turn = Colour.White;
        //Game.startTimer(turn);
        //this.enablePieces(turn);
    }
    
    public Colour getTurn(){
        return turn;
    }
    
    public void changeTurn(){
        Game.stopTimer(turn);
        
        if(turn == Colour.White)
            turn = Colour.Black;
        else
            turn = Colour.White;
        
        Game.startTimer(turn);
    }    
    
    public void addPiece(Piece piece) {
        myPieces.add(piece);
        translate.addChild(piece);
    }
    
    public void removePiece(Piece piece){
        myPieces.remove(piece);
        translate.removeChild(piece);
    }
    
    public void initialPositionPieces(){
        for (Piece p : myPieces)
            p.initialPosition();
    }
    
    public void initialBoard(){
        for (Piece p : myPieces)
            board[p.position.getX()][p.position.getY()] = p;     
    }    
    
    public boolean allowMove(Piece piece, Position pos){
        boolean allow = false;
        boolean remove = false;  // indica si se elimina una pieza que huebiera en pos
        
        if(piece.validMove(pos))
            allow = true;
        
        if(allow){
            if(piece instanceof Pawn){ // si es un peón
                System.out.println("Soy un peón");
                if(pos.getY()-piece.getPosition().getY() != 0){ // si el peón avanza en diagonal 
                    if (board[pos.getX()][pos.getY()]!=null){  // si la pieza contraria es del otro color, podemos movernos y eliminamos la que haya 
                        if(board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                            allow = false;
                        else
                            remove = true;
                    }else
                        allow = false;                
                }else{                                          // el peón avanza de frente
                    if(board[pos.getX()][pos.getY()]!=null)    // si hay alguna pieza no se le permite avanzar
                        allow = false;
                }
                if(allow && ((Pawn)piece).getInStart())     // si el peón está en la casilla de partida
                    ((Pawn)piece).setInStart(false);        // le indicamos que ya no lo está
            }else if(piece instanceof King){
                System.out.println("Soy un Rey");
                if(board[pos.getX()][pos.getY()]!=null)
                    if(board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                        allow = false;
                    else
                        remove = true;                
            }else if(piece instanceof Knight){
                System.out.println("Soy un caballo");
                if(board[pos.getX()][pos.getY()]!=null)
                    if(board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                        allow = false;
                    else
                        remove = true;
            }else if (piece instanceof Rook){
                System.out.println("Soy una torre");
                if(recta(piece.getPosition(),pos))
                {//si nos podemos mover
                    if (board[pos.getX()][pos.getY()]!=null)
                        if(board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                            allow = false;
                        else    
                            remove = true;
                }else allow = false;
            }else if (piece instanceof Bishop){
                System.out.println("Soy un alfil");
                if(diagonal(piece.getPosition(), pos))
                {
                    if (board[pos.getX()][pos.getY()]!=null)
                    {
                        if (board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                            allow =false;
                        else
                            remove = true;
                    }
                }else allow = false;
                
            }else if (piece instanceof Queen){
                System.out.println("Soy la Reina");
                if(recta(piece.getPosition(),pos))
                {//si noifs podemos mover
                    if (board[pos.getX()][pos.getY()]!=null)
                        if(board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                            allow = false;
                        else    
                            remove = true;
                }else if (diagonal(piece.getPosition(), pos))
                {
                    if (board[pos.getX()][pos.getY()]!=null)
                    {
                        if (board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                            allow =false;
                        else
                            remove = true;
                    }
                }else allow = false;
            }
        }        
        
        // si el movimiento se permite actualizamos la matriz de board, posiciones....
        if(allow){
            if(remove)    
                this.removePiece(board[pos.getX()][pos.getY()]); 
            board[pos.getX()][pos.getY()] = piece;
            board[piece.getPosition().getX()][piece.getPosition().getY()] = null; // la posición anterior de la pieza la actualizamos a null
            //piece.setPosition(pos);
        }
        
        return allow;
    }
    
    public boolean recta (Position ini, Position next){
        if ( (ini.getX()<next.getX() || ini.getX()>next.getX() ) && ini.getY()==next.getY()){ //se mueve en el eje X
            return rectaX(ini, next);
        }else if ( (ini.getY()<next.getY() || ini.getY()>next.getY() ) && ini.getX()==next.getX() )//se mueve en el eje Y
        {
            return rectaY(ini, next);
        }
        return false;
    }
    
    public boolean rectaX (Position ini, Position next){
        System.out.println("Recta X");
        int i,j;
        if (ini.getX()<next.getX()){
            i = ini.getX()+1;
            j = next.getX()-1;
        }else{
            i = next.getX()+1;
            j = ini.getX()-1;
        }
        for (; i <= j; i++){
            if ( (board[i][ini.getY()]!=null)) return false;
        }
        
        return true;
    }
    public boolean rectaY(Position ini, Position next){
        System.out.println("Recta Y");
        int i,j;
        if (ini.getY()<next.getY()){
            i = ini.getY()+1;
            j = next.getY()-1;
        }else{
            i = next.getY()+1;
            j = ini.getY()-1;
        }
        for (; i <= j; i++){
            if ((board[ini.getX()][i]!=null) )return false;
        }
        return true;
    }
    public boolean diagonal (Position ini, Position next){
        if(Math.abs(ini.getX()-next.getX())==Math.abs(ini.getY()-next.getY()) && Math.abs(ini.getX()-next.getX())==1)return true;
        if( ini.getX()<next.getX() )//diagonal para arriba
        {
            if (ini.getY()<next.getY() )//diagonal para arriba derecha
            {
                return diagonalArD(ini, next);
            }else{//diagonal para arriba izquierda
                return diagonalArI(ini, next);
            }
        }else{//diagonal para abajo
            if (ini.getY()<next.getY())//diagonal para abajo derecha
            {
                return diagonalAbD(ini, next);
            }else{//diagonal para abajo izquierda
                return diagonalAbI(ini, next);
            }
        }
    }
    public boolean diagonalArD (Position ini, Position next){
        int i,j,k,l;
        i = ini.getX()+1;
        j = ini.getY()+1;
        k = next.getX()-1;
        l = next.getY()-1;
        do{
            if ( (board[i][j]!=null) )return false;
            i++;
            j++;
        }while(i<=k && j<=l);
        
        return true;
    }
    public boolean diagonalArI(Position ini, Position next){
        int i,j,k,l;
        i = ini.getX()+1;
        j = ini.getY()-1;
        k = next.getX()-1;
        l = next.getY()+1;
        do{
            if ( (board[i][j]!=null) )return false;
            i++;
            j--;
        }while(i<=k && j>=l);
        return true;
    }
    public boolean diagonalAbD (Position ini, Position next){
        int i,j,k,l;
        i = ini.getX()-1;
        j = ini.getY()+1;
        k = next.getX()+1;
        l = next.getY()-1;
        do{
            if ( (board[i][j]!=null) )return false;
            i--;
            j++;
        }while(i>=k && j<=l);
        return true;
    }
    public boolean diagonalAbI(Position ini, Position next){
        int i,j,k,l;
        i = ini.getX()-1;
        j = ini.getY()-1;
        k = next.getX()+1;
        l = next.getY()+1;
        do{
            if ( (board[i][j]!=null) )return false;
            i--;
            j--;
        }while(i>=k && j>=l);
        return true;
    }
    /*
    void enablePick(boolean onOff){
        if(onOff){
            this.setPickable(true);
        }else{
            this.setPickable(false);                        
        }        
    }
    
    void enablePieces(Colour colour){
        for(Piece p : myPieces)
            if(p.getColour() == colour)
                p.enablePick(true);
            else
                p.enablePick(false);        
    }
    
    void disablePieces(){
        for(Piece p : myPieces)           
            p.enablePick(false);
    }    
    */
    
    
}
