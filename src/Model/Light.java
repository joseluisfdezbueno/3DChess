/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.PointLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
/**
 *
 * @author Jose
 */
public class Light {
        javax.media.j3d.Light aLight;
    
    public Light(){

    }
    
    javax.media.j3d.Light crearLuzAmbiental(Color3f color){
        
        // Se crea la luz ambiente                
        aLight = new AmbientLight (color);
        aLight.setInfluencingBounds (new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 10000.0));
        aLight.setEnable(true);
        
        return aLight;
    }       
    
    
    javax.media.j3d.Light crearLuzPuntual(Color3f color, Point3f posicion, Point3f atenuacion){        
        
        // Se crea la luz puntual en el sol
        aLight = new PointLight (color, posicion, atenuacion);
        aLight.setInfluencingBounds (new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 10000.0));
        aLight.setEnable(true);
        
        return aLight;  
    }   
    
}
