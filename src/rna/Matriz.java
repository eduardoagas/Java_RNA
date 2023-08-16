/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rna;

/**
 *
 * @author Du
 */
public class Matriz {
    
    double[][] data;
    int rows;
    int cols;

    public double[] toVector(){
        int linha = data.length;
        int coluna = data[0].length;
        double[] v = new double[linha*coluna];
        int k = 0;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++, k++) {
                v[k] = data[i][j];
            }
        }
        return v;
    }
    
    public void transp() {
        double[][] transp = new double[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transp[i][j] = data[j][i];
            }
        }
        data = transp;
        int aux = rows;
        rows = cols;
        cols = aux;
    }
    
    public static void printVetor(double[] v){
        for (int j = 0; j < v.length; j++) {
                System.out.print(v[j] + "\n");
            }
    }

    public void printMatriz() {
        int linha = data.length;
        int coluna = data[0].length;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public Matriz(double[][] matriz){
        data = matriz;
        rows = matriz.length;
        cols = matriz[0].length;
    }
    
    public Matriz(double[] vetor, int linha, int coluna) {
        rows = linha;
        cols = coluna;
        double[][] matriz = new double[linha][coluna];
        int k = 0;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                matriz[i][j] = vetor[k];
                k++;
            }
        }
        data = matriz;
    }
    
    public Matriz(double max, int linha, int coluna){
        rows = linha;
        cols = coluna;
        int s =linha*coluna;
        double[] vetor = new double[s];
        for(int i = 0; i < s; i++){
            //vetor[i] = (Math.floor(Math.random()*(max)) +1.0d);
            //vetor[i] = (Math.floor(Math.random()*(max)) - 1);
            vetor[i] = ((Math.random()*(max)) - 1);
        }
        double[][] matriz = new double[linha][coluna];
        int k = 0;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                matriz[i][j] = vetor[k];
                k++;
            }
        }
        data = matriz;
    }
    
    public static Matriz copiaMatriz(Matriz b){
        Matriz a = new Matriz(1, b.rows, b.cols);
        int linha = b.rows;
        int coluna = b.cols;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                a.data[i][j] = b.data[i][j];
            }
        }
        return a;
    }

    public static Matriz multiplicaMatriz(Matriz a, Matriz b) {
        try{
            Matriz c = new Matriz(0.0d, a.rows, b.cols);
            double soma;
            for (int linha = 0; linha < a.data.length; linha++) {
                for (int coluna = 0; coluna < b.data[0].length; coluna++) {
                    soma = 0.0d;
                    for (int k = 0; k < a.data[0].length; k++) {
                        soma += a.data[linha][k] * b.data[k][coluna];
                    }
                    c.data[linha][coluna] = soma;
                }
            }
            return c;
        }catch(Exception e){
            System.out.println("Não foi  possível multiplicar a matriz.");
            return null;
        }
    }
    
    public void somaMatriz(Matriz b) {
        int linha = data.length;
        int coluna = data[0].length;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                data[i][j] = data[i][j] + b.data[i][j];
            }
        }
    }
    
    public void subtraiMatriz(Matriz b) {
        int linha = data.length;
        int coluna = data[0].length;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                data[i][j] = data[i][j] - b.data[i][j];
            }
        }
    }
    
    public void multiplicaEscalar(double esc) {
        int linha = data.length;
        int coluna = data[0].length;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                data[i][j] = data[i][j]*esc;
            }
        }
    }
    
    public void hadamard(Matriz b) {
        int linha = data.length;
        int coluna = data[0].length;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                data[i][j] = data[i][j] * b.data[i][j];
            }
        }
    }
    
    public void sigmoid() {
        int linha = data.length;
        int coluna = data[0].length;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                data[i][j] = 1/(1 + Math.exp(data[i][j]*(-1)));
            }
        }
    }
    
    public void dsigmoid(){
        int linha = data.length;
        int coluna = data[0].length;
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                data[i][j] = data[i][j]*(1 - data[i][j]);
            }
        }
    }

}
