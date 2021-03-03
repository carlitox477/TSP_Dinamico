import java.util.HashSet;

import EstructurasCatedra.Ciudad;
import java.util.Set;

public class MainSetTest{
    public static void main(String[]args){
        Ciudad c1 =new Ciudad("Glaciar Perito Moreno", "Argentina", -73.0481, 50.4732, true);
		Ciudad c2= new Ciudad("Ushuaia", "Argentina", -54.0872, -68.3040, false);

        Set set1=new HashSet<Ciudad>();
        Set set2=new HashSet<Ciudad>();

        boolean igualdad;

        igualdad=set1.equals(set2);

        System.out.println("Los sets son iguales: "+igualdad);

        set1.add(c1);
        set1.add(c2);

        set2.add(c2);
        set2.add(c1);

        igualdad= set1.equals(set2);
        igualdad= (set1.hashCode()==set2.hashCode());
        System.out.println("Los sets son iguales: "+igualdad);
        System.out.println("Hashcode set1: "+set1.hashCode());
        System.out.println("Hashcode set2: "+set2.hashCode());
    }
}