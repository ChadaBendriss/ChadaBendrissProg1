//First Name: Chada
//Last Name: Bendriss
//Student nember: 300266679


public class Point3D {

    //Initialisation des variables tout en les mettant privés
    private int clusterLabel=0;
    private double x;
    private double  y;
    private double  z;

    //Permet de recevoir les coordonnes du point
    public Point3D(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    //Donne acces au coordonné x
    public double getX(){
        return x;

    }
    //Donne acces au coordonné y
    public double getY(){
        return y;

    }
    //Donne acces au coordonné z
    public double getZ(){
        return z;

    }
    //Permet d'attribuer le clusterlabel au point.
    //Si le clusterlabel est 0, cela veut dire qu'on n'a pas encore verifier ce point
    //Si le clusterlabel est -1, cela veut dire que c'est un noise et n'appartient pas au groupe de  points
    //Si le clusterlabel est 1, cela veut dire que on a deja verifié ce point et il appartient au groupe de points
    public void setclusterLabel(int nb){
        this.clusterLabel=nb;
    }

    //permet d'acceder a la valeur du clusterlabel
    public int getclusterLabel(){
        return clusterLabel;
    }

    //permet de calculet la distance entre deux points
    public double distance(Point3D pt){
        return Math.pow((Math.pow(pt.getX() - this.getX(), 2) + Math.pow(pt.getY() - this.getY(), 2) + Math.pow(pt.getZ() - this.getZ(), 2) *1.0), 0.5);

    }
}
