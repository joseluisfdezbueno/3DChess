/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import GUI.ControlWindow;

/**
 *
 * @author Jose
 */
public class Game {
    static private ControlWindow controlWindow;

    private Game game = new Game();
    
    /*
    public Game(ControlWindow cW){
        controlWindow = cW;        
    }
    */
    private Game() {
        
    }
    
    public static void setControlWindow(ControlWindow cW){
        controlWindow = cW;
    }
    
    // pone en marcha el cronómetro
    public static void startTimer(Colour colour){
        if(colour == Colour.White)
            controlWindow.startWhiteTime();
        else          
            controlWindow.startBlackTime();            
    }
    
    // para el cronómetro
    public static void stopTimer(Colour colour){
        if(colour == Colour.White)
            controlWindow.stopWhiteTime();
        else
            controlWindow.stopBlackTime();            
    }       
    
    // rota el tablero
    static void rotateView(Colour turn) {
        if(turn == Colour.White)
            controlWindow.rotateView(0);            
        else
            controlWindow.rotateView(1);
    }    
    
    // indica el ganador
    public static void winner(Colour colour){
        if(colour == Colour.Black){
            controlWindow.setTimeBlack("Win Black");
            controlWindow.setTimeWhite("Win Black");
        }else{
            controlWindow.setTimeBlack("Win White");
            controlWindow.setTimeWhite("Win White");
        }
        controlWindow.stopBlackTime();
        controlWindow.stopWhiteTime();
    }
    
    // indica que se han producido tablas
    public static void staleMate(){
        controlWindow.setTimeBlack("STALEMATE");
        controlWindow.setTimeWhite("STALEMATE");
        
        controlWindow.stopBlackTime();
        controlWindow.stopWhiteTime();
    }    
    
}
