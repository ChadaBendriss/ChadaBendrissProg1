//First Name: Chada
//Last Name: Bendriss
//Student nember: 300266679

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

public class DBScan {
    private double eps=0.96;
    private int minPts=4;
    private int NumberOfClusters;
    private List<Point3D> points= new ArrayList<Point3D>();
    
    //Permet de recevoir la liste des points
    public DBScan(List<Point3D>points){

        this.points=points;

    }

    //Permet de modifier eps et d'acceder a sa valeur grace au return 
    public double setEps(double eps){
        this.eps=eps;
        return eps;

    }
    //Permet de modifier minPts et d'acceder a sa valeur grace au return 
    public int setMinPts(int minPts){
        this.minPts=minPts;
        return minPts;

    }
    
    public void findClusters(){
        //Initialisation du nombre de clusters
        int C=0;
        //Etudier chaque point de la liste pour lui trouver un cluster
        for(int P =0; P<points.size();P++){
            //Si ce point a deja été traité, alors on l'ignore
            //On s'interesse aux points qui n'ont pas encore été traités
            if(points.get(P).getclusterLabel() != 0 ){
                continue;
            }
            //Trouver les points voisins
            NearestNeighbors N=new NearestNeighbors(points);
            List<Point3D>newList=N.rangeQuery(points.get(P), eps);

            //Si le point n'est pas voisin, alors son cluster label sera 1
            //pour dire qu'on a etudié ce point et il n'appartient 
            //pas au groupe et on l'ignore et on passe a l'autre point 
            if(newList.size()<minPts){
                points.get(P).setclusterLabel(-1);
                continue;
            }

            //Incrementer le nombre de cluster label
            C=C+1;
           
            //Assignation du point au cluster label
            points.get(P).setclusterLabel(C);
            StackClass<Point3D> S=new StackClass<Point3D>();

            //On stocke les points voisins dans une pile
            for(int k=0;k<newList.size();k++){
                S.push( newList.get(k));
            }
           
            while (!(S.empty())){
                Point3D Q=S.pop();
                if(Q.getclusterLabel()==-1){
                    Q.setclusterLabel(C);
                }
                //On ignore les points qui ont été traités
                if(Q.getclusterLabel()!=0){
                    continue;
                }

                //pour le point traité et appartenant a un groupe
                //On lui assigne son cluster label 
                Q.setclusterLabel(C);

                //On regarde la liste des points proches du point qu'on a
                newList=N.rangeQuery(Q, eps);
                if(newList.size()>= minPts){
                    for(int l=0;l<newList.size();l++){
                        S.push(newList.get(l));

                    }
                }
            }
            NumberOfClusters=C;
        }
    }
    //Permet d'acceder au nombre de clusters
    public int getNumberOfClusters(){
        return NumberOfClusters;

    }

    //Permet de modifier le nombre de cluster
    public void setNumberOfClusters(int NumberOfClusters){
        this.NumberOfClusters=NumberOfClusters;
    }

    //Permet d'acceder a la liste des points
    public List<Point3D> getPoints(){
        return points;

    }

