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
public class Translate extends TransformGroup{
    private Transform3D translation;
    
    public Translate(Vector3d vector){
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);           
        
        translation = new Transform3D();
        translation.setTranslation(vector);
        this.setTransform(translation);             
    }    
    
    public void setTranslate(Vector3d vector){       
        translation.setTranslation(vector);     
        this.setTransform(translation);        
    }    
    
}
