

public class Multiplication extends OperationBinaire{
    public Multiplication(Expression opGauche, Expression opDroite){
        this.operandeGauche = opGauche;
        this.operandeDroite = opDroite;
    }

    @Override
    public double evaluate(){
        return operandeGauche.evaluate() * operandeDroite.evaluate();
    }
}