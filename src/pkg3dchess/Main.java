/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dchess;

import GUI.ControlWindow;
import Model.Universe;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;

/**
 *
 * @author Jose
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Se obtiene la configuración gráfica del sistema y se crea el Canvas3D que va a mostrar la imagen
        //Canvas3D canvasPlanta = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
        Canvas3D canvas = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
        // Se le da el tamaño deseado al lienzo
        //canvasPlanta.setSize(520, 480);
        canvas.setSize(940, 695);
        // Se crea el universo y la rama de la vista con ese canvas
        Universe universe = new Universe(canvas);
        //Universo universo2 = new Universo(canvasVariable);
        // Se crea la GUI a partir del Canvas3D y del Universo
        ControlWindow controlWindow = new ControlWindow (canvas, universe);
        // Se muestra la ventana principal de la aplicación
        controlWindow.showWindow();    
    }
    
}
