/**
 * PSET 3
 * May 6th, 2020
 *
 * @author Jason Chen, Zi Lan Zhang
 **/

// class that stores the character and frequency
public class Elem{
    Character c;
    Integer f;

    public Elem(Character c, Integer f){
        this.c = c;
        this.f = f;

    }

    public Character getChar(){
        return c;
    }

    public Integer getF(){
        return f;
    }

    // returns the character and frequency as a string
    @Override
    public String toString(){
        if (getChar() != null) {
            return getChar().toString() + ": " + getF().toString();
        }else if (getF() != null){
            return getF().toString();
        }else{
            return "empty file";
        }
    }
}