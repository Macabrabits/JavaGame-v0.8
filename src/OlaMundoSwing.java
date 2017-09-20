import java.io.IOException;
import javax.swing.JPanel;
public class OlaMundoSwing extends JPanel {
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        Jogo pong = new Jogo();
        pong.load(); // carrega arquivos e leitura de arquivo
        pong.setVisible(true);
        pong.run();
    }    
}