    //Permet de lire le fichier
    public static List<Point3D> read(String filename){

        String path= filename;
        String line="";
        List<Point3D>nPoints=new ArrayList<Point3D>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            //Permet d'ignorer la premiere ligne
            br.readLine();
           
            //Separer les points grace a la virgule puis les Stocker dans une liste
            while((line=br.readLine()) !=null){
                String [] tab= line.split("," );
                nPoints.add(new Point3D (Double.parseDouble(tab[0]),Double.parseDouble(tab[1]),Double.parseDouble(tab[2])));
                
               
            }
            //Fermet le fichier
            br.close();
        }catch (FileNotFoundException e) {

            e.printStackTrace();
            System.out.println("not read");
        }catch(IOException e){
            ;

        }
        return nPoints;

    }

    //Permet de créer le fichier
    public void save (String filename){
        try {
            //Ecrire dans le nouveau csv file
            FileWriter fw = new FileWriter(filename,true);
            BufferedWriter bw=new BufferedWriter(fw);
            PrintWriter pw =new PrintWriter(bw);

            pw.println("x"+","+"y"+","+"z"+","+"C"+","+"R"+","+"G"+","+"B");
            pw.println("0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0"+","+"0");
            
            for(int k=1;k<getNumberOfClusters();k++){

                //R,G,B valeurs aléatoires entre 1 et 0
                //Convertion des valeurs de R,G,B en 9 decimales

                double []tab=new double [getNumberOfClusters()];
                Random RR = new Random();
                double R=RR.nextDouble();
                R=(double)Math.round(R* 1000000000) / 1000000000;
                Random GR = new Random(); 
                double G= GR.nextDouble();
                G=(double)Math.round(G* 1000000000) / 1000000000;
                Random BR = new Random(); 
                double B=BR.nextDouble();
                B=(double)Math.round(B* 1000000000) / 1000000000;
                tab[0]=R;
                tab[1]=G;
                tab[2]=B;
                for(int h=1;h<getPoints().size();h++){
                    
                    double X=getPoints().get(h).getX();
                    X=(double)Math.round(X* 1000000000) / 1000000000;

                    double Y=getPoints().get(h).getY();
                    Y=(double)Math.round(Y* 1000000000) / 1000000000;

                    double Z=getPoints().get(h).getZ();
                    Z=(double)Math.round(Z* 1000000000) / 1000000000;

                
                    pw.println(X+","+Y+","+Z+","+getPoints().get(h).getclusterLabel()+","+tab[0]+","+tab[1]+","+tab[2]);
                }
            }
            //Fermer le fichier
            pw.flush();
            pw.close();
            



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Not saved");
            
        }

    }
    public static void main(String[] args) {
        //long FirstTimeARR = System.currentTimeMillis();
        List<Point3D>nlistn;
        //Lire le fichier
        nlistn=DBScan.read("Point_Cloud_1.csv");
        DBScan db = new DBScan(nlistn);
        db.findClusters();
        db.save("Point_Cloud_1_clusters_0.96_4_36.csv");
        System.out.println("java DBScan Point_Cloud_1.csv 0.96 4");
        System.out.println("number of clusters : "+db.getNumberOfClusters());
       
        
        //Creation du tableau pour stocker la somme des points trouvés pour chaque cluster
        Integer tab[]=new Integer [db.getNumberOfClusters()+1];
        int g=0;
        int w=0;
        for(int k=0;k<db.getNumberOfClusters()+1;k++){
            for(int f=0;f<db.getPoints().size();f++){
                if(db.getPoints().get(f).getclusterLabel()==k){
                    g=g+1;
                   
                }
                
            }
            

            //Stocker la somme des points trouvée pour chaque groupe dans un tableau
            tab[w]=g;
            w++;
            g=0;
        }

        //Trier le tableau pour avoir le plus grand nombre de points d'un clusters vers le plus petit nombre 
        //de points d'un cluster
        Arrays.sort(tab,Collections.reverseOrder());
        System.out.println("The size of all clusters found, from the largest one to the smallest one");
       
        //Afficher les sommes en ordre descandant
        for(int x=0;x<tab.length-1;x++){
            System.out.println(tab[x]);
            if(x==tab.length-2){
                System.out.println("We can now conclude that the noise has : "+tab[x]+" points");

            }
        }


        List<Point3D>nlistn2;
        //Lire le fichier
        nlistn2=DBScan.read("Point_Cloud_2.csv");
        DBScan db2 = new DBScan(nlistn2);
        db2.findClusters();
        System.out.println("");
        System.out.println("java DBScan Point_Cloud_2.csv 0.96 4");
        db2.save("Point_Cloud_2_clusters_0.96_4_34.csv");
        System.out.println("number of clusters : "+db2.getNumberOfClusters());
       
        
        //Creation du tableau pour stocker la somme des points trouvés pour chaque cluster
        Integer tab2[]=new Integer [db2.getNumberOfClusters()+1];
        int g2=0;
        int w2=0;
        for(int k2=0;k2<db2.getNumberOfClusters()+1;k2++){
            for(int f2=0;f2<db2.getPoints().size();f2++){
                if(db2.getPoints().get(f2).getclusterLabel()==k2){
                    g2=g2+1;
                   
                }
                
            }
            

            //Stocker la somme des points trouvée pour chaque groupe dans un tableau
            tab2[w2]=g2;
            w2++;
            g2=0;
        }

        //Trier le tableau pour avoir le plus grand nombre de points d'un clusters vers le plus petit nombre 
        //de points d'un cluster
        Arrays.sort(tab2,Collections.reverseOrder());
        System.out.println("The size of all clusters found, from the largest one to the smallest one");
       
        //Afficher les sommes en ordre descandant
        for(int x2=0;x2<tab2.length-1;x2++){
            System.out.println(tab2[x2]);
            if(x2==tab2.length-2){
                System.out.println("We can now conclude that the noise has : "+tab2[x2]+" points");

            }
        }






        List<Point3D>nlistn3;
        //Lire le fichier
        nlistn3=DBScan.read("Point_Cloud_3.csv");
        DBScan db3 = new DBScan(nlistn3);
        db3.findClusters();
        System.out.println("");
        System.out.println("java DBScan Point_Cloud_3.csv 0.96 4");
        db3.save("Point_Cloud_3_clusters_0.96_4_46.csv");
        System.out.println("number of clusters : "+db3.getNumberOfClusters());
       
        
        //Creation du tableau pour stocker la somme des points trouvés pour chaque cluster
        Integer tab3[]=new Integer [db3.getNumberOfClusters()+1];
        int g3=0;
        int w3=0;
        for(int k3=0;k3<db3.getNumberOfClusters()+1;k3++){
            for(int f3=0;f3<db3.getPoints().size();f3++){
                if(db3.getPoints().get(f3).getclusterLabel()==k3){
                    g3=g3+1;
                   
                }
                
            }
            

            //Stocker la somme des points trouvée pour chaque groupe dans un tableau
            tab3[w3]=g3;
            w3++;
            g3=0;
        }

        //Trier le tableau pour avoir le plus grand nombre de points d'un clusters vers le plus petit nombre 
        //de points d'un cluster
        Arrays.sort(tab3,Collections.reverseOrder());
        System.out.println("The size of all clusters found, from the largest one to the smallest one");
       
        //Afficher les sommes en ordre descandant
        for(int x3=0;x3<tab3.length-1;x3++){
            System.out.println(tab3[x3]);
            if(x3==tab3.length-2){
                System.out.println("We can now conclude that the noise has : "+tab3[x3]+" points");

            }
        }
        //long DA =(System.currentTimeMillis()-FirstTimeARR);
        //System.out.println(DA);
       
        



    }
    

}
