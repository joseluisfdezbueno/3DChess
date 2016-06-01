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
    
    public static void startTimer(Colour colour){
        if(colour == Colour.White)
     //       System.out.print(controlWindow);
            controlWindow.startWhiteTime();
        else
//            System.out.print(controlWindow);            
            controlWindow.startBlackTime();            
    }
    
    public static void stopTimer(Colour colour){
        if(colour == Colour.White)
            controlWindow.stopWhiteTime();
        else
            controlWindow.stopBlackTime();            
    }    
    
    public static void setControlWindow(ControlWindow cW){
        controlWindow = cW;
    }
            
    
}
