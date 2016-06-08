/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.sun.j3d.utils.pickfast.PickCanvas;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Point3d;

/**
 *
 * @author Jose
 */
public class Pick extends Behavior{
    private AppStatus status;
    private Piece selected;
    private Board board;
    
    private WakeupCondition condicion;
    private PickCanvas pickCanvas;
    private Canvas3D canvas;
    
    public Pick (Canvas3D canvas, BranchGroup rama){
        this.canvas = canvas;
        condicion = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
        pickCanvas = new PickCanvas(canvas, rama);
        pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
        //pickCanvas.setFlags(PickInfo.SCENEGRAPHPATH | PickInfo.CLOSEST_INTERSECTION_POINT); // 
        this.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 10000.0));
        status = AppStatus.SelectPiece;
    }
    
    @Override
    public void initialize(){
        // Activamos la funcionalidad
        setEnable(true);
        wakeupOn(condicion);
    }
    
    public void setStatus(AppStatus status){        
        this.status = status;
        
        if(status==AppStatus.SelectBox)
            pickCanvas.setFlags(PickInfo.SCENEGRAPHPATH | PickInfo.CLOSEST_INTERSECTION_POINT);
        else if(status==AppStatus.SelectPiece)
            pickCanvas.setFlags(ALLOW_BOUNDS_READ); // revisar constante
        else if(status==AppStatus.Nothing)
            setEnable(false);
    }
    
    public void setBoard(Board board){
        this.board = board;
    }
    
    @Override
    public void processStimulus(Enumeration cond){
        WakeupOnAWTEvent c = (WakeupOnAWTEvent) cond.nextElement();
        AWTEvent[] e = c.getAWTEvent();
        MouseEvent raton = (MouseEvent) e[0];
        Point3d point;        
        PickInfo pi;        
        
        switch(raton.getID()){
            case MouseEvent.MOUSE_CLICKED:
                switch (status){
                  
                    case SelectPiece:
                        pickCanvas.setShapeLocation(raton);
                        pi = pickCanvas.pickClosest();
                        if(pi != null){               
                            //System.out.println(pi.getNode().getParent().getParent());
                            //System.out.println(pi.getNode().getParent().getParent().getParent().getParent().getParent()); // shape3d -> branchGroup -> Model -> Scale -> Translate -> Piece
                            
                            if(pi.getNode().getParent().getParent().getParent().getParent().getParent() instanceof Piece){
                                selected = (Piece)pi.getNode().getParent().getParent().getParent().getParent().getParent();
                                if(selected.getColour() == board.getTurn()){
                                    ((Piece)pi.getNode().getParent().getParent().getParent().getParent().getParent()).upPiece();

              //              board.disablePieces();                            
               //             board.enablePick(true);
                            
                                    setStatus(status.SelectBox);
                                }
                            }
                        }
                        break;
                    case SelectBox:
                        pickCanvas.setShapeLocation(raton);
                        pi = pickCanvas.pickClosest();                        
                        point = pi.getClosestIntersectionPoint();
                                  
                        //System.out.println("\n " + point.x + " " +  point.y + " " + point.z);
                        
                        if(selected.getPosition().equals(Position.point3dToBoardPosition(point))){
                            setStatus(status.SelectPiece);
                            selected.downPiece(); // bajamos la pieza para elegir otra
                        }else                                                   
                            if(board.allowMove(selected, Position.point3dToBoardPosition(point))){
                                //selected.setPosition(Position.point3dToBoardPosition(point));
                                
                                if(!board.getEndGame()){
                                    board.changeTurn();
        //                      board.enablePick(false);
        //                      board.enablePieces(board.getTurn());                        
                                    setStatus(status.SelectPiece);
                                }else
                                    setStatus(status.Nothing);
                            }   
                        break;
     
                    case Nothing:    
                        
                        break;
                        
                    default:
                        System.out.println("Fallo al hacer pick");   
                }
        }   
        
        // Establecer de nuevo la condición de respuesta
        wakeupOn(condicion);
    }
    
}        