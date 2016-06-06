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
    
    // controla si se permite hacer los enroques
    private boolean allowRWCastling; // blancas a derechas
    private boolean allowLWCastling;
    private boolean allowRBCastling;
    private boolean allowLBCastling;
    
    // conotrola los jaques
    private boolean whiteCheck;
    private boolean blackCheck;
    
    // posiciones reyes
    private Position whiteKingPosition;
    private Position blackKingPosition;
    
    // controla el fin del juego
    private boolean endGame;
    
    public Board (String modelPath, Vector3d position){
        this.setCapability(Node.ENABLE_PICK_REPORTING);
        this.setPickable(true);
        
        myModel = new Model(modelPath, 0);
        scale = new Scale(10d);
        translate = new Translate(position);
        translate.setCapability(Group.ALLOW_CHILDREN_WRITE);
        translate.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        
        scale.addChild(myModel);
        translate.addChild(scale);
        this.addChild(translate);        
        
        myPieces = new ArrayList<>();     
               
        turn = Colour.White;
        allowRWCastling = true;
        allowLWCastling = true;
        allowRBCastling = true;
        allowLBCastling = true;        
        //Game.startTimer(turn);
        //this.enablePieces(turn);
        whiteCheck = false;
        blackCheck = false;
        
        whiteKingPosition = new Position(0, 4);
        blackKingPosition = new Position(7, 4);
        
        endGame = false;
    }
    
    // devuelve si el juego se ha acabado
    public boolean getEndGame(){
        return endGame;
    }
    
    // devuelve de quien es el turno actual del juego
    public Colour getTurn(){
        return turn;
    }
    
    // realiza un cambio de turno
    public void changeTurn(){
        Game.stopTimer(turn);
        
        if(turn == Colour.White)
            turn = Colour.Black;
        else
            turn = Colour.White;
        
        Game.startTimer(turn);
    }    
    
    // añade una pieza al grafo de escena, y al array de piezas
    public void addPiece(Piece piece) {
        myPieces.add(piece);
        translate.addChild(piece);
    }
    
    // elimina una pieza del grafo de escena y del array de piezas
    public void removePiece(Piece piece){
        myPieces.remove(piece);
        translate.removeChild(piece);
    }
    
    /*
    // inicia las posiciones iniiciales de las pieza
    public void initialPositionPieces(){
        for (Piece p : myPieces)
            p.initialPosition();
    }
    */
    
    // actualiza la matriz del tablero con las piezas
    public void refreshBoard(){
        for (Piece p : myPieces)
            board[p.position.getX()][p.position.getY()] = p;     
    }    
    
    // 
    public void addPieceBoard(Piece piece){
        board[piece.getPosition().getX()][piece.getPosition().getY()] = piece;  
    }
    
    // devuelve si se puede realizar el movimiento de una pieza a una posición en la partida
    public boolean allowMove(Piece piece, Position pos){
        boolean allow = false;
        boolean remove = false;  // indica si se elimina una pieza que huebiera en pos
        Position kingPosition = null;
        
        if(piece.validMove(pos)) // comprobamos si el movimiento es valido en la pieza, sin tener en cuenta el resto
            allow = true;
        
        if(allow){
            if(piece instanceof Pawn){ // si es un peón
                System.out.println("Soy un peón");
                if(pos.getY()-piece.getPosition().getY() != 0){ // si el peón avanza en diagonal 
                    if (board[pos.getX()][pos.getY()]!=null){  // si la pieza contraria es del otro color, podemos movernos y eliminamos la que haya 
                        if(board[pos.getX()][pos.getY()].getColour() == piece.getColour()){
                            allow = false;
                            System.out.println("Peon del mismo color");
                        }else{
                            remove = true;
                            System.out.println("Estoy comiendo");
                        }
                    }else{
                        System.out.println("Estoy comiendo");                        
                        allow = false;                
                    }
                }else{                                          // el peón avanza de frente
                    if(((Pawn)piece).getInStart() && !recta(piece.getPosition(),pos)) // si avanzamos 2 pasos en la salida y hay alguien en medio, no se permite
                        allow = false;
                        
                    if(board[pos.getX()][pos.getY()]!=null)    // si hay alguna pieza no se le permite avanzar
                        allow = false;
                }
                
            }else if(piece instanceof King){
                System.out.println("Soy un Rey");
                
                // comprobamos que podemos movernos a la posición adyacente
                if(board[pos.getX()][pos.getY()]!=null)
                    if(board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                        allow = false;
                    else
                        remove = true;
                
                // queremos hacer enroque
                if(pos.getY() - piece.getPosition().getY() == -2){ // enroque a izquerdas
                    if(piece.getColour() == Colour.White && this.allowLWCastling && !this.whiteCheck // blancas
                        && !checkInPosition(new Position(0, 2), Colour.Black) && !checkInPosition(new Position(0, 3), Colour.Black)){
                            if(this.recta(piece.getPosition(), new Position(0, 0))){
                                board[0][0].setPosition(new Position(0, 3));
                                board[0][3] = board[0][0];
                                board[0][0] = null;
                            }else
                                allow = false;
                    //}else
                     //   allow = false;
                
                    }else if(piece.getColour() == Colour.Black && this.allowLBCastling && !this.blackCheck // negras
                        && !checkInPosition(new Position(7, 2), Colour.White) && !checkInPosition(new Position(7, 3), Colour.White)){
                            if(this.recta(piece.getPosition(), new Position(7, 0))){
                                board[7][0].setPosition(new Position(7, 3));
                                board[7][3] = board[0][0];
                                board[7][0] = null;                                                                            
                            }else
                                allow = false;
                            
                    }else
                        allow = false;
                }else if(pos.getY() - piece.getPosition().getY() == 2){  // enroque a derechas
                    if(piece.getColour() == Colour.White && this.allowRWCastling && !this.whiteCheck  // blancas
                        && !checkInPosition(new Position(0, 5), Colour.Black) && !checkInPosition(new Position(0, 6), Colour.Black)){
                            if(this.recta(piece.getPosition(), new Position(0, 7))){
                                board[0][7].setPosition(new Position(0, 5));
                                board[0][5] = board[0][7];
                                board[0][7] = null;
                            }else
                                allow = false;
                    //else
                    //    allow = false;
                
                    }else if(piece.getColour() == Colour.Black && this.allowRBCastling && !this.blackCheck    // negras
                        && !checkInPosition(new Position(7, 5), Colour.White) && !checkInPosition(new Position(7, 6), Colour.White)){
                            if(this.recta(piece.getPosition(), new Position(7, 7))){
                                board[7][7].setPosition(new Position(7, 5));
                                board[7][5] = board[7][7];
                                board[7][7] = null;                    
                            }else
                                allow = false;                                                               
                    }else
                        allow = false;
                    
                }

            }else if(piece instanceof Knight){
                System.out.println("Soy un caballo");
                if(board[pos.getX()][pos.getY()]!=null) // comprobamos que podemos movernos a la posición
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
                if(diagonal(piece.getPosition(), pos))  // comprobamos que se puede mover
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
                if(piece.getPosition().getX() == pos.getX() || piece.getPosition().getY() == pos.getY()){ // si nos movemos linealmente
                    if(recta(piece.getPosition(),pos)){       //si nos podemos mover
                        if (board[pos.getX()][pos.getY()]!=null)
                            if(board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                                allow = false;
                            else    
                                remove = true;
                    }
                }else{                                                     // si nos movemos diagonalmente
                    if(diagonal(piece.getPosition(), pos)){
                        if (board[pos.getX()][pos.getY()]!=null)
                            if (board[pos.getX()][pos.getY()].getColour() == piece.getColour())
                                allow =false;
                            else
                                remove = true;                        
                    }
                }
            }
        }       
        
        // Comprobamos si no habia jaque, y en tal caso, comprobamos que sólo podemos movernos a una casilla sin amenaza
        if(allow && (turn == Colour.White && !whiteCheck || turn == Colour.Black && !blackCheck)){
            Position auxPos = piece.getPosition();
            Piece auxPiece = null;            
                        
            // simulamos el board que se va a producir si movemos la pieza seleccionada
            if(remove){
                auxPiece = board[pos.getX()][pos.getY()];                            
                myPieces.remove(auxPiece);
            }
            
            board[pos.getX()][pos.getY()] = piece;
            board[piece.getPosition().getX()][piece.getPosition().getY()] = null;
            piece.setPositionNotDraw(pos);
            
            if(turn == Colour.White && !whiteCheck)
                kingPosition = this.whiteKingPosition;
            else
                kingPosition = this.blackKingPosition;
                        
            if(piece instanceof King)
                kingPosition = piece.getPosition();
            
            if(turn == Colour.White && !whiteCheck){
                if(this.checkInPosition(kingPosition, Colour.Black))
                    allow = false;
            }else{
                if(this.checkInPosition(kingPosition, Colour.White))
                    allow = false;
            }
                          
            if(!allow){
                System.out.println("Si movemos a esa casilla nos dan jaque mate");
            }
            piece.setPositionNotDraw(auxPos);
            board[piece.getPosition().getX()][piece.getPosition().getY()] = piece;
            if(remove){
                board[pos.getX()][pos.getY()] = auxPiece;
                myPieces.add(auxPiece);
            }else 
                board[pos.getX()][pos.getY()] = null;                        
        }
        
        
        // Comprobamos si había jaque y si sigue habiendo jaque
        if(allow && turn == Colour.White && whiteCheck){ // si es el turno de las blancas
            Position auxPos = piece.getPosition();
            Piece auxPiece = null;
            kingPosition = this.whiteKingPosition;
                        
            // simulamos el board que se va a producir si movemos la pieza seleccionada
            if(remove){
                auxPiece = board[pos.getX()][pos.getY()];
                myPieces.remove(auxPiece);
            }
            board[pos.getX()][pos.getY()] = piece;
            board[piece.getPosition().getX()][piece.getPosition().getY()] = null;
            piece.setPositionNotDraw(pos);
                        
            if(piece instanceof King)
                kingPosition = piece.getPosition();
            if(this.checkInPosition(kingPosition, Colour.Black))
                allow = false;
            else
                whiteCheck = false;                
            
            if(!allow){
                System.out.println("Sigue en jaque la reina blanca");
            }
            // volvemos el board a su forma original
            piece.setPositionNotDraw(auxPos);
            board[piece.getPosition().getX()][piece.getPosition().getY()] = piece;
            if(remove){
                board[pos.getX()][pos.getY()] = auxPiece;
                myPieces.add(auxPiece);
            }else 
                board[pos.getX()][pos.getY()] = null;
        }    
        
        if(allow && turn == Colour.Black && blackCheck){ // si es el turno de las negras
            Position auxPos = piece.getPosition();            
            Piece auxPiece = null;
            kingPosition = this.blackKingPosition;
            
            if(remove){
                auxPiece = board[pos.getX()][pos.getY()];
                myPieces.remove(auxPiece);
            }                
                        
            board[pos.getX()][pos.getY()] = piece;
            board[piece.getPosition().getX()][piece.getPosition().getY()] = null;            
            piece.setPositionNotDraw(pos);            
            
            if(piece instanceof King)
                kingPosition = piece.getPosition();            
            if(this.checkInPosition(kingPosition, Colour.White))
                allow = false;        
            
            if(!allow){
                System.out.println("Sigue en jaque la reina negra");
            }            
            else
                blackCheck = false;
            
            piece.setPositionNotDraw(auxPos);
            board[piece.getPosition().getX()][piece.getPosition().getY()] = piece;
            if(remove){
                board[pos.getX()][pos.getY()] = auxPiece;
                myPieces.add(auxPiece);            
            }else
                board[pos.getX()][pos.getY()] = null;            
        }
                                
        // si finalmente el movimiento se permite....
        if(allow){            
            
            if(piece instanceof Rook){ // si se mueve una torre, dejamos de poder hacer un enroque hacia su lado
                if(piece.getPosition().getX() == 0 && piece.getPosition().getY() == 0 && this.allowLWCastling)
                    allowLWCastling = false;
                if(piece.getPosition().getX() == 0 && piece.getPosition().getY() == 7 && this.allowRWCastling)
                    allowRWCastling = false;
                if(piece.getPosition().getX() == 7 && piece.getPosition().getY() == 0 && this.allowLBCastling)
                    allowLBCastling = false;
                if(piece.getPosition().getX() == 7 && piece.getPosition().getY() == 7 && this.allowRBCastling)
                    allowRBCastling = false;
            }
            
            // Acutalizamos el movimiento de la pieza
            if(remove)    
                this.removePiece(board[pos.getX()][pos.getY()]);            
            board[pos.getX()][pos.getY()] = piece;
            board[piece.getPosition().getX()][piece.getPosition().getY()] = null; // la posición anterior de la pieza la actualizamos a null
            piece.setPosition(pos);
            
             // Si la pieza es un peón
            if(piece instanceof Pawn){             
                if(((Pawn)piece).getInStart())     // si el peón estaba en la casilla de partida
                    ((Pawn)piece).setInStart(false);        // le indicamos que ya no lo está
                
                if(pos.getX()==7 || pos.getX()==0){  // si ha llegado al final, lo transformamos en reina
                    transform((Pawn)piece);
                }            
            }
            
            // Si la pieza movida es un rey, actualizamos las posiciones de las variables del board            
            if(piece instanceof King){                                            
                if(piece.getColour() == Colour.White){
                    this.whiteKingPosition = piece.getPosition();
                    if(allowRWCastling || allowLWCastling){ // dejamos de poder hacer un enroque en el futuro
                        this.allowRWCastling = false;
                        this.allowLWCastling = false;
                    }                                            
                }else if(piece.getColour() == Colour.Black){
                    this.blackKingPosition = piece.getPosition();                 
                    if(allowRBCastling || allowLBCastling){ // dejamos de poder hacer un enroque en el futuro
                        this.allowRBCastling = false;
                        this.allowLBCastling = false;
                    }                     
                }    
            }
            
            // Comprobamos si estamos produciendo un jaque al rival
            if(turn == Colour.White){
                this.blackCheck = checkInPosition(this.blackKingPosition, Colour.White);
                if(blackCheck){
                    if(this.isThereCheckMate(piece.getPosition(), blackKingPosition,  Colour.White)){
                        Game.winner(Colour.White);
                        endGame = true;
                        //Scene.setPickMode(AppStatus.Nothing);
                        System.out.println("Jaque MATE a las NEGRAS");
                    }
                    System.out.println("Jaque a las negras");
                }
            }
            
            if(turn == Colour.Black){
                this.whiteCheck = checkInPosition(this.whiteKingPosition, Colour.Black);
                if(whiteCheck){
                    if(this.isThereCheckMate(piece.getPosition(), whiteKingPosition,  Colour.Black)){
                        Game.winner(Colour.Black);
                        endGame = true;
                        //Scene.setPickMode(AppStatus.Nothing);
                        System.out.println("Jaque MATE a las BLANCAS");                        
                    }
                    System.out.println("Jaque a las blancas");
                }
            }
            
            if(this.isThereStaleMate()){  // comprobamos si hay tablas
                //Scene.setPickMode(AppStatus.Nothing);
                endGame = true;
                Game.staleMate();
            }
            
        }            
        return allow;    
    }
    
    // devuelve si se puede mover una pieza en la misma fila o columna
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
    
    // devuelve si se puede mover una pieza diagonalmente
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
    
    // transforma el peón en reina al llegar a la última casilla
    public void transform(Pawn pawn){
        Queen queen;
        
        System.out.println("peon se transforma");
        
        if(pawn.getColour() == Colour.Black)
            queen = new Queen("tablero\\reinaNegra.obj", Colour.Black, new Position(pawn.getPosition().getX(), pawn.getPosition().getY()));
        else
            queen = new Queen("tablero\\reinaBlanca.obj", Colour.White, new Position(pawn.getPosition().getX(), pawn.getPosition().getY()));
        
        this.removePiece(pawn);
        
        this.addPiece(queen);
        this.addPieceBoard(queen);      
    }    
    
    // Devuelve si las piezas de un color dan jaque a una posición dada 
    public boolean checkInPosition(Position pos, Colour colour){ // devuelve si hay jaque a una posición por un color
        boolean check = false;
        
        for(Piece piece : myPieces)
            if(piece.getColour() == colour){
                if(piece.validMove(pos)){
                    if(piece instanceof Pawn){
                        
                        if(Math.abs(piece.getPosition().getY() - pos.getY()) == 1){
                            if(colour == Colour.Black){
                                if(pos.getX()-piece.getPosition().getX() == -1){
                                    check = true;
                                    System.out.println("\n Peon de color " + piece.getColour().toString() + " está dando jaque");
                                }
                            }else
                                if(pos.getX()-piece.getPosition().getX() == 1){
                                    check = true;
                                    System.out.println("\n Peon de color " + piece.getColour().toString() + " está dando jaque");
                                }
                        }                                                                        
                    }else if(piece instanceof Rook){
                        if(recta(piece.getPosition(),pos)){
                            check = true;
                            System.out.println("\n Torre de color " + piece.getColour().toString() + " está dando jaque");
                        }
                    }else if(piece instanceof Knight){
                        check = true;
                        System.out.println("\n Caballo de color " + piece.getColour().toString() + " está dando jaque");
                    }else if(piece instanceof Bishop){
                        if(diagonal(piece.getPosition(), pos)){
                            check = true;
                            System.out.println("\n Alfil de color " + piece.getColour().toString() + " está dando jaque");
                        }
                    }else if(piece instanceof Queen){                        
                        if(piece.getPosition().getX() == pos.getX() || piece.getPosition().getY() == pos.getY()){
                            if(recta(piece.getPosition(),pos)){
                                check = true;
                                System.out.println("\n Reina de color " + piece.getColour().toString() + " y posicion "  + piece.getPosition().getX() + piece.getPosition().getY() + " está dando jaque en línea");
                            }
                        }else
                            if(diagonal(piece.getPosition(), pos)){
                                check = true;                        
                                System.out.println("\n Reina de color " + piece.getColour().toString() + " y posicion " + piece.getPosition().getX() + piece.getPosition().getY() + " está dando jaque en diagonal");
                            }                          
                    }                                        
                }                               
            }                
                
        return check;
    }
    
    // Devuelve un arraylist con las posiciones intermedias entre 2 posiciones dadas
    public ArrayList<Position> getPossBetweenPoss(Position ini, Position next){
        ArrayList<Position> positions = new ArrayList();
        int i, j, k, l;
        
        if((ini.getX()==next.getX() && ini.getY()!=next.getY()) || (ini.getX()!=next.getX() && ini.getY()==next.getY())){ // línea recta
            if(ini.getX()==next.getX()){ // se mueve en la línea horizontal                        
                if (ini.getY()<next.getY()){
                    i = ini.getY()+1;
                    j = next.getY()-1;
                }else{
                    i = next.getY()+1;
                    j = ini.getY()-1;
                }
                for (; i <= j; i++)
                    positions.add(new Position(ini.getX(), i));
           
            }else{   // se mueve en la línea vertical
                if (ini.getX()<next.getX()){
                    i = ini.getX()+1;
                    j = next.getX()-1;
                }else{
                    i = next.getX()+1;
                    j = ini.getX()-1;
                }
                for (; i <= j; i++)
                    positions.add(new Position(i, ini.getY()));
                                                                     
            }
        
        }else if(Math.abs(ini.getX()-next.getX())==Math.abs(ini.getY()-next.getY())
                && (Math.abs(ini.getX()-next.getX())==Math.abs(ini.getY()-next.getY()) && Math.abs(ini.getX()-next.getX())==1)){ // diagonal
                                  
            if( ini.getX()<next.getX() ){  //diagonal para arriba
                if (ini.getY()<next.getY() ){  //diagonal para arriba derecha
                    i = ini.getX()+1;
                    j = ini.getY()+1;
                    k = next.getX()-1;
                    l = next.getY()-1;
                    do{
                        positions.add(new Position(i, j));
                        i++;
                        j++;
                    }while(i<=k && j<=l);
            
                }else{  // arriba a la izquierda
                    i = ini.getX()+1;
                    j = ini.getY()-1;
                    k = next.getX()-1;
                    l = next.getY()+1;
                    do{
                        positions.add(new Position(i, j));
                        i++;
                        j--;
                    }while(i<=k && j>=l);                    
                }
            }else{ //diagonal para abajo
                if (ini.getY()<next.getY() ){ // abajo a la derecha
                    i = ini.getX()-1;
                    j = ini.getY()+1;
                    k = next.getX()+1;
                    l = next.getY()-1;
                    do{
                        positions.add(new Position(i, j));
                        i--;
                        j++;
                    }while(i>=k && j<=l);                                                            
                }else{   // abajo a la izquierda
                    i = ini.getX()-1;
                    j = ini.getY()-1;
                    k = next.getX()+1;
                    l = next.getY()+1;
                    do{
                        positions.add(new Position(i, j));
                        i--;
                        j--;
                    }while(i>=k && j>=l);                                        
                }                                
            }
                
        }else
            positions = null;
        
        
        return positions;
    }
    
    // devuelve si podemos mover una pieza, donde no sufra jaque nuestro rey
    public boolean moveWithoutCheck(Piece piece, Position pos, boolean remove){
        boolean allow = true;
        //boolean remove = false;
        Position kingPosition = null;
        Piece auxPiece = null;
        Position auxPos = piece.getPosition();   
                
        if(piece.getColour() == Colour.White) // si es el turno de las blancas
            kingPosition = this.whiteKingPosition;
                        
            // simulamos el board que se va a producir si movemos la pieza seleccionada
            if(remove){
                auxPiece = board[pos.getX()][pos.getY()];
                myPieces.remove(auxPiece);
            }
            board[pos.getX()][pos.getY()] = piece;
            board[piece.getPosition().getX()][piece.getPosition().getY()] = null;
            piece.setPositionNotDraw(pos);
                        
            if(piece instanceof King)
                kingPosition = piece.getPosition();
            if(piece.getColour() == Colour.White){
                if(this.checkInPosition(kingPosition, Colour.Black))
                    allow = false;
            }else
                if(this.checkInPosition(kingPosition, Colour.White))
                    allow = false;                
            //else
            //    whiteCheck = false;
            
            // volvemos el board a su forma original
            piece.setPositionNotDraw(auxPos);
            board[piece.getPosition().getX()][piece.getPosition().getY()] = piece;
            if(remove){
                board[pos.getX()][pos.getY()] = auxPiece;
                myPieces.add(auxPiece);
            }else 
                board[pos.getX()][pos.getY()] = null;
        
        return allow;
    }
    
               
    // devuelve si el rey puede moverse a alguna posición a su alrededor. Colour: color del rey
    public boolean anyMoveForKing(Colour colour){
        boolean allow = false;
        Position kingPosition = null;
        Piece piece = null;
        int xPosition;
        int yPosition;                
        
        if(colour == Colour.White){
            kingPosition = this.whiteKingPosition;            
        }else
            kingPosition = this.blackKingPosition;
        
        piece = board[kingPosition.getX()][kingPosition.getY()];
        
        // arriba a la izq
        xPosition = kingPosition.getX()+1;
        yPosition = kingPosition.getY()-1;
                
        if(xPosition >= 0 && yPosition >= 0){
            if(board[xPosition][yPosition]!=null){ // arriba a la izq
                if(board[xPosition][yPosition].getColour() == piece.getColour())
                    allow = false;
                else
                    allow = moveWithoutCheck(piece, kingPosition, true);
            }else
                allow = moveWithoutCheck(piece, kingPosition, false);
        }
        
        // arriba
        xPosition = kingPosition.getX()+1;
        yPosition = kingPosition.getY();
        if(xPosition >= 0 && yPosition >= 0){
            
            if(board[xPosition][yPosition]!=null){ // arriba a la izq
                if(board[xPosition][yPosition].getColour() == piece.getColour())
                    allow = false;
                else
                    allow = moveWithoutCheck(piece, kingPosition, true);
            }else
                allow = moveWithoutCheck(piece, kingPosition, false);
        }
        
        // arriba a la der
        xPosition = kingPosition.getX()+1;
        yPosition = kingPosition.getY()+1;
        if(xPosition >= 0 && yPosition >= 0){
            
            if(board[xPosition][yPosition]!=null){ // arriba a la izq
                if(board[xPosition][yPosition].getColour() == piece.getColour())
                    allow = false;
                else
                    allow = moveWithoutCheck(piece, kingPosition, true);
            }else
                allow = moveWithoutCheck(piece, kingPosition, false);
        }
        
        // a la izq
        xPosition = kingPosition.getX();
        yPosition = kingPosition.getY()-1;
        if(xPosition >= 0 && yPosition >= 0){
            
            if(board[xPosition][yPosition]!=null){ // arriba a la izq
                if(board[xPosition][yPosition].getColour() == piece.getColour())
                    allow = false;
                else
                    allow = moveWithoutCheck(piece, kingPosition, true);
            }else
                allow = moveWithoutCheck(piece, kingPosition, false);
        }
        
        // a la der
        xPosition = kingPosition.getX();
        yPosition = kingPosition.getY()+1;
        if(xPosition >= 0 && yPosition >= 0){
            
            if(board[xPosition][yPosition]!=null){ // arriba a la izq
                if(board[xPosition][yPosition].getColour() == piece.getColour())
                    allow = false;
                else
                    allow = moveWithoutCheck(piece, kingPosition, true);
            }else
                allow = moveWithoutCheck(piece, kingPosition, false);
        }
        
        // abajo a la izq
        xPosition = kingPosition.getX()-1;
        yPosition = kingPosition.getY()-1;
        if(xPosition >= 0 && yPosition >= 0){
            
            if(board[xPosition][yPosition]!=null){ // arriba a la izq
                if(board[xPosition][yPosition].getColour() == piece.getColour())
                    allow = false;
                else
                    allow = moveWithoutCheck(piece, kingPosition, true);
            }else
                allow = moveWithoutCheck(piece, kingPosition, false);
        }
        
        // abajo
        xPosition = kingPosition.getX()-1;
        yPosition = kingPosition.getY();
        if(xPosition >= 0 && yPosition >= 0){
            
            if(board[xPosition][yPosition]!=null){ // arriba a la izq
                if(board[xPosition][yPosition].getColour() == piece.getColour())
                    allow = false;
                else
                    allow = moveWithoutCheck(piece, kingPosition, true);
            }else
                allow = moveWithoutCheck(piece, kingPosition, false);
        }
        
        // abajo a la der
        xPosition = kingPosition.getX()-1;
        yPosition = kingPosition.getY()+1;
        if(xPosition >= 0 && yPosition >= 0){
            
            if(board[xPosition][yPosition]!=null){ // arriba a la izq
                if(board[xPosition][yPosition].getColour() == piece.getColour())
                    allow = false;
                else
                    allow = moveWithoutCheck(piece, kingPosition, true);
            }else
                allow = moveWithoutCheck(piece, kingPosition, false);
        }

        return allow;
    }    
    
    
    // devuelve si hay o no jaque mate
    public boolean isThereCheckMate(Position checkPosition, Position kingPosition, Colour colour){
        boolean checkMate = true;
        ArrayList<Position> positions = new ArrayList();
                
        // comprobamos si alguna de las piezas puede comerse a la que nos da jaque
        if(colour == Colour.Black) // si el jaque es de las negras a las blancas
            if(this.checkInPosition(checkPosition, Colour.White))
                checkMate = false;
        else
            if(this.checkInPosition(checkPosition, Colour.Black))
                checkMate = false;
        
        // si no podemos comernos la pieza que nos da jaque, vamos, si hay alguna pieza que podamos poner por medio, entre ella y el rey
        if(checkMate){
            positions = getPossBetweenPoss(checkPosition, kingPosition); // obtenemos las posiciones intermedias
            if(positions != null){
                for(Position p : positions){
                    if(colour == Colour.Black) // si el jaque es de las negras a las blancas
                        if(this.checkInPosition(p, Colour.White))
                            checkMate = false;
                    else
                        if(this.checkInPosition(p, Colour.Black))
                            checkMate = false;                
                }
            }
        }
        
        // comprobamos si con algún movimiento del rey deja de haber jaque mate
        if(checkMate){
            if(colour == Colour.Black) // si el jaque es de las negras a las blancas
                if(anyMoveForKing(Colour.White))
                    checkMate = false;
            else
                if(anyMoveForKing(Colour.Black))
                    checkMate = false;                        
        }                                        
        
        return checkMate;
    }
    
    
    // Devuelve si se ha producido tablas o no
    public boolean isThereStaleMate(){
        boolean staleMate = false;
        Piece whiteBishopPiece = null;
        Piece blackBishopPiece = null;        
        
        boolean whitePawn = false, whiteRook = false, whiteKnight = false, whiteBishop = false, whiteQueen = false, whiteKnight2 = false, whiteBishop2 = false;
        boolean blackPawn = false, blackRook = false, blackKnight = false, blackBishop = false, blackQueen = false, blackKnight2 = false, blackBishop2 = false;
        
        // comprobamos las piezas que tenemos en el tablero
        for(Piece p : myPieces){
            if(p.getColour() == Colour.White){
                if(p instanceof Pawn)
                    whitePawn = true;
                else if(p instanceof Rook)
                    whiteRook = true;
                else if(p instanceof Knight && !whiteKnight)
                    whiteKnight = true;
                else if(p instanceof Knight)
                    whiteKnight2 = true;                
                else if(p instanceof Bishop && !whiteBishop){
                    whiteBishop = true;
                    whiteBishopPiece = p;
                }else if(p instanceof Bishop)
                    whiteBishop2 = true;                
                else if(p instanceof Queen)
                    whiteQueen = true;                
            }else{
                if(p instanceof Pawn)
                    blackPawn = true;
                else if(p instanceof Rook)
                    blackRook = true;
                else if(p instanceof Knight && !blackKnight)
                    blackKnight = true;
                else if(p instanceof Knight)
                    blackKnight2 = true;                
                else if(p instanceof Bishop && !blackBishop){
                    blackBishop = true;
                    blackBishopPiece = p;                    
                }else if(p instanceof Bishop)
                    blackBishop2 = true;                
                else if(p instanceof Queen)
                    blackQueen = true;                    
                                                
            }                                
        }
        
        if(!whitePawn && !whiteRook && !whiteKnight && !whiteBishop && !whiteQueen   // rey solo contra rey
                && !blackPawn && !blackRook && !blackKnight && !blackBishop && !blackQueen)
            staleMate = true;
        else if(!whitePawn && !whiteRook && !whiteKnight && !whiteBishop && !whiteQueen   // solo rey blanco contra rey y caballo negros
                && !blackPawn && !blackRook && blackKnight && !blackKnight2 && !blackBishop && !blackQueen)
            staleMate = true;
        else if(!whitePawn && !whiteRook && whiteKnight && !whiteKnight2 && !whiteBishop && !whiteQueen   // rey y caballo blancos, solo contra rey negro
                && !blackPawn && !blackRook && !blackKnight && !blackBishop && !blackQueen)
            staleMate = true;
        else if(!whitePawn && !whiteRook && !whiteKnight && !whiteBishop && !whiteQueen   // solo rey blanco contra rey y alfil negros
                && !blackPawn && !blackRook && !blackKnight && blackBishop && !blackBishop2 && !blackQueen)
            staleMate = true;
        else if(!whitePawn && !whiteRook && !whiteKnight && whiteBishop && !whiteBishop2 && !whiteQueen   // rey y alfil blancos, solo contra rey negro
                && !blackPawn && !blackRook && !blackKnight && !blackBishop && !blackQueen)
            staleMate = true;
        
        else if(!whitePawn && !whiteRook && !whiteKnight && whiteBishop && !whiteBishop2 && !whiteQueen   // rey y alfil blancos, solo contra rey negro y alfil negro y en las casillas del mismo color
                && !blackPawn && !blackRook && !blackKnight && blackBishop && !blackBishop2  && !blackQueen){
            if((whiteBishopPiece.getInitialPosition().getY() == 2 && blackBishopPiece.getInitialPosition().getY() == 5) // casillas negras
                || (whiteBishopPiece.getInitialPosition().getY() == 5 && blackBishopPiece.getInitialPosition().getY() == 2)) // casillas blancas
                staleMate = true;
        }
            
        
        return staleMate;
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


// para sacar el jaque mate, antes de nada saber la pieza (posicion) que nos esta dando jaque...luego, comprobar que no hay ningún jaque de nuestras piezas a la posición que nos está dando el jaque a nosotros
// después, comprobar las casillas que hay entre la posición que nos da jaque y nuestro rey, guardar en un arraylist de posiciones, comprobar si nuestras piezas dan jaque a dichas posiciones, si alguna da, es que no es mate
// en última instancia comprobar los movimientos de nuestro rey a su alrededor, si en alguno deja de haber jaque del enemigo