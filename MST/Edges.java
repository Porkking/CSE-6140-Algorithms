package src;
import java.util.Comparator;

public class Edges implements Comparable<Edges> {
    int start;
    int end;
    int len;
    @Override
    public int compareTo(Edges e1){
        return this.len - e1.len;
    }
    public Edges(int s, int e, int l){
        start = s;
        end = e;
        len = l;
    }
}
