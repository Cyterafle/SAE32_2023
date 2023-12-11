

public class Addition extends OperationBinaire{
    public Addition(Expression opGauche, Expression opDroite){
        this.operandeGauche = opGauche;
        this.operandeDroite = opDroite;
    }

    @Override
    public double evaluate(){
        return operandeGauche.evaluate() + operandeDroite.evaluate();
    }
}