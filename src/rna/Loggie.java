/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Du
 */
public class Loggie {
    
        public static void gravarLog(RNA rna) throws IOException {
            
            FileWriter arq = new FileWriter((env.RNApath + rna.nome + ".txt"));
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf("#Pesos IH#");
            for (int i = 0; i < rna.weights_ih.rows; i++) {
                gravarArq.printf("%n");
                for (int j = 0; j < rna.weights_ih.cols; j++) {
                    gravarArq.printf("%s ", String.valueOf(rna.weights_ih.data[i][j]));
                }          
            }
            gravarArq.printf("%n");
            gravarArq.printf("#Pesos HO#");
            for (int i = 0; i < rna.weights_ho.rows; i++) {
                gravarArq.printf("%n");
                for (int j = 0; j < rna.weights_ho.cols; j++) {
                    gravarArq.printf("%s ", String.valueOf(rna.weights_ho.data[i][j]));
                }          
            }
            gravarArq.printf("%n");
            gravarArq.printf("#bias IH#");
            for (int i = 0; i < rna.bias_ih.rows; i++) {
                gravarArq.printf("%n");
                for (int j = 0; j < rna.bias_ih.cols; j++) {
                    gravarArq.printf("%s ", String.valueOf(rna.bias_ih.data[i][j]));
                }          
            }
            gravarArq.printf("%n");
            gravarArq.printf("#bias HO#");
            for (int i = 0; i < rna.bias_ho.rows; i++) {
                gravarArq.printf("%n");
                for (int j = 0; j < rna.bias_ho.cols; j++) {
                    gravarArq.printf("%s ", String.valueOf(rna.bias_ho.data[i][j]));
                }          
            }
            

            arq.close();
        }
    
    	public static void lerLogRna(RNA rna, String log) {
		File arquivo = new File(env.LogsPath + log + ".txt");
                readContent(rna, arquivo);
	}
        
        
	public static void readContent(RNA rna, File f){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String linha;
                        linha = reader.readLine();
                        linha = reader.readLine();
                        for (int i = 0; i < rna.nh_nodes; i++) {
                            for (int j = 0; j < rna.ni_nodes; j++) {
                                rna.weights_ih.data[i][j] = Double.valueOf(linha.split(" ")[j]);
                            }
                            linha = reader.readLine();
                        }
                        //System.out.println(linha);
                        linha = reader.readLine();
                        for (int i = 0; i < rna.no_nodes; i++) {
                            for (int j = 0; j < rna.nh_nodes; j++) {
                                rna.weights_ho.data[i][j] = Double.valueOf(linha.split(" ")[j]);
                            }
                            linha = reader.readLine();
                        }
                        linha = reader.readLine();
                        for (int i = 0; i < rna.nh_nodes; i++) {
                            for (int j = 0; j < 1; j++) {
                                rna.bias_ih.data[i][j] = Double.valueOf(linha.split(" ")[j]); 
                            }
                            linha = reader.readLine();
                        }
                        linha = reader.readLine();
                        for (int i = 0; i < rna.no_nodes; i++) {
                            for (int j = 0; j < 1; j++) {
                                rna.bias_ho.data[i][j] = Double.valueOf(linha.split(" ")[j]); 
                            }
                            linha = reader.readLine();
                        }
		} 
                catch (FileNotFoundException fileNotFound){
			fileNotFound.printStackTrace();
		} catch (IOException ioex){
			ioex.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
