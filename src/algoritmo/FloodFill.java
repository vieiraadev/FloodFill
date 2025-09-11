package algoritmo;

import estruturas.Fila;
import estruturas.Pilha;
import modelo.Pixel;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa o algoritmo Flood Fill utilizando as estruturas de Pilha e Fila.
 * O objetivo é preencher uma área de cor sólida em uma imagem até encontrar
 * bordas de outra cor (neste caso, preto).
 */
public class FloodFill {
    private final BufferedImage imagemOriginal;
    private final int largura;
    private final int altura;
    private final List<BufferedImage> framesAnimacao;
    private int contadorFrames;

    // Constantes para performance e legibilidade
    private static final int COR_BORDA_PRETA = Color.BLACK.getRGB();
    private static final int INTERVALO_FRAMES = 25; // Salva um frame a cada 25 pixels pintados

    public FloodFill(String caminhoImagem) throws IOException {
        this.imagemOriginal = ImageIO.read(new File(caminhoImagem));
        if (this.imagemOriginal == null) {
            throw new IOException("Não foi possível carregar a imagem: " + caminhoImagem);
        }

        this.largura = imagemOriginal.getWidth();
        this.altura = imagemOriginal.getHeight();
        this.framesAnimacao = new ArrayList<>();

        // Garante que as pastas de saída existam
        new File("output").mkdirs();
        new File("animation").mkdirs();
    }

    /**
     * Ponto de entrada para executar o algoritmo com uma Pilha (Stack).
     * O uso da Pilha resulta em um preenchimento em profundidade (Depth-First
     * Search - DFS).
     */
    public void executarComPilha(int xInicial, int yInicial, Color novaCor) {
        System.out.println("\nIniciando Flood Fill com PILHA (LIFO)...");
        BufferedImage imagemTrabalho = copiarImagem(imagemOriginal);
        int corAlvo = imagemTrabalho.getRGB(xInicial, yInicial);
        int corPreenchimento = novaCor.getRGB();

        // Condições iniciais para evitar processamento desnecessário
        if (corAlvo == corPreenchimento || corAlvo == COR_BORDA_PRETA) {
            System.out.println("O pixel inicial já é da cor de preenchimento ou é uma borda. Nenhuma ação necessária.");
            salvarImagem(imagemTrabalho, "output/resultado_pilha.png");
            return;
        }

        Pilha<Pixel> pilha = new Pilha<>();
        pilha.push(new Pixel(xInicial, yInicial));
        boolean[][] visitados = new boolean[altura][largura];
        int pixelsPintados = 0;

        while (!pilha.isEmpty()) {
            Pixel atual = pilha.pop();
            int x = atual.getX();
            int y = atual.getY();

            // Pula o pixel se ele não for válido ou já foi visitado
            if (!isCoordenadaValida(x, y) || visitados[y][x]) {
                continue;
            }

            // Marca o pixel como visitado
            visitados[y][x] = true;

            // Verifica se o pixel pode ser preenchido (é da cor alvo e não é borda)
            if (podePreencherPixel(imagemTrabalho.getRGB(x, y), corAlvo)) {
                imagemTrabalho.setRGB(x, y, corPreenchimento);
                pixelsPintados++;

                // Salva um frame da animação em intervalos definidos
                if (pixelsPintados % INTERVALO_FRAMES == 0) {
                    salvarFrameAnimacao(imagemTrabalho, "pilha");
                }

                // Empilha os 4 vizinhos para processamento futuro
                pilha.push(new Pixel(x, y - 1)); // Cima
                pilha.push(new Pixel(x, y + 1)); // Baixo
                pilha.push(new Pixel(x - 1, y)); // Esquerda
                pilha.push(new Pixel(x + 1, y)); // Direita
            }
        }
        salvarFrameAnimacao(imagemTrabalho, "pilha"); // Salva o último frame
        salvarImagem(imagemTrabalho, "output/resultado_pilha.png");
        System.out.println("Preenchimento com Pilha concluído. " + pixelsPintados + " pixels pintados.");
    }

