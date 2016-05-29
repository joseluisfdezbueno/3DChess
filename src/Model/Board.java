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
        
        myModel = new Model(modelPath);
        scale = new Scale(10d);
        translate = new Translate(position);
        translate.setCapability(Group.ALLOW_CHILDREN_WRITE);
        
        scale.addChild(myModel);
        translate.addChild(scale);
        this.addChild(translate);        
        
        myPieces = new ArrayList<>();     
        
        // ------
        
        turn = Colour.White;
        //this.enablePieces(turn);
    }
    
    public Colour getTurn(){
        return turn;
    }
    
    public void changeTurn(){
        if(turn == Colour.White)
            turn = Colour.Black;
        else
            turn = Colour.White;
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
                
            }                                                            
        }        
        
        // si el movimiento se permite actualizamos la matriz de board, posiciones....
        if(allow){
            if(remove)    
                this.removePiece(board[pos.getX()][pos.getY()]); 
            board[pos.getX()][pos.getY()] = piece;
            board[piece.getPosition().getX()][piece.getPosition().getY()] = null; // la posición anterior de la pieza la actualizamos a null
            piece.setPosition(pos);
        }
        
        return allow;
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
