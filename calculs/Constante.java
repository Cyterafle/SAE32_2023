

public class Constante extends Expression{
    private double valeur;

    public Constante(double value){
        this.valeur = value;
    }

    @Override
    public double evaluate(){
        return this.valeur;
    }
}