    /**
     * Ponto de entrada para executar o algoritmo com uma Fila (Queue).
     * O uso da Fila resulta em um preenchimento em largura (Breadth-First Search -
     * BFS).
     */
    public void executarComFila(int xInicial, int yInicial, Color novaCor) {
        System.out.println("\nIniciando Flood Fill com FILA (FIFO)...");
        BufferedImage imagemTrabalho = copiarImagem(imagemOriginal);
        int corAlvo = imagemTrabalho.getRGB(xInicial, yInicial);
        int corPreenchimento = novaCor.getRGB();

        if (corAlvo == corPreenchimento || corAlvo == COR_BORDA_PRETA) {
            System.out.println("O pixel inicial já é da cor de preenchimento ou é uma borda. Nenhuma ação necessária.");
            salvarImagem(imagemTrabalho, "output/resultado_fila.png");
            return;
        }

        Fila<Pixel> fila = new Fila<>();
        fila.enqueue(new Pixel(xInicial, yInicial));
        boolean[][] visitados = new boolean[altura][largura];
        visitados[yInicial][xInicial] = true; // Marca o inicial como visitado
        int pixelsPintados = 0;

        while (!fila.isEmpty()) {
            Pixel atual = fila.dequeue();
            int x = atual.getX();
            int y = atual.getY();

            if (podePreencherPixel(imagemTrabalho.getRGB(x, y), corAlvo)) {
                imagemTrabalho.setRGB(x, y, corPreenchimento);
                pixelsPintados++;

                if (pixelsPintados % INTERVALO_FRAMES == 0) {
                    salvarFrameAnimacao(imagemTrabalho, "fila");
                }

                // Enfileira os 4 vizinhos para processamento se forem válidos e não visitados
                int[][] vizinhos = { { x, y - 1 }, { x, y + 1 }, { x - 1, y }, { x + 1, y } };
                for (int[] vizinho : vizinhos) {
                    int vizinhoX = vizinho[0];
                    int vizinhoY = vizinho[1];
                    if (isCoordenadaValida(vizinhoX, vizinhoY) && !visitados[vizinhoY][vizinhoX]) {
                        fila.enqueue(new Pixel(vizinhoX, vizinhoY));
                        visitados[vizinhoY][vizinhoX] = true;
                    }
                }
            }
        }
        salvarFrameAnimacao(imagemTrabalho, "fila");
        salvarImagem(imagemTrabalho, "output/resultado_fila.png");
        System.out.println("Preenchimento com Fila concluído. " + pixelsPintados + " pixels pintados.");
    }

    // --- MÉTODOS AUXILIARES ---

    /**
     * A REGRA CENTRAL: um pixel só pode ser preenchido se ele tiver a mesma cor
     * do pixel inicial (corAlvo) E não for uma borda preta.
     * Esta é a lógica que impede o preenchimento de "vazar" pelas linhas.
     */
    private boolean podePreencherPixel(int corAtual, int corAlvo) {
        return corAtual == corAlvo && corAtual != COR_BORDA_PRETA;
    }

    public boolean isCoordenadaValida(int x, int y) {
        return x >= 0 && x < largura && y >= 0 && y < altura;
    }

    public boolean isBorda(int x, int y) {
        if (!isCoordenadaValida(x, y))
            return true; // Fora da imagem é considerado borda
        return imagemOriginal.getRGB(x, y) == COR_BORDA_PRETA;
    }

    private BufferedImage copiarImagem(BufferedImage source) {
        BufferedImage copia = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                copia.setRGB(x, y, source.getRGB(x, y));
            }
        }
        return copia;
    }

    private void salvarImagem(BufferedImage img, String caminho) {
        try {
            ImageIO.write(img, "png", new File(caminho));
            System.out.println("✔ Imagem final salva em: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a imagem final: " + e.getMessage());
        }
    }

    private void salvarFrameAnimacao(BufferedImage img, String tipo) {
        try {
            contadorFrames++;
            String nomeArquivo = String.format("animation/%s_frame_%04d.png", tipo, contadorFrames);
            BufferedImage frame = copiarImagem(img);
            framesAnimacao.add(frame);
            ImageIO.write(frame, "png", new File(nomeArquivo));
        } catch (IOException e) {
            System.err.println("Aviso: Falha ao salvar frame de animação: " + e.getMessage());
        }
    }

    // --- GETTERS ---
    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public List<BufferedImage> getFramesAnimacao() {
        return framesAnimacao;
    }
}