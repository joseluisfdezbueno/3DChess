/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.media.j3d.BranchGroup;
import static javax.media.j3d.BranchGroup.ALLOW_DETACH;
import javax.vecmath.Vector3d;

/**
 *
 * @author Jose
 */
public abstract class Piece extends BranchGroup{
    Colour colour;
    Position initialPosition;
    Position position;
    
    Model myModel;
    Scale scale;
    Translate translate;
    
    
    public Piece(String modelPath, Colour colour, Position position){
        //this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //this.setCapability(Node.ENABLE_PICK_REPORTING);
        //this.setPickable(true);
        //this.setCapability(Group.ALLOW_CHILDREN_WRITE);
        this.setCapability(ALLOW_DETACH);        
        
        this.colour = colour;
        this.initialPosition = position;
        this.position = position;        
        myModel = new Model(modelPath, 1);
        
        scale = new Scale(0.8d);
        translate = new Translate(Position.giveBackDrawPosition(position));
                
        scale.addChild(myModel);
        translate.addChild(scale);
        this.addChild(translate);                
    }
        
    
    public void initialPosition(){
        this.position = this.initialPosition;
        this.translate.setTranslate(Position.giveBackDrawPosition(this.position));
    }
    
    public Colour getColour(){
        return colour;
    }

    public Position getPosition(){
        return position;
    }
    
    public Position getInitialPosition(){
        return initialPosition;
    }    
    
    public void setPosition(Position position){
        this.position = position;
        System.out.println(this.position.getX() + "  " + this.position.getY());
        this.translate.setTranslate(Position.giveBackDrawPosition(this.position));
    }
    
    public void setPositionNotDraw(Position position){
        this.position = position;
    }    
    
    public void upPiece(){    
        Vector3d vector;
        vector = Position.giveBackDrawPosition(this.position);
        vector.y = vector.y + 2;
        this.translate.setTranslate(vector);        
    } 
    
    public void downPiece(){        
        Vector3d vector;
        vector = Position.giveBackDrawPosition(this.position);        
        this.translate.setTranslate(vector);        
    }     
    
    public abstract boolean validMove(Position pos);
    
    /*
    void enablePick(boolean onOff){
        if(onOff){
            this.setPickable(true);
        }else{
            this.setPickable(false);                        
        }        
    }
    */
}