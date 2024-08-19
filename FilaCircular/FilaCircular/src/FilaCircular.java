public class FilaCircular<T> {
    private int topo = -1;
    private int base = 0;
    private T[] dados;
    private int tamanho;

    @SuppressWarnings("unchecked")
    public FilaCircular(int tamanho) {
        this.tamanho = tamanho;
        dados = (T[]) new Object[tamanho];
    }

    public void adicionar(T elemento) {
        if (estahCheia()) {
            throw new IllegalStateException("Fila está cheia");
        }
        topo = mover(topo + 1);
        dados[topo] = elemento;
    }

    public T remover() {
        if (estahVazia()) {
            throw new IllegalStateException("Fila está vazia");
        }
        T elemento = dados[base];
        dados[base] = null;
        base = mover(base + 1);
        return elemento;
    }

    public void limpar() {
        while (!estahVazia()) {
            remover();
        }
    }

    public boolean estahCheia() {
        return mover(topo + 1) == base;
    }

    public boolean estahVazia() {
        return topo + 1 == base || (topo < base && mover(topo + 1) == base);
    }

    private int mover(int posicao) {
        return (posicao + tamanho) % tamanho;
    }
}
