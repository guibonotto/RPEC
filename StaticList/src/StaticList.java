public class StaticList {
    private int[] data;
    private int size;
    private int capacity;

    // Construtor da lista estática
    public StaticList(int capacity) {
        this.capacity = capacity;
        this.data = new int[capacity];
        this.size = 0;
    }

    // Limpa a lista
    public void clear() {
        this.size = 0;
    }

    // Adiciona um elemento ao final da lista
    public void add(int data) {
        if (!isFull()) {
            this.data[size] = data;
            size++;
        } else {
            System.out.println("A lista está cheia.");
        }
    }

    // Adiciona um elemento em uma posição específica
    public void add(int data, int pos) {
        if (!isFull() && pos >= 0 && pos <= size) {
            for (int i = size; i > pos; i--) {
                this.data[i] = this.data[i - 1];
            }
            this.data[pos] = data;
            size++;
        } else {
            System.out.println("A lista está cheia ou a posição é inválida.");
        }
    }

    // Remove o elemento em uma posição específica
    public void remove(int pos) {
        if (!isEmpty() && pos >= 0 && pos < size) {
            for (int i = pos; i < size - 1; i++) {
                this.data[i] = this.data[i + 1];
            }
            size--;
        } else {
            System.out.println("A lista está vazia ou a posição é inválida.");
        }
    }

    // Verifica se a lista está vazia
    public boolean isEmpty() {
        return size == 0;
    }

    // Verifica se a lista está cheia
    public boolean isFull() {
        return size == capacity;
    }

    // Define o valor em uma posição específica
    public void setData(int data, int pos) {
        if (pos >= 0 && pos < size) {
            this.data[pos] = data;
        } else {
            System.out.println("Posição inválida.");
        }
    }

    // Obtém o valor de uma posição específica
    public int getData(int pos) {
        if (pos >= 0 && pos < size) {
            return this.data[pos];
        } else {
            System.out.println("Posição inválida.");
            return -1; // Retorna um valor inválido caso a posição seja incorreta
        }
    }

    // Retorna o tamanho atual da lista
    public int getSize() {
        return size;
    }

    // Encontra a posição de um dado específico
    public int find(int data) {
        for (int i = 0; i < size; i++) {
            if (this.data[i] == data) {
                return i;
            }
        }
        return -1; // Retorna -1 se o dado não for encontrado
    }

    // Método para exibir a lista
    public void display() {
        for (int i = 0; i < size; i++) {
            System.out.print(this.data[i] + " ");
        }
        System.out.println();
    }

    // Método principal para testar a classe StaticList
    public static void main(String[] args) {
        StaticList lista = new StaticList(5);

        lista.add(10);
        lista.add(20);
        lista.add(30);
        lista.display();

        lista.add(15, 1); // Adiciona 15 na posição 1
        lista.display();

        lista.remove(2); // Remove o elemento na posição 2
        lista.display();

        System.out.println("Elemento na posição 1: " + lista.getData(1));
        System.out.println("Posição do elemento 20: " + lista.find(20));

        lista.setData(25, 1); // Define o valor 25 na posição 1
        lista.display();

        System.out.println("A lista está vazia? " + lista.isEmpty());
        System.out.println("A lista está cheia? " + lista.isFull());

        lista.clear(); // Limpa a lista
        lista.display();
    }
}
