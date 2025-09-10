package algoritmo;

import estruturas.Stack;
import estruturas.Queue;
import modelo.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do algoritmo Flood Fill
 * Utiliza Pilha e Fila para armazenamento dos pixels
 */
public class FloodFillAlgorithm {
    private BufferedImage image;
    private int width;
    private int height;
    private List<BufferedImage> animationFrames;
    private int frameCounter;
    private final int FRAME_INTERVAL = 10; // Salva frame a cada 10 pixels
    
    public FloodFillAlgorithm(String imagePath) throws IOException {
        this.image = ImageIO.read(new File(imagePath));
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.animationFrames = new ArrayList<>();
        this.frameCounter = 0;
    }
    
    /**
     * Flood Fill usando Pilha (LIFO)
     */
    public void floodFillWithStack(int startX, int startY, Color newColor) {
        System.out.println("\n=== Iniciando Flood Fill com PILHA ===");
        System.out.println("Ponto inicial: (" + startX + ", " + startY + ")");
        System.out.println("Cor escolhida: " + newColor);
        
        // Cria cópia da imagem para não modificar a original
        BufferedImage workingImage = copyImage(image);
        animationFrames.clear();
        frameCounter = 0;
        
        // Cria pasta para animação se não existir
        new File("animation").mkdirs();
        
        // Obtém a cor original do pixel inicial
        int originalColor = workingImage.getRGB(startX, startY);
        int fillColor = newColor.getRGB();
        
        // Se a cor já é a mesma, não faz nada
        if (originalColor == fillColor) {
            System.out.println("Pixel já tem a cor desejada!");
            return;
        }
        
        // Cria a pilha e adiciona o pixel inicial
        Stack<Pixel> stack = new Stack<>();
        stack.push(new Pixel(startX, startY));
        
        // Marca pixels já visitados para evitar reprocessamento
        boolean[][] visited = new boolean[height][width];
        int pixelsProcessed = 0;
        
        while (!stack.isEmpty()) {
            Pixel current = stack.pop();
            int x = current.getX();
            int y = current.getY();
            
            // Verifica limites da imagem
            if (x < 0 || x >= width || y < 0 || y >= height) {
                continue;
            }
            
            // Verifica se já foi visitado
            if (visited[y][x]) {
                continue;
            }
            
            // Verifica se o pixel tem a cor original
            if (workingImage.getRGB(x, y) != originalColor) {
                continue;
            }
            
            // Marca como visitado e pinta o pixel
            visited[y][x] = true;
            workingImage.setRGB(x, y, fillColor);
            pixelsProcessed++;
            
            // Salva frame da animação
            if (pixelsProcessed % FRAME_INTERVAL == 0) {
                saveAnimationFrame(workingImage);
            }
            
            // Adiciona os 4 vizinhos na pilha (ordem: direita, esquerda, baixo, cima)
            stack.push(new Pixel(x + 1, y)); // Direita
            stack.push(new Pixel(x - 1, y)); // Esquerda
            stack.push(new Pixel(x, y + 1)); // Baixo
            stack.push(new Pixel(x, y - 1)); // Cima
        }
        
        // Salva frame final
        saveAnimationFrame(workingImage);
        
        System.out.println("Flood Fill com Pilha concluído!");
        System.out.println("Pixels processados: " + pixelsProcessed);
        System.out.println("Frames de animação gerados: " + animationFrames.size());
        
        // Salva a imagem final
        saveImage(workingImage, "output_stack.png");
    }
    
