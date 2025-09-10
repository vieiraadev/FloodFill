package modelo;

/**
 * Classe que representa um pixel na imagem
 * Armazena as coordenadas (x, y) de um ponto específico
 * 
 */
public class Pixel {
    private int x;  // Coordenada X (horizontal) do pixel
    private int y;  // Coordenada Y (vertical) do pixel
    
    /**
     * Construtor do Pixel
     * 
     * @param x Coordenada X do pixel (coluna na imagem)
     * @param y Coordenada Y do pixel (linha na imagem)
     */
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Obtém a coordenada X do pixel
     * 
     * @return Valor da coordenada X
     */
    public int getX() {
        return x;
    }
    
    /**
     * Define a coordenada X do pixel
     * 
     * @param x Nova coordenada X
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Obtém a coordenada Y do pixel
     * 
     * @return Valor da coordenada Y
     */
    public int getY() {
        return y;
    }
    
    /**
     * Define a coordenada Y do pixel
     * 
     * @param y Nova coordenada Y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Verifica se este pixel está dentro dos limites de uma imagem
     * 
     * @param width Largura da imagem
     * @param height Altura da imagem
     * @return true se o pixel está dentro dos limites, false caso contrário
     */
    public boolean isValid(int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    /**
     * Calcula a distância euclidiana entre este pixel e outro
     * Útil para análises de proximidade
     * 
     * @param other Outro pixel
     * @return Distância entre os dois pixels
     */
    public double distanceTo(Pixel other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Retorna os 4 vizinhos diretos (cima, baixo, esquerda, direita)
     * Usado no algoritmo Flood Fill
     * 
     * @return Array com os 4 pixels vizinhos
     */
    public Pixel[] getNeighbors() {
        return new Pixel[] {
            new Pixel(x + 1, y),  // Direita
            new Pixel(x - 1, y),  // Esquerda
            new Pixel(x, y + 1),  // Baixo
            new Pixel(x, y - 1)   // Cima
        };
    }
    
    /**
     * Retorna os 8 vizinhos (incluindo diagonais)
     * Útil para variações do algoritmo
     * 
     * @return Array com os 8 pixels vizinhos
     */
    public Pixel[] getAllNeighbors() {
        return new Pixel[] {
            new Pixel(x + 1, y),      // Direita
            new Pixel(x - 1, y),      // Esquerda
            new Pixel(x, y + 1),      // Baixo
            new Pixel(x, y - 1),      // Cima
            new Pixel(x + 1, y + 1),  // Diagonal inferior direita
            new Pixel(x - 1, y - 1),  // Diagonal superior esquerda
            new Pixel(x + 1, y - 1),  // Diagonal superior direita
            new Pixel(x - 1, y + 1)   // Diagonal inferior esquerda
        };
    }
    
    /**
     * Cria uma cópia deste pixel
     * 
     * @return Nova instância de Pixel com as mesmas coordenadas
     */
    public Pixel copy() {
        return new Pixel(this.x, this.y);
    }
    
    /**
     * Representação em String do pixel
     * Formato: Pixel(x, y)
     * 
     * @return String representando o pixel
     */
    @Override
    public String toString() {
        return "Pixel(" + x + ", " + y + ")";
    }
    
    /**
     * Verifica se dois pixels são iguais
     * Dois pixels são iguais se têm as mesmas coordenadas
     * 
     * @param obj Objeto a ser comparado
     * @return true se os pixels são iguais, false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Pixel pixel = (Pixel) obj;
        return x == pixel.x && y == pixel.y;
    }
    
    /**
     * Gera o hash code do pixel
     * Baseado nas coordenadas x e y
     * 
     * @return Hash code do pixel
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}