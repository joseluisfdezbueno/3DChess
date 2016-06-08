/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 *
 * @author Jose
 */
public class Vista extends TransformGroup{
    private boolean activada;
    private View view;
    private Canvas3D canvas;
    
    Vista(Canvas3D canvas){
        this.canvas = canvas;
    }
    
    public void crearVPlanta(Point3d posicion, Point3d dondeMirar, Vector3d vup, float escala, 
            float planoDelantero, float planoTrasero){
        
        this.setCapability(ALLOW_TRANSFORM_WRITE);
        
        // TransformGroup para posicionar y orientar la vista
        Transform3D transformPlanta = new Transform3D();
        transformPlanta.lookAt(posicion, dondeMirar, vup);
        transformPlanta.invert();
                
        this.setTransform(transformPlanta);
        
        ViewPlatform vpPlanta = new ViewPlatform();
        this.addChild (vpPlanta);
        
        // Definición de la vista paralela
        view = new View();
        view.setPhysicalBody(new PhysicalBody());
        view.setPhysicalEnvironment (new PhysicalEnvironment());
        
        // ajustamos parámetros
        view.setProjectionPolicy(View.PARALLEL_PROJECTION);
        view.setScreenScalePolicy (View.SCALE_EXPLICIT);
        view.setScreenScale(escala);
        view.setFrontClipDistance(planoDelantero); //0.1
        view.setBackClipDistance(planoTrasero); // 20
        
        // enlazamos view con el ViewPlatform
        view.attachViewPlatform(vpPlanta);          
            
    }
    
    public void crearVPerspSujetiva(Point3d posicion, Point3d dondeMirar, Vector3d vup, float anguloApertura, 
            float planoDelantero, float planoTrasero){
        
        this.setCapability(ALLOW_TRANSFORM_WRITE);        
 
        // TransformGroup para posicionar y orientar la vista
        Transform3D transformPersp = new Transform3D();
        transformPersp.lookAt(posicion, dondeMirar, vup);
        transformPersp.invert();
        
        this.setTransform(transformPersp);
        
        ViewPlatform vpPersp = new ViewPlatform();
        this.addChild(vpPersp);
        
        // Definición de la vista en perspectiva
        view = new View();
        view.setPhysicalBody(new PhysicalBody());
        view.setPhysicalEnvironment(new PhysicalEnvironment());
        // la ajustmos
        view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        view.setFieldOfView(Math.toRadians(anguloApertura));
        view.setFrontClipDistance(planoDelantero);
        view.setBackClipDistance(planoTrasero);

        // enlazamos view con el ViewPlatform        
        view.attachViewPlatform(vpPersp);             
    }        
    
    public void setCanvas(Canvas3D canvas){
        this.canvas = canvas;
    }
    
    public Canvas3D getCanvas(){
        return this.canvas;
    }    
        
    public void habilitar(){
        // enlazamos el canvas al view
        if(!activada){
            view.addCanvas3D(this.canvas);
            activada = true;
        }
    }
    
    public void deshabilitar(){
        // eliminamos el enlace entre el canvas y el view
        if(activada){ // Si está activa
            view.removeCanvas3D(this.canvas); // Se quita el Canvas al View
            activada = false;
        }
    }
    
    public void setPlanView(Point3d posicion, Point3d dondeMirar, Vector3d vup){
        // TransformGroup para posicionar y orientar la vista
        Transform3D transformPlanta = new Transform3D();
        transformPlanta.lookAt(posicion, dondeMirar, vup);
        transformPlanta.invert();
                
        this.setTransform(transformPlanta);
    }
            
}