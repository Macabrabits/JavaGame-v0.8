import java.io.IOException;
/**
 *
 * @author Victor Hugo
 */
public class enemy4 extends enemyPai {
    public int rew = 0;

    public enemy4(int X,int Y) throws IOException {
        super( X, Y);
        this.x = X;
        this.y = Y;
        imagem = new Imagem(X * 32,Y * 32,"enemy4.png");
    }

    public void move(int playerX, int playerY) throws IOException {
        m[y][x] ++;
        if (x < 23) {
            if (m[y][x + 1] == 1) {//direita
                rew = 2;
            }
        }
        
        if (y < 13) {
            if (m[y + 1][x] == 1) {//baixo
                rew = 3;
            }
        }
        if (x > 0) {
            if (m[y][x - 1] == 1) {//esquerda
                rew = 4;
            }
        }
        
        
        if (y > 0) {
            if (m[y - 1][x] == 1) {//cima
                rew = 5;
            }
        }
        
        ///////////////
        if (x > 0) { //esquerda
            if (m[y][x - 1] == 0) {
                rew = 6;
            }
        }
        if (y > 0) {//cima
            if (m[y - 1][x] == 0) {
                rew = 7;
            }
        }
        if (x < 24) {//direita
            if (m[y][x + 1] == 0) {
                rew = 8;
            }
        }

        if (y < 13) {//baixo
            if (m[y + 1][x] == 0) {
                rew = 9;
            }
        }
        ////////       
        if (rew == 2) {//esquerda
            x++;
        }
        if (rew == 3) {//cima
            y++;
        }
        if (rew == 4) {//direita
            x--;
        }
        if (rew == 5) {//baixo
            y--;
        }        
        
        if (rew == 6) {//esquerda
            x--;
        }
        if (rew == 7) {//cima
            y--;
        }
        if (rew == 8) {//direita
            x++;
        }
        if (rew == 9) {//baixo
            y++;
        }
        
        if(rew == 0){
            path = map.findPath(x, y, playerX, playerY);
            n = path.get(0);
            x = n.getX();
            y = n.getY();
        }
        rew = 0;
        imagem.set(x*32, y*32);
    }
}
