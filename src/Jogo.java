
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import astar.Node;
import java.io.PrintWriter;

public class Jogo extends JPanel implements KeyListener, MouseListener {

    private boolean jogoAtivo, mainMenu, looserMenu;
    private JPanel mousepanel;
    private Imagem Player, imagem2, menu, brick, lmenu;
    private enemy1 ene1;
    private enemy2 ene2;
    private enemy3 ene3;
    private enemy4 ene4;
    private final playSound soundWin = new playSound();
    private final playSound soundBackground = new playSound();
    private final playSound soundMenu = new playSound();
    public int playerX = 0, playerY = 0, H, exitX, exitY, var = 1, steps = 0, stepsCount, lvl = 1;    //
    private int[][] lab;
    private java.util.List<Node> path;
    private int framerate = 0;
    public labirinto labirinto = new labirinto("level" + lvl + "/labirinto.txt");

    JFrame frame;

    public Jogo() throws IOException {
        frame = new JFrame("Ola Mundo Grafico");
        frame.setSize(800 + 7, 480 - 4);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);//desabilita maximizar janela
        frame.add(this); // adiciona o JPanel na janela
        this.setBackground(Color.white); // muda cor de fundo do JPanel
        this.addKeyListener(this); // registra o JPanel na lista de eventos a serem repassados
        setFocusable(true); // permite o JPanel receber os eventos

