package algoritmo;

import estruturas.Fila;
import estruturas.Pilha;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import modelo.Pixel;

public class FloodFill {
    private final BufferedImage imagemOriginal;
    private final int largura;
    private final int altura;
    private int contadorFrames;

    // Cores definidas como inteiros no formato ARGB para não usar a classe
    // 'java.awt.Color'.
    public static final int COR_PRETA_RGB = 0xFF000000;
    public static final int COR_VERMELHA_RGB = 0xFFFF0000;
    public static final int COR_VERDE_RGB = 0xFF00FF00;
    public static final int COR_AZUL_RGB = 0xFF0000FF;

    private static final int INTERVALO_FRAMES = 25;

    public FloodFill(String caminhoImagem) throws IOException {
        File arquivoImagem = new File(caminhoImagem);
        this.imagemOriginal = ImageIO.read(arquivoImagem);
        if (this.imagemOriginal == null) {
            throw new IOException("Imagem não encontrada ou formato inválido.");
        }
        this.largura = imagemOriginal.getWidth();
        this.altura = imagemOriginal.getHeight();
        new File("output").mkdirs();
        new File("animation").mkdirs();
    }

    public void executarComPilha(int xInicial, int yInicial, int novaCorRGB) {
        System.out.println("Executando com PILHA...");
        BufferedImage imagemTrabalho = copiarImagem(imagemOriginal);
        int corAlvo = imagemTrabalho.getRGB(xInicial, yInicial);

        if (corAlvo == novaCorRGB || corAlvo == COR_PRETA_RGB)
            return;

        Pilha<Pixel> pilha = new Pilha<>();
        pilha.push(new Pixel(xInicial, yInicial));
        boolean[][] visitados = new boolean[altura][largura];
        this.contadorFrames = 0;
        int pixelsProcessados = 0;

        while (!pilha.isEmpty()) {
            Pixel atual = pilha.pop();
            int x = atual.getX();
            int y = atual.getY();

            if (!isCoordenadaValida(x, y) || visitados[y][x] || imagemTrabalho.getRGB(x, y) != corAlvo) {
                continue;
            }

            visitados[y][x] = true;
            imagemTrabalho.setRGB(x, y, novaCorRGB);
            pixelsProcessados++;

            if (pixelsProcessados % INTERVALO_FRAMES == 0) {
                salvarFrameAnimacao(imagemTrabalho, "pilha");
            }

            pilha.push(new Pixel(x, y - 1));
            pilha.push(new Pixel(x, y + 1));
            pilha.push(new Pixel(x - 1, y));
            pilha.push(new Pixel(x + 1, y));
        }
        salvarFrameAnimacao(imagemTrabalho, "pilha");
        salvarImagem(imagemTrabalho, "output/resultado_pilha.png");
    }

    public void executarComFila(int xInicial, int yInicial, int novaCorRGB) {
        System.out.println("Executando com FILA...");
        BufferedImage imagemTrabalho = copiarImagem(imagemOriginal);
        int corAlvo = imagemTrabalho.getRGB(xInicial, yInicial);

        if (corAlvo == novaCorRGB || corAlvo == COR_PRETA_RGB)
            return;

        Fila<Pixel> fila = new Fila<>();
        fila.enqueue(new Pixel(xInicial, yInicial));
        boolean[][] visitados = new boolean[altura][largura];
        visitados[yInicial][xInicial] = true;
        this.contadorFrames = 0;
        int pixelsProcessados = 0;

        while (!fila.isEmpty()) {
            Pixel atual = fila.dequeue();
            int x = atual.getX();
            int y = atual.getY();

            if (imagemTrabalho.getRGB(x, y) == corAlvo) {
                imagemTrabalho.setRGB(x, y, novaCorRGB);
                pixelsProcessados++;

                if (pixelsProcessados % INTERVALO_FRAMES == 0) {
                    salvarFrameAnimacao(imagemTrabalho, "fila");
                }

                int[][] vizinhos = { { x, y - 1 }, { x, y + 1 }, { x - 1, y }, { x + 1, y } };
                for (int[] vizinho : vizinhos) {
                    if (isCoordenadaValida(vizinho[0], vizinho[1]) && !visitados[vizinho[1]][vizinho[0]]) {
                        fila.enqueue(new Pixel(vizinho[0], vizinho[1]));
                        visitados[vizinho[1]][vizinho[0]] = true;
                    }
                }
            }
        }
        salvarFrameAnimacao(imagemTrabalho, "fila");
        salvarImagem(imagemTrabalho, "output/resultado_fila.png");
    }

    private boolean isCoordenadaValida(int x, int y) {
        return x >= 0 && x < largura && y >= 0 && y < altura;
    }

    private BufferedImage copiarImagem(BufferedImage source) {
        // Forçamos o tipo para TYPE_INT_RGB para garantir que a imagem não tenha
        // um canal de transparência (Alpha). Isso resulta em um fundo branco sólido.
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
            System.out.println("Imagem final salva em: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao salvar a imagem: " + e.getMessage());
        }
    }

    private void salvarFrameAnimacao(BufferedImage img, String tipo) {
        try {
            contadorFrames++;
            String nomeArquivo = String.format("animation/%s_frame_%04d.png", tipo, contadorFrames);
            ImageIO.write(copiarImagem(img), "png", new File(nomeArquivo));
        } catch (IOException e) {
            // Erro silencioso para não poluir o console com falhas de salvamento de frames
        }
    }
}