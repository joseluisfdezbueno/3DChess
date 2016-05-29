/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

/**
 *
 * @author Jose
 */
public class Scale extends TransformGroup{
    private Transform3D scale;
    
    public Scale(Vector3d vector){
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);      
        
        scale = new Transform3D();
        scale.setScale(vector);
        this.setTransform(scale);          
    }    
    
    public Scale(Double value){
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);   
        
        scale = new Transform3D();
        scale.setScale(value);
        this.setTransform(scale);
    }        
    
    public void setScale(Double value){
        scale.setScale(value);     
        this.setTransform(scale);        
    }        
    
    public void setScale(Vector3d vector){
        scale.setScale(vector);     
        this.setTransform(scale);        
    }            
}
