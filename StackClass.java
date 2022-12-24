//First Name: Chada
//Last Name: Bendriss
//Student nember: 300266679



import java.util.Stack;
 
public class StackClass<Object> {

    private Stack<Object> S;
    
    public StackClass() { 
        this.S = new Stack<Object>(); 
    }
    
    //Retourn true si la pile est vide est false sinon
    public boolean empty() { 
        return this.S.empty(); 
    }

    //Supprime et retourne l'élément supprimé de la pile
    public Object pop() {
        return this.S.pop(); 
   }

   //Ajoute et retourne l'élément ajouté à la pile
   public Object push(Object element) {
        return this.S.push(element); 


   }
    
   
}
