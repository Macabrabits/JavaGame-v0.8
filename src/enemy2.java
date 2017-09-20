
import java.io.IOException;

/**
 *
 * @author Victor Hugo
 */
public class enemy2 extends enemyPai {

    public enemy2(int X,int Y) throws IOException {
        super( X, Y);
        this.x = X;
        this.y = Y;
        imagem = new Imagem(X * 32, Y * 32, "enemy2.png");
    }

    public void move(int playerX, int playerY) throws IOException {
        path = map.findPath(x, y, playerX, playerY);
        if (path.size() <= 2) {
            System.out.println("Killed by Enemy 2");
            x = playerX;
            y = playerY;
            dead = true;
        }
        if (path.size() < 5) {
            n = path.get(0);
            x = n.getX();
            y = n.getY();
        }
        imagem.set(x * 32, y * 32);
    }
}
