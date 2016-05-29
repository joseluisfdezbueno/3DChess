/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.IndexedLineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;
import javax.vecmath.Color3f;

/**
 *
 * @author fvelasco
 */
public class AxeLines extends BranchGroup {
  private Switch axesBranch;
  
  public AxeLines (float length) {
    // El nodo del que cuelgan los tres ejes
    //     Se le permite mostrar sus hijos o no
    //     De inicio, se muestran todos los hijos
    axesBranch = new Switch ();
    axesBranch.setCapability(Switch.ALLOW_SWITCH_WRITE);
    axesBranch.setWhichChild(Switch.CHILD_ALL);
    float[] coordinates = { 0.0f, 0.0f, 0.0f, length, 0.0f, 0.0f,
      0.0f, length, 0.0f, 0.0f, 0.0f, length };
    int[] indices = { 0, 1, 0, 2, 0, 3 };
    Color3f[] colors = {
      new Color3f (1,0,0),
      new Color3f (0,1,0),
      new Color3f (0,0,1)
    };
    int[] colorIndices = { 0, 0, 1, 1, 2, 2 };
    IndexedLineArray lines = new IndexedLineArray (coordinates.length/3,
      IndexedLineArray.COORDINATES 
              | IndexedLineArray.COLOR_3
            , indices.length);
    lines.setCoordinates (0, coordinates);
    lines.setCoordinateIndices(0, indices);
    lines.setColors (0, colors);
    lines.setColorIndices(0, colorIndices);
    Shape3D axes = new Shape3D (lines);
    axesBranch.addChild(axes);
    this.addChild(axesBranch);
  }
  
   void showAxes (boolean onOff) {
    if (onOff)
      axesBranch.setWhichChild(Switch.CHILD_ALL);
    else
      axesBranch.setWhichChild(Switch.CHILD_NONE);      
  }

}
