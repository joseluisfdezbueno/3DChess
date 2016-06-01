/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import java.io.FileNotFoundException;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;

/**
 *
 * @author Jose
 */
public class Model extends BranchGroup{
    private ObjectFile archivo;
    private Scene modelo;
    
    public Model(String ruta, int modo){
        this.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        this.setCapability(Group.ALLOW_CHILDREN_WRITE);
                      
        //archivo = new ObjectFile (ObjectFile.RESIZE | ObjectFile.STRIPIFY);        
        //archivo = new ObjectFile (ObjectFile.STRIPIFY);
        //archivo = new ObjectFile (ObjectFile.RESIZE);
        //archivo = new ObjectFile ();
        
        if(modo == 0)
            archivo = new ObjectFile (ObjectFile.RESIZE | ObjectFile.STRIPIFY);                     
        else if(modo == 1)
            //archivo = new ObjectFile (ObjectFile.TRIANGULATE);
            archivo = new ObjectFile (ObjectFile.STRIPIFY); 
        
        try {   
            modelo = archivo.load (ruta);          
        }catch (FileNotFoundException | ParsingErrorException | IncorrectFormatException e){
            System.err.println (e);
            System.exit(1);
        }
        this.addChild (modelo.getSceneGroup());         
        
    }

}
