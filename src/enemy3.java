import java.io.IOException;

/**
 *
 * @author Victor Hugo
 */
public class enemy3 extends enemyPai {

    public enemy3(int X,int Y) throws IOException {
        super( X, Y);
        this.x = X;
        this.y = Y;
        imagem = new Imagem(X * 32, Y * 32, "enemy3.png");
        
    }

    public void move(int playerX, int playerY) throws IOException {
        path = map.findPath(x, y, playerX, playerY);
        if (path.size() == 1) {
            System.out.println("Killed by Enemy 3");
            x = playerX;
            y = playerY;
            dead = true;
        }
        if (path.size() > 1) {
            n = path.get(0);
            x = n.getX();
            y = n.getY();
        }
        imagem.set(x * 32, y * 32);
    }
}
