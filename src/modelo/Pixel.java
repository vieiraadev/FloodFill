package modelo;

/**
 * Representa um pixel com coordenadas (x, y) na imagem.
 * É uma classe modelo (POJO) para facilitar o armazenamento
 * de coordenadas nas estruturas de dados (Pilha e Fila).
 */
public class Pixel {
    private final int x;
    private final int y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Pixel(" + x + ", " + y + ")";
    }
}