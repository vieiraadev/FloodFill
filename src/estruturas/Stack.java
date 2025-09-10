package estruturas;

/**
 * Implementação de Pilha Encadeada
 * Estrutura de dados LIFO - Last In First Out (Último a entrar, primeiro a sair)
 * @param <T> Tipo genérico dos elementos armazenados na pilha
 */
public class Stack<T> {
    private Node<T> top;  // Referência para o topo da pilha
    private int size;     // Tamanho atual da pilha
    
    /**
     * Classe interna Node - representa cada elemento da pilha
     * Segue o conceito de encadeamento
     */
    private static class Node<T> {
        T element;           // Dado armazenado no nó
        Node<T> previous;    // Referência para o nó anterior (abaixo na pilha)
        
        /**
         * Construtor do nó
         * @param element Elemento a ser armazenado
         */
        Node(T element) {
            this.element = element;
            this.previous = null;
        }
    }
    
    /**
     * Construtor da pilha
     * Inicializa uma pilha vazia
     */
    public Stack() {
        this.top = null;
        this.size = 0;
    }
    
    /**
     * Método push - Empilha um elemento no topo da pilha
     * Complexidade: O(1)
     * 
     * @param element Elemento a ser empilhado
     */
    public void push(T element) {
        // Cria um novo nó com o elemento
        Node<T> newNode = new Node<>(element);
        
        // Se a pilha não está vazia, o novo nó aponta para o antigo topo
        if (top != null) {
            newNode.previous = top;
        }
        
        // O novo nó se torna o topo
        top = newNode;
        
        // Incrementa o tamanho
        size++;
    }
    
    /**
     * Método pop - Desempilha e retorna o elemento do topo
     * Remove sempre o último elemento adicionado (LIFO)
     * Complexidade: O(1)
     * 
     * @return Elemento removido do topo, ou null se a pilha estiver vazia
     */
    public T pop() {
        // Verifica se a pilha está vazia
        if (isEmpty()) {
            return null;
        }
        
        // Guarda o elemento do topo
        T element = top.element;
        
        // Move o topo para o elemento anterior
        top = top.previous;
        
        // Decrementa o tamanho
        size--;
        
        return element;
    }
    
    /**
     * Método peek - Retorna o elemento do topo sem remover
     * Complexidade: O(1)
     * 
     * @return Elemento do topo, ou null se a pilha estiver vazia
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.element;
    }
    
    /**
     * Verifica se a pilha está vazia
     * 
     * @return true se a pilha está vazia, false caso contrário
     */
    public boolean isEmpty() {
        return top == null;
    }
    
    /**
     * Retorna o tamanho atual da pilha
     * 
     * @return Número de elementos na pilha
     */
    public int size() {
        return size;
    }
    
    /**
     * Limpa a pilha, removendo todos os elementos
     */
    public void clear() {
        top = null;
        size = 0;
    }
    
    /**
     * Retorna uma representação em String da pilha
     * 
     * @return String representando a pilha
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Stack: []";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Stack: [TOP -> ");
        
        Node<T> current = top;
        while (current != null) {
            sb.append(current.element);
            if (current.previous != null) {
                sb.append(" -> ");
            }
            current = current.previous;
        }
        
        sb.append("]");
        return sb.toString();
    }
}