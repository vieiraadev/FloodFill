package estruturas;

/**
 * Implementação de uma Pilha (Stack) encadeada.
 * Utiliza a política LIFO (Last-In, First-Out): o último elemento a entrar é o
 * primeiro a sair.
 *
 * @param <T> Tipo genérico do elemento armazenado.
 */
public class Pilha<T> {
    private Node<T> topo;
    private int tamanho;

    private static class Node<T> {
        T elemento;
        Node<T> anterior;

        Node(T elemento) {
            this.elemento = elemento;
            this.anterior = null;
        }
    }

    public Pilha() {
        this.topo = null;
        this.tamanho = 0;
    }

    /**
     * Empilha (adiciona) um elemento no topo da pilha.
     * Complexidade: O(1).
     */
    public void push(T elemento) {
        Node<T> novoNode = new Node<>(elemento);
        novoNode.anterior = topo;
        topo = novoNode;
        tamanho++;
    }

    /**
     * Desempilha (remove) e retorna o elemento do topo.
     * Retorna null se a pilha estiver vazia.
     * Complexidade: O(1).
     */
    public T pop() {
        if (isEmpty())
            return null;
        T elemento = topo.elemento;
        topo = topo.anterior;
        tamanho--;
        return elemento;
    }

    /**
     * Retorna o elemento do topo sem removê-lo.
     */
    public T peek() {
        if (isEmpty())
            return null;
        return topo.elemento;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public int size() {
        return tamanho;
    }
}