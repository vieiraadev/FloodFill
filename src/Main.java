import algoritmo.FloodFill;
import java.io.IOException; // Necessário para tratar erros de leitura de arquivo

/**
 * Classe principal que executa o programa Flood Fill de forma não-interativa.
 * Em uma única execução, o programa irá gerar DOIS arquivos de saída:
 * 1. O resultado do preenchimento com Pilha.
 * 2. O resultado do preenchimento com Fila.
 * As configurações são definidas diretamente no código.
 */
public class Main {
    public static void main(String[] args) {

        // ==========================================================
        // CONFIGURAÇÕES DO ALGORITMO
        // Altere os valores abaixo para testar diferentes casos
        // ==========================================================

        // 1. Especifique o caminho da imagem PNG.
        String caminhoDaImagem = "src/img/maze.png";

        // 2. Defina as coordenadas (X, Y) do ponto inicial para o preenchimento.
        int pontoInicialX = 20;
        int pontoInicialY = 20;

        // 3. Escolha as cores de preenchimento para cada algoritmo.
        // Use as constantes definidas em FloodFill.java.
        int corParaPilhaRGB = FloodFill.COR_VERMELHA_RGB;
        int corParaFilaRGB = FloodFill.COR_AZUL_RGB;

        // ==========================================================

        try {
            System.out.println("============================================");
            System.out.println("         ALGORITMO FLOOD FILL");
            System.out.println("     Executando Pilha e Fila em sequência");
            System.out.println("============================================");

            // --- EXECUÇÃO COM PILHA ---
            System.out.println("\n[1 de 2] Iniciando execução com PILHA...");
            FloodFill floodFillPilha = new FloodFill(caminhoDaImagem);
            floodFillPilha.executarComPilha(pontoInicialX, pontoInicialY, corParaPilhaRGB);
            System.out.println("Execução com Pilha concluída.");

            // --- EXECUÇÃO COM FILA ---
            System.out.println("\n[2 de 2] Iniciando execução com FILA...");
            FloodFill floodFillFila = new FloodFill(caminhoDaImagem);
            floodFillFila.executarComFila(pontoInicialX, pontoInicialY, corParaFilaRGB);
            System.out.println("Execução com Fila concluída.");

            System.out.println("\n✔ Processamento finalizado!");
            System.out.println("Verifique a pasta 'output' para os arquivos:");
            System.out.println("- resultado_pilha.png (Cor: Vermelho)");
            System.out.println("- resultado_fila.png (Cor: Azul)");

        } catch (IOException e) {
            System.err.println(
                    "ERRO FATAL: Não foi possível ler o arquivo de imagem. Verifique o caminho: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
