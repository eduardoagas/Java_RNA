/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Du
 */
public class RNA {

    /**
     * @param args the command line arguments
     */
    
    final long treinosPorSegundo = 100; //200 //100
    String nome;
    int ni_nodes; //input
    int nh_nodes; //hidden
    int no_nodes; //output
    Matriz bias_ih;
    Matriz bias_ho;
    Matriz weights_ih;
    Matriz weights_ho;
    double learning_rate;
    double score; //TO DEVELOP IDEA
    public double[] last_output;
        
    public RNA(int ni_nodes, int nh_nodes, int no_nodes) {
        this.ni_nodes = ni_nodes;
        this.nh_nodes = nh_nodes;
        this.no_nodes = no_nodes;        
        randomize();
    }
    
    
    public final void randomize(){
        this.learning_rate = 0.1;
        this.bias_ih = new Matriz(2.0d, nh_nodes, 1); //input to hidden bias
        this.bias_ho = new Matriz(2.0d, no_nodes, 1); //hidden to output bias
        this.weights_ih = new Matriz(2.0d, nh_nodes, ni_nodes);
        this.weights_ho = new Matriz(2.0d, no_nodes, nh_nodes); 
        this.last_output = new double[10];
    }
    
    public void train(double[] inp, double[] target){        
        //----------------feedforward--------------  
        //input -> hidden
        Matriz input = new Matriz(inp, inp.length, 1);
        Matriz hidden = Matriz.multiplicaMatriz(weights_ih, input);
        hidden.somaMatriz(bias_ih);
        hidden.sigmoid();
        //hidden -> output
        Matriz output = Matriz.multiplicaMatriz(weights_ho, hidden);
        output.somaMatriz(bias_ho);
        output.sigmoid();
        
        //---------------backpropagation--------------
        //output -> hidden
        Matriz expected = new Matriz(target, target.length, 1);
        Matriz output_error = Matriz.copiaMatriz(expected);
        output_error.subtraiMatriz(output);
        Matriz d_output = Matriz.copiaMatriz(output);
        d_output.dsigmoid();
        Matriz hidden_t = Matriz.copiaMatriz(hidden);
        hidden_t.transp();
        Matriz gradient_ho = Matriz.copiaMatriz(output_error);
        gradient_ho.hadamard(d_output);
        gradient_ho.multiplicaEscalar(learning_rate);
        bias_ho.somaMatriz(gradient_ho);
        Matriz weights_ho_deltas = Matriz.multiplicaMatriz(gradient_ho, hidden_t);
        weights_ho.somaMatriz(weights_ho_deltas);
        
        //hidden - > input
        Matriz weights_ho_t = Matriz.copiaMatriz(weights_ho);
        weights_ho_t.transp();
        Matriz hidden_error = Matriz.multiplicaMatriz(weights_ho_t, output_error);
        Matriz d_hidden = Matriz.copiaMatriz(hidden);
        d_hidden.dsigmoid();
        Matriz input_t = Matriz.copiaMatriz(input);
        input_t.transp();
        Matriz gradient_ih = Matriz.copiaMatriz(hidden_error);
        gradient_ih.hadamard(d_hidden);
        gradient_ih.multiplicaEscalar(learning_rate);
        bias_ih.somaMatriz(gradient_ih);
        Matriz weights_ih_deltas = Matriz.multiplicaMatriz(gradient_ih, input_t);
        weights_ih.somaMatriz(weights_ih_deltas);
        
    }
    
    public void carregarDados(){            
         Loggie.lerLogRna(this, nome);
    }
     public RNA(int ni_nodes, int nh_nodes, int no_nodes, String nome) {
         this(ni_nodes, nh_nodes, no_nodes);
         this.nome = nome;
     }
    
    public double[] predict(double[] inp){
        //input -> hidden
        Matriz input = new Matriz(inp, inp.length, 1);
        Matriz hidden = Matriz.multiplicaMatriz(weights_ih, input);
        hidden.somaMatriz(bias_ih);
        hidden.sigmoid();
        //hidden -> output
        Matriz output = Matriz.multiplicaMatriz(weights_ho, hidden);
        output.somaMatriz(bias_ho);
        output.sigmoid();
        
        return output.toVector();
    }
    