    /**
     * Flood Fill usando Fila (FIFO)
     */
    public void floodFillWithQueue(int startX, int startY, Color newColor) {
        System.out.println("\n=== Iniciando Flood Fill com FILA ===");
        System.out.println("Ponto inicial: (" + startX + ", " + startY + ")");
        System.out.println("Cor escolhida: " + newColor);
        
        // Cria cópia da imagem para não modificar a original
        BufferedImage workingImage = copyImage(image);
        animationFrames.clear();
        frameCounter = 0;
        
        // Cria pasta para animação se não existir
        new File("animation").mkdirs();
        
        // Obtém a cor original do pixel inicial
        int originalColor = workingImage.getRGB(startX, startY);
        int fillColor = newColor.getRGB();
        
        // Se a cor já é a mesma, não faz nada
        if (originalColor == fillColor) {
            System.out.println("Pixel já tem a cor desejada!");
            return;
        }
        
        // Cria a fila e adiciona o pixel inicial
        Queue<Pixel> queue = new Queue<>();
        queue.enqueue(new Pixel(startX, startY));
        
        // Marca pixels já visitados para evitar reprocessamento
        boolean[][] visited = new boolean[height][width];
        int pixelsProcessed = 0;
        
        while (!queue.isEmpty()) {
            Pixel current = queue.dequeue();
            int x = current.getX();
            int y = current.getY();
            
            // Verifica limites da imagem
            if (x < 0 || x >= width || y < 0 || y >= height) {
                continue;
            }
            
            // Verifica se já foi visitado
            if (visited[y][x]) {
                continue;
            }
            
            // Verifica se o pixel tem a cor original
            if (workingImage.getRGB(x, y) != originalColor) {
                continue;
            }
            
            // Marca como visitado e pinta o pixel
            visited[y][x] = true;
            workingImage.setRGB(x, y, fillColor);
            pixelsProcessed++;
            
            // Salva frame da animação
            if (pixelsProcessed % FRAME_INTERVAL == 0) {
                saveAnimationFrame(workingImage);
            }
            
            // Adiciona os 4 vizinhos na fila (ordem: direita, esquerda, baixo, cima)
            queue.enqueue(new Pixel(x + 1, y)); // Direita
            queue.enqueue(new Pixel(x - 1, y)); // Esquerda
            queue.enqueue(new Pixel(x, y + 1)); // Baixo
            queue.enqueue(new Pixel(x, y - 1)); // Cima
        }
        
        // Salva frame final
        saveAnimationFrame(workingImage);
        
        System.out.println("Flood Fill com Fila concluído!");
        System.out.println("Pixels processados: " + pixelsProcessed);
        System.out.println("Frames de animação gerados: " + animationFrames.size());
        
        // Salva a imagem final
        saveImage(workingImage, "output_queue.png");
    }
    
    /**
     * Cria uma cópia da imagem
     */
    private BufferedImage copyImage(BufferedImage source) {
        BufferedImage copy = new BufferedImage(
            source.getWidth(), 
            source.getHeight(), 
            BufferedImage.TYPE_INT_RGB
        );
        copy.getGraphics().drawImage(source, 0, 0, null);
        return copy;
    }
    
    /**
     * Salva um frame da animação
     */
    private void saveAnimationFrame(BufferedImage img) {
        animationFrames.add(copyImage(img));
        frameCounter++;
        
        // Salva o frame como arquivo
        try {
            String fileName = String.format("animation/frame_%04d.png", frameCounter);
            File outputFile = new File(fileName);
            ImageIO.write(copyImage(img), "png", outputFile);
        } catch (IOException e) {
            System.err.println("Erro ao salvar frame: " + e.getMessage());
        }
    }
    
    /**
     * Salva a imagem final
     */
    private void saveImage(BufferedImage img, String fileName) {
        try {
            File outputFile = new File(fileName);
            ImageIO.write(img, "png", outputFile);
            System.out.println("Imagem salva: " + fileName);
        } catch (IOException e) {
            System.err.println("Erro ao salvar imagem: " + e.getMessage());
        }
    }
    
    /**
     * Retorna os frames da animação
     */
    public List<BufferedImage> getAnimationFrames() {
        return animationFrames;
    }
    
    /**
     * Retorna a imagem original
     */
    public BufferedImage getOriginalImage() {
        return image;
    }
    
    /**
     * Retorna a largura da imagem
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Retorna a altura da imagem
     */
    public int getHeight() {
        return height;
    }
}