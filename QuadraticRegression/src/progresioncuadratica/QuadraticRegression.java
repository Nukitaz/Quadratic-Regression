package progresioncuadratica;

public class QuadraticRegression {
    double sales[] = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};
    double advertising[] = {23, 26, 30, 34, 43, 48, 52, 57, 58};
    int n = sales.length;

    double b0, b1, b2;

    private double determinant(double m[][]) {
        return m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1]) -
               m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0]) +
               m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0]);
    }

    private void calculateCoefficients() {
        double sumX = 0, sumY = 0, sumX2 = 0, sumX3 = 0, sumX4 = 0;
        double sumXY = 0, sumX2Y = 0;
        
        for (int i = 0; i < n; i++) {
            double x = advertising[i];
            double y = sales[i];
            sumX += x;
            sumY += y;
            sumX2 += x * x;
            sumX3 += x * x * x;
            sumX4 += x * x * x * x;
            sumXY += x * y;
            sumX2Y += x * x * y;
        }

        // Resolución del sistema de ecuaciones lineales (matriz de coeficientes)  
        double matrix[][] = {
            {n, sumX, sumX2},       // Fila 0: (0,0), (0,1), (0,2)
            {sumX, sumX2, sumX3},   // Fila 1: (1,0), (1,1), (1,2)
            {sumX2, sumX3, sumX4}   // Fila 2: (2,0), (2,1), (2,2)
        };
        //vector
        double constants[] = {sumY, sumXY, sumX2Y};

        // Resolución usando determinantes
        double det = determinant(matrix);
        //se forma una nueva matriz, sustituyendo la primera columna de matrix
        b0 = determinant(new double[][]{{constants[0], matrix[0][1], matrix[0][2]},
                                        {constants[1], matrix[1][1], matrix[1][2]},
                                        {constants[2], matrix[2][1], matrix[2][2]}}) / det;
        //se forma una nueva matriz, sustituyendo la segunda columna de matrix
        b1 = determinant(new double[][]{{matrix[0][0], constants[0], matrix[0][2]},
                                        {matrix[1][0], constants[1], matrix[1][2]},
                                        {matrix[2][0], constants[2], matrix[2][2]}}) / det;
        //se forma una nueva matriz, sustituyendo la tercera columna de matrix
        b2 = determinant(new double[][]{{matrix[0][0], matrix[0][1], constants[0]},
                                        {matrix[1][0], matrix[1][1], constants[1]},
                                        {matrix[2][0], matrix[2][1], constants[2]}}) / det;
    }

    //constructor
    public QuadraticRegression() {
        calculateCoefficients(); //llama al metodo calculateCoefficients para calcular los coeficientes de la ecuacion
    }

    public String getRegressionCurve() {
        return "y (b0)= " + b0 + " + (b1): " + b1 + " * x + (b2): " + b2 + " * x^2";
    }

    //predicciones
    public void printPredictions() {
        double testValues[] = {20, 25, 35, 50, 60};
        System.out.println("Predicciones:");
        for (double x : testValues) {
            double y = b0 + b1 * x + b2 * x * x; // formula general de la regresion cuadratica
            System.out.println("Para x = " + x + ", y = " + y);
        }
    }

    public void printCorrelationAndDetermination() {
        double yMean = 0;
        for (double y : sales) {
            yMean += y;
        }
        yMean /= n;

        double ssTot = 0, ssRes = 0;
        for (int i = 0; i < n; i++) {
            double yPred = b0 + b1 * advertising[i] + b2 * advertising[i] * advertising[i];
            ssTot += Math.pow(sales[i] - yMean, 2); //ssTot: suma total de cuadrados
            ssRes += Math.pow(sales[i] - yPred, 2); //
        }

        double r2 = 1 - (ssRes / ssTot);
        System.out.println("Coeficiente de Determinación (R^2): " + r2);
    }
}