    public void display(double[][] inputs, int num){
        for(int i = 0; i < num; i++){
            Matriz.printVetor(this.predict(inputs[i]));
            System.out.println("");
        }
    }
/*    
    public static void main(String[] args) throws IOException {
        RNA rna = new RNA(2,3,1);
        //DATASET
        int num = 4; //numero de entradas e saídas
        double[][] inputs ={
            {1.0,1.0},
            {1.0,0.0},
            {0.0,1.0},
            {0.0,0.0}             
        };
        double[][] targets={
                {0.0},
                {1.0},
                {1.0},
                {0.0}
        };
        rna.display(inputs, num);
    }
  */  
    public void executarTreino(double[][] inputs, double[][] targets, int num) throws IOException{
        
        int index; 
        double[] valorTeste1 = inputs[0];
        //double[] valorTeste2 = inputs[1];
        //System.out.println("valorTeste1 =" + valorTeste1[0]);
        //System.out.println("valorTeste2 =" + valorTeste2[1]);
        //double acertoMinimo1 = 0.01;
        double acertoMinimo = 0.9;
        //System.out.println("targets[0][0] = " + targets[0][0]);
        //System.out.println("targets[1][0] = " + targets[1][0]);
        //double condicaoParada1 = targets[0][0] + acertoMinimo1; //mínima prox de zero
        double condicaoParada = targets[0][0]*acertoMinimo; //máximo prox de um
        //System.out.println("targets[0][0] = " + targets[0][0]);
        //System.out.println("condicao de parada = " + targets[0][0]*acertoMinimo);
        //System.out.println("condicaoparada1 = " + condicaoParada1);
        //System.out.println("condicaoparada2 = " + condicaoParada2);
        double predicao1;
        int frame = 0;
        
        //System.out.println("Começou assim => ");
        //display(inputs, num);
        for (int i = 0; i < treinosPorSegundo; i++) {
                index =(int) Math.floor(Math.random()*num);
                train(inputs[index], targets[index]); //targets[num]
            }
        Loggie.gravarLog(this);
        /*
        while(true){
            for (int i = 0; i < treinosPorSegundo; i++) {
                index =(int) Math.floor(Math.random()*num);
                train(inputs[index], targets[index]);
            }
            frame++;
            predicao1 = predict(valorTeste1)[0];
          //  predicao2 = predict(valorTeste2)[0];
            //System.out.println("frame = " + frame);
            //display(inputs, num);
            //System.out.println("");
            //System.out.println("predicao = " + predicao1);
            if(predicao1 > condicaoParada){
                //Criar log
                //Loggie.gravarLog(this);
                break;
            }
        }
        */
        //System.out.println("Terminou assim =>");
        //display(inputs,num);
        /*System.out.println("");
        System.out.println("");
        System.out.println("Dados");
        System.out.println("pesos ih");
        weights_ih.printMatriz();
        System.out.println("pesos ho");
        weights_ho.printMatriz();
        System.out.println("bias ih");
        bias_ih.printMatriz();
        System.out.println("bias oh");
        bias_ho.printMatriz();
        */
        
    }
    
    /*
    public static void main(String[] args) throws IOException {
        
        RNA rna = new RNA(2,3,1);//entrada, oculta, saída
        //DATASET
        int num = 4; //numero de entradas e saídas
        double[][] inputs ={
            {1.0,1.0},
            {1.0,0.0},
            {0.0,1.0},
            {0.0,0.0}             
        };
        double[][] targets={
                {0.0},
                {1.0},
                {1.0},
                {0.0}
        };
        int index;
        long treinosPorSegundo = 10000; 
        double[] valorTeste1 = inputs[0];
        double[] valorTeste2 = inputs[1];
        System.out.println("valorTeste1 =" + valorTeste1[0]);
        System.out.println("valorTeste2 =" + valorTeste2[1]);
        double acertoMinimo1 = 0.04;
        double acertoMinimo2 = 0.98;
        System.out.println("targets[0][0] = " + targets[0][0]);
        System.out.println("targets[1][0] = " + targets[1][0]);
        double condicaoParada1 = targets[0][0] + acertoMinimo1; //mínima prox de zero
        double condicaoParada2 = targets[1][0]*acertoMinimo2; //máximo prox de um
        System.out.println("condicaoparada1 = " + condicaoParada1);
        System.out.println("condicaoparada2 = " + condicaoParada2);
        double predicao1;
        double predicao2;
        int frame = 0;
        
        System.out.println("Começou assim => ");
        rna.display(inputs, num);
        
        while(true){
            for (int i = 0; i < treinosPorSegundo; i++) {
                index =(int) Math.floor(Math.random()*num);
                rna.train(inputs[index], targets[index]);
            }
            frame++;
            predicao1 = rna.predict(valorTeste1)[0];
            predicao2 = rna.predict(valorTeste2)[0];
            //System.out.println("frame = " + frame);
            //rna.display(inputs, num);
            //System.out.println("");
            if(predicao1 < condicaoParada1 && predicao2 > condicaoParada2){
                //Criar log
                Loggie.gravarLog(rna);
                break;
            }
        }
        
        System.out.println("Terminou assim =>");
        rna.display(inputs,num);
        System.out.println("");
        System.out.println("");
        System.out.println("Dados");
        System.out.println("pesos ih");
        rna.weights_ih.printMatriz();
        System.out.println("pesos ho");
        rna.weights_ho.printMatriz();
        System.out.println("bias ih");
        rna.bias_ih.printMatriz();
        System.out.println("bias oh");
        rna.bias_ho.printMatriz();
        
        
    }
    
    
    */
}
