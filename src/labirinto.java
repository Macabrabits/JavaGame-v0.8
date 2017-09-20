import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Victor Hugo
 */
public class labirinto {

    public int l, c;
    public int lab[][];
    private Imagem brick;
    private Graphics g;
    public int exitX = 99,exitY = 99;

    public labirinto(String file) throws FileNotFoundException, IOException {
        this.g = g;
        BufferedReader entrada = new BufferedReader(new FileReader(file));
        String line = entrada.readLine();
        String valores[] = line.split(" ");
        l = Integer.parseInt(valores[0]);
        c = Integer.parseInt(valores[1]);
        lab = new int[l][c];
        for (int linha = 0; linha < l; linha++) {
            line = entrada.readLine();
            valores = line.split(" ");
            for (int coluna = 0; coluna < c; coluna++) {
                lab[linha][coluna] = Integer.parseInt(valores[coluna]);
            }
        }
    }

    public void desenhaLab(Graphics g) throws IOException {
        for (int v = 0; v < l; v++) {
            for (int w = 0; w < c; w++) {
                if (lab[v][w] == -1) {
                    brick = new Imagem(w * 32, v * 32, "brick.png");
                    brick.desenha(g);
                }
                if (lab[v][w] == -5) {
                    g.setColor(Color.black);
                    exitX = w;
                    exitY = v;                       
                    lab[v][w] = 0;
                }
                g.fillRect(exitX * 32, exitY * 32, 32, 32); 
            }
        }
    }

    public void loadObjects() {

    }

    public int[][] returnLab() {
        return lab;
    }

    public void print() {
        for (int v = 0; v < l; v++) {
            for (int w = 0; w < c; w++) {
                if(lab[v][w] == 0)
                    System.out.print(" "+ 0);
                else
                    System.out.print(lab[v][w]);
            }
            System.out.println();
        }
    }
}
