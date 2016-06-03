/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.vecmath.Vector3d;

/**
 *
 * @author Jose
 */

public class Scene extends BranchGroup{
    
    private Board board;
    private Pawn pawn;
    private Rook rook;
    private Knight knight;
    private Bishop bishop;    
    private King king;
    private Queen queen;
    
    private Pick pick;    
    private Canvas3D canvas;
    private AppStatus status;
    
    
    public Scene(Canvas3D canvas){
        this.canvas = canvas;
        
        // Se crea la escena
        crearEscena();
    }

    private void crearEscena(){                
        //this.setCapability(Node.ENABLE_PICK_REPORTING);
        //this.setPickable(true);
        
        // Creamos y enlazamos pick
        pick = new Pick(canvas, this);              
        this.addChild(pick);
                                                       
        // Creamos y enlazamos el tablero
        // Board (pathModel, position)
        board = new Board("tablero\\ChessBoard2.obj", new Vector3d(0, 0, 0));
        this.addChild(board);
        
                              
        //############## Black Pieces ##############        
            
        // Pawns        
        pawn = new Pawn("tablero\\peonNegro.obj", Colour.Black, new Position(6,0));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonNegro.obj", Colour.Black, new Position(6,1));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonNegro.obj", Colour.Black, new Position(6,2));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonNegro.obj", Colour.Black, new Position(6,3));
        board.addPiece(pawn);
        
        pawn = new Pawn("tablero\\peonNegro.obj", Colour.Black, new Position(6,4));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonNegro.obj", Colour.Black, new Position(6,5));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonNegro.obj", Colour.Black, new Position(6,6));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonNegro.obj", Colour.Black, new Position(6,7));
        board.addPiece(pawn);               
        
        // Rooks        
        rook = new Rook("tablero\\torreNegra.obj", Colour.Black, new Position(7,0));
        board.addPiece(rook);
        rook = new Rook("tablero\\torreNegra.obj", Colour.Black, new Position(7,7));
        board.addPiece(rook);                                
        
        // Knights        
        knight = new Knight("tablero\\caballoNegro.obj", Colour.Black, new Position(7,1));
        board.addPiece(knight);
        knight = new Knight("tablero\\caballoNegro.obj", Colour.Black, new Position(7,6));
        board.addPiece(knight);        
                
        // Bishops
        bishop = new Bishop("tablero\\alfilNegro.obj", Colour.Black, new Position(7,2));
        board.addPiece(bishop);  
        bishop = new Bishop("tablero\\alfilNegro.obj", Colour.Black, new Position(7,5));
        board.addPiece(bishop);        
                        
        // Queen        
        queen = new Queen("tablero\\reinaNegra.obj", Colour.Black, new Position(7,3));
        board.addPiece(queen);
                
        // King
        king = new King("tablero\\reyNegro.obj", Colour.Black, new Position(7,4));
        board.addPiece(king);        
        
        
        //############## White Pieces ##############          
        
        // Pawns                
        pawn = new Pawn("tablero\\peonBlanco.obj", Colour.White, new Position(1,0));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonBlanco.obj", Colour.White, new Position(1,1));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonBlanco.obj", Colour.White, new Position(1,2));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonBlanco.obj", Colour.White, new Position(1,3));
        board.addPiece(pawn);

        pawn = new Pawn("tablero\\peonBlanco.obj", Colour.White, new Position(1,4));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonBlanco.obj", Colour.White, new Position(1,5));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonBlanco.obj", Colour.White, new Position(1,6));
        board.addPiece(pawn);
        pawn = new Pawn("tablero\\peonBlanco.obj", Colour.White, new Position(1,7));
        board.addPiece(pawn);        
        
        // Rooks
        rook = new Rook("tablero\\torreBlanca.obj", Colour.White, new Position(0,0));
        board.addPiece(rook);
        rook = new Rook("tablero\\torreBlanca.obj", Colour.White, new Position(0,7));
        board.addPiece(rook);                  
        
        // Knights
        knight = new Knight("tablero\\caballoBlanco.obj", Colour.White, new Position(0,1));
        board.addPiece(knight);        
        knight = new Knight("tablero\\caballoBlanco.obj", Colour.White, new Position(0,6));
        board.addPiece(knight);        
                       
        // Bishops        
        bishop = new Bishop("tablero\\alfilBlanco.obj", Colour.White, new Position(0,2));
        board.addPiece(bishop);
        bishop = new Bishop("tablero\\alfilBlanco.obj", Colour.White, new Position(0,5));
        board.addPiece(bishop);        
                
        // Queen        
        queen = new Queen("tablero\\reinaBlanca.obj", Colour.White, new Position(0,3));
        board.addPiece(queen);        
        
        // King        
        king = new King("tablero\\reyBlanco.obj", Colour.White, new Position(0,4));
        board.addPiece(king);        
        
        
        // Inicialmos el board y activamos pick
        board.refreshBoard();        
        pick.setBoard(board);
        
        
//        interact();
        
        
 
    }
    
    public void interact(){
        
        /*
        if(status == status.Nothing){
            status = status.SelectPiece;
            pick.setStatus(status);            
        }
        */        
        
    }

}
