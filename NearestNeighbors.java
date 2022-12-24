//First Name: Chada
//Last Name: Bendriss
//Student nember: 300266679


import java.util.ArrayList;
import java.util.List;

public class NearestNeighbors {
    
    private List<Point3D>pts;

    //Permet de recevoir une liste des points
    public NearestNeighbors(List<Point3D>pts){
        this.pts=pts;

    }

    //Permet de recevoir le point et son eps
    //On compare la distance entre le point avec tous les autres point de la liste
    //Si la distance de ce point avec un point de la liste est plus petite ou egale a eps, 
    //alors on l'ajoute a la nouvelle liste
    //on retourne la nouvelle liste qui contient les points proches entre eux
    public List<Point3D> rangeQuery(Point3D p, double eps){
        List<Point3D>N=new ArrayList<Point3D>();
        
        for(int P=0;P<pts.size();P++){
            if(p.distance(pts.get(P))<=eps){
                N.add(pts.get(P));
            }
        }
        return N;

    }
}