        jogoAtivo = true;
        mainMenu = true;
        looserMenu = false;

    }

    @Override
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    /*
    Metodo carrega imagens e faz leitura de arquivos
     */
    public void load() {
        // carrega uma imagem
        try {
            Player = new Imagem(0, 0, "player.bmp");
            menu = new Imagem(0, -16, "menu.bmp");
            lmenu = new Imagem(0, -16, "looserMenu.jpg");
            placeObjects();
        } catch (IOException e) {
            System.out.println("Impossosivel carregar a imagem:" + e);
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);// limpa a janela
        try {
            labirinto.desenhaLab(g);
        } catch (IOException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        Player.desenha(g);
        ene1.desenha(g);
        ene2.desenha(g);
        ene3.desenha(g);
        ene4.desenha(g);
        drawGrid(g);
        if (looserMenu) {
            looserMenu(g);
        }
        if (mainMenu) {
            mainMenu(g);
        }

    }

    //Exemplo do loop principal do jogo
    public void run() throws IOException, InterruptedException {

        while (jogoAtivo) {
            // faz as simulações fisicas
            // desenha a tela
            repaint();
            // delay de 10 milisegundos
            Thread.sleep(10);
            if (!mainMenu) {
                framerate++;
                if (framerate % 96 == 0) {
                    ene3.move(playerX, playerY);
                }
                if (framerate % 24 == 0) {
                    ene4.move(playerX, playerY);
                }
                if (ene1.collided() || ene2.collided() || ene3.collided()) { //Desenha o inimigo matando antes de fechar o jogo
                    endGame();
                    looserMenu = true;
                }

            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (looserMenu) {
            looserMenu = false;
            mainMenu = true;
        }
        if (!mainMenu) {
            movePlayer(e);
            try {
                ene1.move(playerX, playerY);
                ene2.move(playerX, playerY);
            } catch (IOException ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (mainMenu) {
            controleMenu(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (playerX == labirinto.exitX && playerY == labirinto.exitY) {
            win();
        }
    }

    public int[][] placeObjects() throws IOException {
        lab = labirinto.lab;
        int l = labirinto.l;
        int c = labirinto.c;
        for (int v = 0; v < l; v++) {
            for (int w = 0; w < c; w++) {
                switch (lab[v][w]) {
                    case -2:
                        ene1 = new enemy1(w, v);
                        lab[v][w] = 0;
                        break;
                    case -3:
                        ene2 = new enemy2(w, v);
                        lab[v][w] = 0;
                        break;
                    case -4:
                        ene3 = new enemy3(w, v);
                        lab[v][w] = 0;
                        break;
                    case -6:
                        ene4 = new enemy4(w, v);
                        lab[v][w] = 0;
                        break;
                    case -5:
                        exitX = w;
                        exitY = v;
                        break;
                    default:
                        break;
                }
            }
        }
        return lab;
    }

    public void drawGrid(Graphics g) {
        for (int pixel = 0; pixel <= getHeight(); pixel += 32) {
            g.drawLine(0, pixel, getWidth(), pixel);
        }
        for (int pixel = 0; pixel <= getWidth(); pixel += 32) {
            g.drawLine(pixel, 0, pixel, getWidth());
        }
    }

    public void movePlayer2(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D && lab[playerY][playerX + 1] == 0 && playerX + 1 < lab[0].length) {
            playerX++;
        } else if (e.getKeyCode() == KeyEvent.VK_A && lab[playerY][playerX - 1] == 0 && playerX > 0) {
            playerX--;
        } else if (e.getKeyCode() == KeyEvent.VK_S && lab[playerY + 1][playerX] == 0 && playerY + 1 < lab[0].length) {
            playerY++;
        } else if (e.getKeyCode() == KeyEvent.VK_W && lab[playerY - 1][playerX] == 0 && playerY > 0) {
            playerY--;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            frame.dispose();
            jogoAtivo = false;
        } else {
            //  endGame();
            looserMenu = true;
        }
        steps++;
        Player.set(playerX * 32, playerY * 32);
    }

    public void movePlayer(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D && playerX + 1 < lab[0].length) {
            playerX++;
        } else if (e.getKeyCode() == KeyEvent.VK_A && playerX > 0) {
            playerX--;
        } else if (e.getKeyCode() == KeyEvent.VK_S && playerY + 1 < lab[0].length) {
            playerY++;
        } else if (e.getKeyCode() == KeyEvent.VK_W && playerY > 0) {
            playerY--;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            frame.dispose();
            jogoAtivo = false;
        }
        if (lab[playerY][playerX] == -1) {
            endGame();
            System.out.println("Killed by fire!");
            looserMenu = true;
        }
        steps++;
        Player.set(playerX * 32, playerY * 32);
    }

    public void win() {
        System.out.println(labirinto.exitX + " " + labirinto.exitY);
        soundBackground.close();
        soundWin.playSound("music.wav");
        if (lvl == 2) {
            lvl = 1;
        } else {
            lvl++;
        }
        mainMenu = true;
        endGame();
        System.out.println("You Win!!");
    }

    public void endGame() {
        stepsCount = steps;
        steps = 0;
        soundBackground.close();
        playerX = 0;
        playerY = 0;
        try {
            PrintWriter writer = new PrintWriter("statistics.txt", "UTF-8");
            writer.println(lvl);
            writer.close();
            labirinto = new labirinto("level" + lvl + "/labirinto.txt");
            load();
        } catch (IOException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mainMenu(Graphics g) {
        super.paint(g);
        menu.desenha(g);
        g.setColor(Color.CYAN);
        g.fillOval(200, getHeight() / 2 - 60 + H * 60, 20, 20);
        butom(g, "Play", 230, getHeight() / 2 - 40);
        butom(g, "Exit", 230, getHeight() / 2 + 20);
        butom(g, "", 230, getHeight() / 2 + 80);
    }

    public void looserMenu(Graphics g) {
        super.paint(g);
        lmenu.desenha(g);
        g.setColor(Color.CYAN);
        g.fillOval(200, getHeight() / 2 - 60 + H * 60, 20, 20);
        butom(g, "Steps = " + stepsCount, 10, getHeight() / 2 - 40);
        butom(g, "Press any key", 10, getHeight() / 2 + 20);

    }

    public void butom(Graphics g, String text, int X, int Y) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(X, Y - 27, 26 * text.length(), 36);
        g.setFont(new Font("Courier New", Font.BOLD, 36));
        g.setColor(Color.CYAN);
        g.drawString(text, X + 6, Y);
    }

    public void controleMenu(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            soundMenu.playSound("sounds/dom" + var + ".wav");
            var++;
            if (var == 10) {
                var = 2;
            }
            if (H == 0) {
                H = 1;
            } else {
                H--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            soundMenu.playSound("sounds/dom" + var + ".wav");
            var++;
            if (var == 10) {
                var = 2;
            }
            if (H == 1) {
                H = 0;
            } else {
                H++;
            }
        }

        if (H == 0 && e.getKeyCode() == KeyEvent.VK_ENTER) { //Play
            mainMenu = false;
            soundBackground.playSound("background.wav");
        }
        if (H == 1 && e.getKeyCode() == KeyEvent.VK_ENTER) { // Exit
            frame.dispose();
            jogoAtivo = false;
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}
