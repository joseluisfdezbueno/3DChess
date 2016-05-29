/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

/**
 *
 * @author Jose
 */
public class Universe extends BranchGroup{
    
    private AxeLines axes;
    private Scene scene;
    
    private View view;
    private Vista planView, perspectiveView;
    private final Canvas3D canvas;
    private final SimpleUniverse universe;
    
    public Universe(Canvas3D canvas){

        this.canvas = canvas;
                
        // ---------- VISTAS Y LUCES -------------- //
                
        // Creamos la vista en planta y la enlazamos al BG universo
        planView = new Vista(canvas);
        //crearVPlanta(posicion, dondeMirar, vup, escala, planoDelantero, planoTrasero)
        planView.crearVPlanta(new Point3d(0,100,40), new Point3d(0,0,0), new Vector3d(0,0,-1), 0.015f, 0.3f, 200f);
        this.addChild(planView);
        //planView.habilitar();
        
        // Creamos y enlazamos la luz ambiental
        Light luz = new Light();
        this.addChild(luz.crearLuzAmbiental(new Color3f (1f, 1f, 1f)));
        
        // Creamos y enlazamos luces puntuales        
        luz = new Light();   
        this.addChild(luz.crearLuzPuntual(new Color3f(0.5f, 0.5f, 0.5f), new Point3f(20, 40, 20), new Point3f(0.05f, 0f, 0f))); // color, posicion, atenuación       
        //this.addChild(luz.crearLuzPuntual(new Color3f(0.5f, 0.5f, 0.5f), new Point3f(-20, 40, 20), new Point3f(0.05f, 0f, 0f)));        
        //this.addChild(luz.crearLuzPuntual(new Color3f(0.5f, 0.5f, 0.5f), new Point3f(20, 40, -20), new Point3f(0.05f, 0f, 0f)));        
        this.addChild(luz.crearLuzPuntual(new Color3f(0.5f, 0.5f, 0.5f), new Point3f(-20, 40, -20), new Point3f(0.05f, 0f, 0f))); 
                
        // ----------- FIN VISTAS Y LUCES ------------- //        
                
        // --- CREAMOS EL RESTO DE RAMAS ----- //        
        
        // Creamos y enlazamos el fondo
        //this.fondo = new Fondo();
        //this.addChild(fondo);
                
        // Creamos y enlazamos la escena        
        scene = new Scene(canvas);
        this.addChild(scene);
                    
        // Axes 
        axes = new AxeLines(20f);
        this.addChild(axes);               
                               
        // Se crea un SimpleUniverse con la vista en perspectiva por defecto
        this.universe = createUniverse (canvas);
                                       
        // Se optimiza nuestro "universo" y se cuelga del simple universe
        this.compile();
        this.universe.addBranchGraph(this);
    }
    
    // creación del universo, a través de un simple universo, con la vista modificada y con comportamiento para la interacción con el ratón
    private SimpleUniverse createUniverse (Canvas3D canvas) {
        // Se crea manualmente un ViewingPlatform para poder personalizarlo y asignárselo al universo
        ViewingPlatform viewingPlatform = new ViewingPlatform();

        // La transformación de vista, dónde se está, a dónde se mira, Vup
        TransformGroup viewTransformGroup = viewingPlatform.getViewPlatformTransform();
        Transform3D viewTransform3D = new Transform3D();
        viewTransform3D.lookAt (new Point3d (20,20,20), new Point3d (0,0,0), new Vector3d (0,1,0));
        viewTransform3D.invert();
        viewTransformGroup.setTransform (viewTransform3D);

        // El comportamiento, para mover la camara con el raton
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(new BoundingSphere(new Point3d (0.0f, 0.0f, 0.0f), 100.0f));
        orbit.setZoomFactor (2.0f);
        viewingPlatform.setViewPlatformBehavior(orbit);

        // Configuramos el view
        Viewer viewer = new Viewer (canvas);
        this.view = viewer.getView();
        this.view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);        
        this.view.setFieldOfView(Math.toRadians(45));
        this.view.setBackClipDistance(50.0);
        this.view.setFrontClipDistance(0.1); 

        // Se construye y devuelve el Universo con los parametros definidos
        return new SimpleUniverse (viewingPlatform, viewer);
    }
           
    // método para cerrar la aplicación
    public void closeApp(int code){
        System.exit(code);
    }

    public void showAxes(boolean onOff) {
        axes.showAxes(onOff);
    }    

    public void enablePerspectiveView() {
       if(view.numCanvas3Ds() == 0){
            planView.deshabilitar();
            view.addCanvas3D(canvas);
        }     
    }

    public void enablePlanView() {
        view.removeAllCanvas3Ds();
        planView.habilitar();
    }
        
}
