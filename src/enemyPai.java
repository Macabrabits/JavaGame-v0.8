
import astar.Map;
import astar.Node;
import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Victor Hugo
 */
public class enemyPai {
    BufferedReader entrada = new BufferedReader(new FileReader("statistics.txt"));
    String lvl = entrada.readLine();
    public int x, y;
    public Imagem imagem;
    public Graphics g;
    public Node n;
    public int[][] lab;
    public java.util.List<Node> path;
    public int[][] m = labirinto("level"+lvl+"/labirinto.txt");
    public Map map = new Map(m);
    public boolean dead = false;
    

    public enemyPai(int X, int Y) throws IOException {
        this.x = X;
        this.y = Y;
        imagem = new Imagem(X * 32, Y * 32, "enemy.bmp");
    }


    
    
    public void desenha(Graphics g){
        imagem.desenha(g);
    }
    
    public boolean collided(){
        return dead;
    }
    

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] labirinto(String file) throws FileNotFoundException, IOException {
        BufferedReader entrada = new BufferedReader(new FileReader(file));
        String line = entrada.readLine();
        String valores[] = line.split(" ");
        int l, c;
        l = Integer.parseInt(valores[0]);
        c = Integer.parseInt(valores[1]);
        int lab[][] = new int[l][c];
        for (int linha = 0; linha < l; linha++) {
            line = entrada.readLine();
            valores = line.split(" ");
            for (int coluna = 0; coluna < c; coluna++) {
                lab[linha][coluna] = Integer.parseInt(valores[coluna]);
            }
            for (int v = 0; v < l; v++) {
                for (int w = 0; w < c; w++) {
                    if (lab[v][w] != -1) {
                        lab[v][w] = 0;
                    }
                }
            }
        }
        return lab;
    }

}
