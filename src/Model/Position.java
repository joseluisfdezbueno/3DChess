/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 *
 * @author Jose
 */
public class Position{
    private int x;
    private int y;
    
    private static final double halfWidth = 1.1111d;    // respecto coordenadas del mundo
    private static final double pieceHeight = 0.06509518623352051*10d; //2.7d; // respecto coordenadas del mundo    
    private static final double width = 0.22222d;       // respecto coordenadas maestras
    private static final double surfaceBoardHeight= 0.06509518623352051d;      // respecto coordenadas maestras
    
    Position(){
        this.x = 0;
        this.y = 0;
    }
    
    Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        
        if (!(obj.getClass().getSimpleName().equals("Position")))
            return false;
        
        Position mc = (Position) obj;
        
        if (!(x == mc.getX()))
            return false;
        if (!(y == mc.getY()))
            return false;        
        
        return true;        
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }    
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }    
    
    
    public static Vector3d giveBackDrawPosition(Position p){
        Vector3d vector = new Vector3d();
        
        // 1º Fila
        
        if(p.x == 0 && p.y == 0)
            vector.set(-halfWidth*7, pieceHeight, halfWidth*7);
        else if(p.x == 0 && p.y == 1)
            vector.set(-halfWidth*5, pieceHeight, halfWidth*7);        
        else if(p.x == 0 && p.y == 2)
            vector.set(-halfWidth*3, pieceHeight, halfWidth*7);         
        else if(p.x == 0 && p.y == 3)
            vector.set(-halfWidth, pieceHeight, halfWidth*7);
        else if(p.x == 0 && p.y == 4)
            vector.set(halfWidth, pieceHeight, halfWidth*7);
        else if(p.x == 0 && p.y == 5)
            vector.set(halfWidth*3, pieceHeight, halfWidth*7);
        else if(p.x == 0 && p.y == 6)
            vector.set(halfWidth*5, pieceHeight, halfWidth*7);           
        else if(p.x == 0 && p.y == 7)
            vector.set(halfWidth*7, pieceHeight, halfWidth*7);                 
        
        // 2º Fila
        
        else if(p.x == 1 && p.y == 0)
            vector.set(-halfWidth*7, pieceHeight, halfWidth*5);
        else if(p.x == 1 && p.y == 1)
            vector.set(-halfWidth*5, pieceHeight, halfWidth*5);
        else if(p.x == 1 && p.y == 2)
            vector.set(-halfWidth*3, pieceHeight, halfWidth*5);         
        else if(p.x == 1 && p.y == 3)
            vector.set(-halfWidth, pieceHeight, halfWidth*5);
        else if(p.x == 1 && p.y == 4)
            vector.set(halfWidth, pieceHeight, halfWidth*5);
        else if(p.x == 1 && p.y == 5)
            vector.set(halfWidth*3, pieceHeight, halfWidth*5);
        else if(p.x == 1 && p.y == 6)
            vector.set(halfWidth*5, pieceHeight, halfWidth*5);           
        else if(p.x == 1 && p.y == 7)
            vector.set(halfWidth*7, pieceHeight, halfWidth*5);        
        
        // 3º Fila
        
        else if(p.x == 2 && p.y == 0)
            vector.set(-halfWidth*7, pieceHeight, halfWidth*3);
        else if(p.x == 2 && p.y == 1)
            vector.set(-halfWidth*5, pieceHeight, halfWidth*3);
        else if(p.x == 2 && p.y == 2)
            vector.set(-halfWidth*3, pieceHeight, halfWidth*3);         
        else if(p.x == 2 && p.y == 3)
            vector.set(-halfWidth, pieceHeight, halfWidth*3);
        else if(p.x == 2 && p.y == 4)
            vector.set(halfWidth, pieceHeight, halfWidth*3);
        else if(p.x == 2 && p.y == 5)
            vector.set(halfWidth*3, pieceHeight, halfWidth*3);
        else if(p.x == 2 && p.y == 6)
            vector.set(halfWidth*5, pieceHeight, halfWidth*3);           
        else if(p.x == 2 && p.y == 7)
            vector.set(halfWidth*7, pieceHeight, halfWidth*3);
                        
        // 4º Fila        
        
        else if(p.x == 3 && p.y == 0)
            vector.set(-halfWidth*7, pieceHeight, halfWidth);
        else if(p.x == 3 && p.y == 1)
            vector.set(-halfWidth*5, pieceHeight, halfWidth);
        else if(p.x == 3 && p.y == 2)
            vector.set(-halfWidth*3, pieceHeight, halfWidth);         
        else if(p.x == 3 && p.y == 3)
            vector.set(-halfWidth, pieceHeight, halfWidth);        
        else if(p.x == 3 && p.y == 4)
            vector.set(halfWidth, pieceHeight, halfWidth);
        else if(p.x == 3 && p.y == 5)
            vector.set(halfWidth*3, pieceHeight, halfWidth);
        else if(p.x == 3 && p.y == 6)
            vector.set(halfWidth*5, pieceHeight, halfWidth);           
        else if(p.x == 3 && p.y == 7)
            vector.set(halfWidth*7, pieceHeight, halfWidth);        
        
        // 5º Fila
        
        else if(p.x == 4 && p.y == 0)
            vector.set(-halfWidth*7, pieceHeight, -halfWidth);
        else if(p.x == 4 && p.y == 1)
            vector.set(-halfWidth*5, pieceHeight, -halfWidth);
        else if(p.x == 4 && p.y == 2)
            vector.set(-halfWidth*3, pieceHeight, -halfWidth);         
        else if(p.x == 4 && p.y == 3)
            vector.set(-halfWidth, pieceHeight, -halfWidth);        
        else if(p.x == 4 && p.y == 4)
            vector.set(halfWidth, pieceHeight, -halfWidth);
        else if(p.x == 4 && p.y == 5)
            vector.set(halfWidth*3, pieceHeight, -halfWidth);
        else if(p.x == 4 && p.y == 6)
            vector.set(halfWidth*5, pieceHeight, -halfWidth);           
        else if(p.x == 4 && p.y == 7)
            vector.set(halfWidth*7, pieceHeight, -halfWidth);       
        
         // 6º Fila
        
        else if(p.x == 5 && p.y == 0)
            vector.set(-halfWidth*7, pieceHeight, -halfWidth*3);
        else if(p.x == 5 && p.y == 1)
            vector.set(-halfWidth*5, pieceHeight, -halfWidth*3);
        else if(p.x == 5 && p.y == 2)
            vector.set(-halfWidth*3, pieceHeight, -halfWidth*3);         
        else if(p.x == 5 && p.y == 3)
            vector.set(-halfWidth, pieceHeight, -halfWidth*3);        
        else if(p.x == 5 && p.y == 4)
            vector.set(halfWidth, pieceHeight, -halfWidth*3);
        else if(p.x == 5 && p.y == 5)
            vector.set(halfWidth*3, pieceHeight, -halfWidth*3);
        else if(p.x == 5 && p.y == 6)
            vector.set(halfWidth*5, pieceHeight, -halfWidth*3);           
        else if(p.x == 5 && p.y == 7)
            vector.set(halfWidth*7, pieceHeight, -halfWidth*3);               
        
        // 7º Fila
        
        else if(p.x == 6 && p.y == 0)
            vector.set(-halfWidth*7, pieceHeight, -halfWidth*5);
        else if(p.x == 6 && p.y == 1)
            vector.set(-halfWidth*5, pieceHeight, -halfWidth*5);
        else if(p.x == 6 && p.y == 2)
            vector.set(-halfWidth*3, pieceHeight, -halfWidth*5);         
        else if(p.x == 6 && p.y == 3)
            vector.set(-halfWidth, pieceHeight, -halfWidth*5);        
        else if(p.x == 6 && p.y == 4)
            vector.set(halfWidth, pieceHeight, -halfWidth*5);
        else if(p.x == 6 && p.y == 5)
            vector.set(halfWidth*3, pieceHeight, -halfWidth*5);
        else if(p.x == 6 && p.y == 6)
            vector.set(halfWidth*5, pieceHeight, -halfWidth*5);           
        else if(p.x == 6 && p.y == 7)
            vector.set(halfWidth*7, pieceHeight, -halfWidth*5);         
                
        // 8º Fila
        
        else if(p.x == 7 && p.y == 0)
            vector.set(-halfWidth*7, pieceHeight, -halfWidth*7);
        else if(p.x == 7 && p.y == 1)
            vector.set(-halfWidth*5, pieceHeight, -halfWidth*7);
        else if(p.x == 7 && p.y == 2)
            vector.set(-halfWidth*3, pieceHeight, -halfWidth*7);         
        else if(p.x == 7 && p.y == 3)
            vector.set(-halfWidth, pieceHeight, -halfWidth*7);        
        else if(p.x == 7 && p.y == 4)
            vector.set(halfWidth, pieceHeight, -halfWidth*7);
        else if(p.x == 7 && p.y == 5)
            vector.set(halfWidth*3, pieceHeight, -halfWidth*7);
        else if(p.x == 7 && p.y == 6)
            vector.set(halfWidth*5, pieceHeight, -halfWidth*7);           
        else if(p.x == 7 && p.y == 7)
            vector.set(halfWidth*7, pieceHeight, -halfWidth*7);
        
        
    //    else
    //        vector.set(0, 0, 0);
                
        return vector;
    }
    
    public static Position point3dToBoardPosition(Point3d p){    
        Position pos = new Position();
        
        System.out.println("Punto seleccionado X: " + p.x + " Z: " + p.z);
                
        // 1º Fila
        if(p.x > -width*4 && p.x < -width*3 && p.z < width*4 && p.z > width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(0, 0);
        else if(p.x > -width*3 && p.x < -width*2 && p.z < width*4 && p.z > width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(0, 1);   
        else if(p.x > -width*2 && p.x < -width*1 && p.z < width*4 && p.z > width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(0, 2);                
        else if(p.x > -width*1 && p.x < width*0 && p.z < width*4 && p.z > width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(0, 3);                
        else if(p.x < width*1 && p.x > width*0 && p.z < width*4 && p.z > width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(0, 4);                
        else if(p.x < width*2 && p.x > width*1 && p.z < width*4 && p.z > width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(0, 5);                
        else if(p.x < width*3 && p.x > width*2 && p.z < width*4 && p.z > width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(0, 6);                        
        else if(p.x < width*4 && p.x > width*3 && p.z < width*4 && p.z > width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(0, 7);        
        
        // 2º Fila
        
        else if(p.x > -width*4 && p.x < -width*3 && p.z < width*3 && p.z > width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(1, 0);
        else if(p.x > -width*3 && p.x < -width*2 && p.z < width*3 && p.z > width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(1, 1);   
        else if(p.x > -width*2 && p.x < -width*1 && p.z < width*3 && p.z > width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(1, 2);                
        else if(p.x > -width*1 && p.x < width*0 && p.z < width*3 && p.z > width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(1, 3);                
        else if(p.x < width*1 && p.x > width*0 && p.z < width*3 && p.z > width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(1, 4);                
        else if(p.x < width*2 && p.x > width*1 && p.z < width*3 && p.z > width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(1, 5);                
        else if(p.x < width*3 && p.x > width*2 && p.z < width*3 && p.z > width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(1, 6);                        
        else if(p.x < width*4 && p.x > width*3 && p.z < width*3 && p.z > width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(1, 7);        
                
        // 3º Fila
        
        else if(p.x > -width*4 && p.x < -width*3 && p.z < width*2 && p.z > width*1 && p.y == surfaceBoardHeight)
            pos.setPosition(2, 0);
        else if(p.x > -width*3 && p.x < -width*2 && p.z < width*2 && p.z > width*1 && p.y == surfaceBoardHeight)
            pos.setPosition(2, 1);   
        else if(p.x > -width*2 && p.x < -width*1 && p.z < width*2 && p.z > width*1 && p.y == surfaceBoardHeight)
            pos.setPosition(2, 2);                
        else if(p.x > -width*1 && p.x < width*0 && p.z < width*2 && p.z > width*1 && p.y == surfaceBoardHeight)
            pos.setPosition(2, 3);                
        else if(p.x < width*1 && p.x > width*0 && p.z < width*2 && p.z > width*1 && p.y == surfaceBoardHeight)
            pos.setPosition(2, 4);                
        else if(p.x < width*2 && p.x > width*1 && p.z < width*2 && p.z > width*1 && p.y == surfaceBoardHeight)
            pos.setPosition(2, 5);                
        else if(p.x < width*3 && p.x > width*2 && p.z < width*2 && p.z > width*1 && p.y == surfaceBoardHeight)
            pos.setPosition(2, 6);                        
        else if(p.x < width*4 && p.x > width*3 && p.z < width*2 && p.z > width*1 && p.y == surfaceBoardHeight)
            pos.setPosition(2, 7);                   
        
        // 4º Fila
        
        else if(p.x > -width*4 && p.x < -width*3 && p.z < width && p.z > width*0 && p.y == surfaceBoardHeight)
            pos.setPosition(3, 0);
        else if(p.x > -width*3 && p.x < -width*2 && p.z < width && p.z > width*0 && p.y == surfaceBoardHeight)
            pos.setPosition(3, 1);   
        else if(p.x > -width*2 && p.x < -width*1 && p.z < width && p.z > width*0 && p.y == surfaceBoardHeight)
            pos.setPosition(3, 2);                
        else if(p.x > -width*1 && p.x < width*0 && p.z < width && p.z > width*0 && p.y == surfaceBoardHeight)
            pos.setPosition(3, 3);                
        else if(p.x < width*1 && p.x > width*0 && p.z < width && p.z > width*0 && p.y == surfaceBoardHeight)
            pos.setPosition(3, 4);                
        else if(p.x < width*2 && p.x > width*1 && p.z < width && p.z > width*0 && p.y == surfaceBoardHeight)
            pos.setPosition(3, 5);                
        else if(p.x < width*3 && p.x > width*2 && p.z < width && p.z > width*0 && p.y == surfaceBoardHeight)
            pos.setPosition(3, 6);                        
        else if(p.x < width*4 && p.x > width*3 && p.z < width && p.z > width*0 && p.y == surfaceBoardHeight)
            pos.setPosition(3, 7);        
        
        // 5º Fila
        
        else if(p.x > -width*4 && p.x < -width*3 && p.z < 0 && p.z > -width && p.y == surfaceBoardHeight)
            pos.setPosition(4, 0);
        else if(p.x > -width*3 && p.x < -width*2 && p.z < 0 && p.z > -width && p.y == surfaceBoardHeight)
            pos.setPosition(4, 1);   
        else if(p.x > -width*2 && p.x < -width*1 && p.z < 0 && p.z > -width && p.y == surfaceBoardHeight)
            pos.setPosition(4, 2);                
        else if(p.x > -width*1 && p.x < width*0 && p.z < 0 && p.z > -width && p.y == surfaceBoardHeight)
            pos.setPosition(4, 3);                
        else if(p.x < width*1 && p.x > width*0 && p.z < 0 && p.z > -width && p.y == surfaceBoardHeight)
            pos.setPosition(4, 4);                
        else if(p.x < width*2 && p.x > width*1 && p.z < 0 && p.z > -width && p.y == surfaceBoardHeight)
            pos.setPosition(4, 5);                
        else if(p.x < width*3 && p.x > width*2 && p.z < 0 && p.z > -width && p.y == surfaceBoardHeight)
            pos.setPosition(4, 6);                        
        else if(p.x < width*4 && p.x > width*3 && p.z < 0 && p.z > -width && p.y == surfaceBoardHeight)
            pos.setPosition(4, 7);        
                
        // 6º Fila
        
        else if(p.x > -width*4 && p.x < -width*3 && p.z < -width && p.z > -width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(5, 0);
        else if(p.x > -width*3 && p.x < -width*2 && p.z < -width && p.z > -width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(5, 1);   
        else if(p.x > -width*2 && p.x < -width*1 && p.z < -width && p.z > -width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(5, 2);                
        else if(p.x > -width*1 && p.x < width*0 && p.z < -width && p.z > -width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(5, 3);                
        else if(p.x < width*1 && p.x > width*0 && p.z < -width && p.z > -width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(5, 4);                
        else if(p.x < width*2 && p.x > width*1 && p.z < -width && p.z > -width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(5, 5);                
        else if(p.x < width*3 && p.x > width*2 && p.z < -width && p.z > -width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(5, 6);                        
        else if(p.x < width*4 && p.x > width*3 && p.z < -width && p.z > -width*2 && p.y == surfaceBoardHeight)
            pos.setPosition(5, 7);     
                        
        // 7º Fila
        
        else if(p.x > -width*4 && p.x < -width*3 && p.z < -width*2 && p.z > -width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(6, 0);
        else if(p.x > -width*3 && p.x < -width*2 && p.z < -width*2 && p.z > -width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(6, 1);   
        else if(p.x > -width*2 && p.x < -width*1 && p.z < -width*2 && p.z > -width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(6, 2);                
        else if(p.x > -width*1 && p.x < width*0 && p.z < -width*2 && p.z > -width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(6, 3);                
        else if(p.x < width*1 && p.x > width*0 && p.z < -width*2 && p.z > -width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(6, 4);                
        else if(p.x < width*2 && p.x > width*1 && p.z < -width*2 && p.z > -width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(6, 5);                
        else if(p.x < width*3 && p.x > width*2 && p.z < -width*2 && p.z > -width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(6, 6);                        
        else if(p.x < width*4 && p.x > width*3 && p.z < -width*2 && p.z > -width*3 && p.y == surfaceBoardHeight)
            pos.setPosition(6, 7);                     
        
        // 8º Fila
        
        else if(p.x > -width*4 && p.x < -width*3 && p.z < -width*3 && p.z > -width*4 && p.y == surfaceBoardHeight)
            pos.setPosition(7, 0);
        else if(p.x > -width*3 && p.x < -width*2 && p.z < -width*3 && p.z > -width*4 && p.y == surfaceBoardHeight)
            pos.setPosition(7, 1);   
        else if(p.x > -width*2 && p.x < -width*1 && p.z < -width*3 && p.z > -width*4 && p.y == surfaceBoardHeight)
            pos.setPosition(7, 2);                
        else if(p.x > -width*1 && p.x < width*0 && p.z < -width*3 && p.z > -width*4 && p.y == surfaceBoardHeight)
            pos.setPosition(7, 3);                
        else if(p.x < width*1 && p.x > width*0 && p.z < -width*3 && p.z > -width*4 && p.y == surfaceBoardHeight)
            pos.setPosition(7, 4);                
        else if(p.x < width*2 && p.x > width*1 && p.z < -width*3 && p.z > -width*4 && p.y == surfaceBoardHeight)
            pos.setPosition(7, 5);                
        else if(p.x < width*3 && p.x > width*2 && p.z < -width*3 && p.z > -width*4 && p.y == surfaceBoardHeight)
            pos.setPosition(7, 6);                        
        else if(p.x < width*4 && p.x > width*3 && p.z < -width*3 && p.z > -width*4 && p.y == surfaceBoardHeight)
            pos.setPosition(7, 7);            
        

//        else
  //          pos.setPosition(3, 3);            
        
        return pos;
    }
}
