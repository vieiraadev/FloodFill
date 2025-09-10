package estruturas;

/**
 * Implementação de Fila Encadeada
 * Estrutura de dados FIFO - First In First Out
 * 
 * @param <T> Tipo genérico dos elementos armazenados na fila
 */
public class Queue<T> {
    private Node<T> first;  // Referência para o início da fila (primeiro elemento)
    private Node<T> rear;   // Referência para o final da fila (último elemento)
    private int size;       // Tamanho atual da fila
    
    /**
     * Classe interna Node - representa cada elemento da fila
     */
    private static class Node<T> {
        T element;      // Dado armazenado no nó
        Node<T> next;   // Referência para o próximo nó na fila
        
        /**
         * Construtor do nó
         * @param element Elemento a ser armazenado
         */
        Node(T element) {
            this.element = element;
            this.next = null;
        }
    }
    
    /**
     * Construtor da fila
     * Inicializa uma fila vazia
     */
    public Queue() {
        this.first = null;
        this.rear = null;
        this.size = 0;
    }
    
    /**
     * Método enqueue - Enfileira um elemento no final da fila
     * Complexidade: O(1)
     * 
     * @param element Elemento a ser enfileirado
     */
    public void enqueue(T element) {
        // Cria um novo nó com o elemento
        Node<T> newNode = new Node<>(element);
        
        if (rear == null) {
            // Fila vazia - o novo nó é tanto o primeiro quanto o último
            first = newNode;
            rear = newNode;
        } else {
            // Adiciona no final da fila
            rear.next = newNode;
            rear = newNode;
        }
        
        // Incrementa o tamanho
        size++;
    }
    
    /**
     * Método dequeue - Desenfileira e retorna o primeiro elemento
     * Remove sempre o primeiro elemento da fila (FIFO)
     * Complexidade: O(1)
     * 
     * @return Elemento removido do início, ou null se a fila estiver vazia
     */
    public T dequeue() {
        // Verifica se a fila está vazia
        if (isEmpty()) {
            return null;
        }
        
        // Guarda o elemento do início
        T element = first.element;
        
        // Move o início para o próximo elemento
        first = first.next;
        
        // Se a fila ficou vazia, atualiza o rear também
        if (first == null) {
            rear = null;
        }
        
        // Decrementa o tamanho
        size--;
        
        return element;
    }
    
    /**
     * Método peek - Retorna o primeiro elemento sem remover
     * Complexidade: O(1)
     * 
     * @return Primeiro elemento da fila, ou null se estiver vazia
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return first.element;
    }
    
    /**
     * Verifica se a fila está vazia
     * 
     * @return true se a fila está vazia, false caso contrário
     */
    public boolean isEmpty() {
        return first == null;
    }
    
    /**
     * Retorna o tamanho atual da fila
     * 
     * @return Número de elementos na fila
     */
    public int size() {
        return size;
    }
    
    /**
     * Limpa a fila, removendo todos os elementos
     */
    public void clear() {
        first = null;
        rear = null;
        size = 0;
    }
    
    /**
     * Retorna o último elemento da fila sem remover
     * Útil para verificações
     * 
     * @return Último elemento, ou null se a fila estiver vazia
     */
    public T getRear() {
        if (rear == null) {
            return null;
        }
        return rear.element;
    }
    
    /**
     * Retorna uma representação em String da fila
     * 
     * @return String representando a fila
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Queue: []";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Queue: [FIRST -> ");
        
        Node<T> current = first;
        while (current != null) {
            sb.append(current.element);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        
        sb.append(" <- REAR]");
        return sb.toString();
    }
}