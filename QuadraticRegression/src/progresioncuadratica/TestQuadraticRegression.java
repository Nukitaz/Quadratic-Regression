package progresioncuadratica;

public class TestQuadraticRegression {
    public static void main(String[] args) {
        QuadraticRegression qr = new QuadraticRegression();
        System.out.println("Curva de Regresión: " + qr.getRegressionCurve());
        qr.printPredictions();
        qr.printCorrelationAndDetermination();
    }
}