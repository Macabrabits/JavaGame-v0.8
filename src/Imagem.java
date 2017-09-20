import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Imagem{
        private int x; 
        private int y;
        final int largura;
        final int altura;
        final BufferedImage imagem;
        private int srcx1; // variavel para animar sprite
	public Imagem(int x, int y, String arquivo) throws IOException {
                this.x = x;
                this.y = y;
                
                imagem = ImageIO.read(new File(arquivo));
                
                this.largura = imagem.getWidth();
                this.altura  = imagem.getHeight();
                this.srcx1  = 0;
	}
        public Imagem(int x, int y, int largura, int altura, String arquivo) throws IOException {
                this.x = x;
                this.y = y;
                this.largura = largura;
                this.altura  = altura;
                imagem = ImageIO.read(new File(arquivo));
	}

	public void desenha(Graphics g ) {
            // desenha a imagem no canto da tela
            //  int dstx1, int dsty1, int dstx2, int dsty2,
            //  int srcx1, int srcy1, int srcx2, int srcy2,
            //  http://pontov.com.br/site/java/48-java2d/111-trabalhando-com-imagens
            g.drawImage(this.imagem, this.x, this.y, this.x+this.largura, this.y+this.altura,
                        this.srcx1, 0, this.srcx1+this.largura,this.altura,null);
         
            
	}
        public void set(int x,int y) {
            this.x = x;
            this.y = y;
	}

	public void teclaLiberada(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_D){
                this.srcx1 = (this.srcx1+this.largura)%this.imagem.getWidth();
                this.x += 32;
            }
            if (e.getKeyCode() == KeyEvent.VK_A){
                this.srcx1 = (this.srcx1+this.largura)%this.imagem.getWidth();
                this.x -= 32;
            }
            if (e.getKeyCode() == KeyEvent.VK_S){
                this.srcx1 = (this.srcx1+this.largura)%this.imagem.getWidth();
                this.y += 32;
            }
            if (e.getKeyCode() == KeyEvent.VK_W){
                this.srcx1 = (this.srcx1+this.largura)%this.imagem.getWidth();
                this.y -= 32;
            }
            
	}
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }

        
       
}
