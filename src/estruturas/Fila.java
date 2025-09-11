package estruturas;

/**
 * Implementação de uma Fila (Queue) encadeada.
 * Utiliza a política FIFO (First-In, First-Out): o primeiro elemento a entrar é
 * o primeiro a sair.
 *
 * @param <T> Tipo genérico do elemento armazenado.
 */
public class Fila<T> {
    private Node<T> primeiro;
    private Node<T> ultimo;
    private int tamanho;

    private static class Node<T> {
        T elemento;
        Node<T> proximo;

        Node(T elemento) {
            this.elemento = elemento;
            this.proximo = null;
        }
    }

    public Fila() {
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

    /**
     * Enfileira (adiciona) um elemento no final da fila.
     * Complexidade: O(1).
     */
    public void enqueue(T elemento) {
        Node<T> novoNode = new Node<>(elemento);
        if (isEmpty()) {
            primeiro = novoNode;
        } else {
            ultimo.proximo = novoNode;
        }
        ultimo = novoNode;
        tamanho++;
    }

    /**
     * Desenfileira (remove) e retorna o elemento do início da fila.
     * Retorna null se a fila estiver vazia.
     * Complexidade: O(1).
     */
    public T dequeue() {
        if (isEmpty())
            return null;
        T elemento = primeiro.elemento;
        primeiro = primeiro.proximo;
        if (primeiro == null) {
            ultimo = null; // A fila ficou vazia
        }
        tamanho--;
        return elemento;
    }

    /**
     * Retorna o primeiro elemento da fila sem removê-lo.
     */
    public T peek() {
        if (isEmpty())
            return null;
        return primeiro.elemento;
    }

    public boolean isEmpty() {
        return primeiro == null;
    }

    public int size() {
        return tamanho;
    }
}