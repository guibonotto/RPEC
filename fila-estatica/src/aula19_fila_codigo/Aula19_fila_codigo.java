package aula19_fila_codigo;

public class Aula19_fila_codigo {

    public static void main(String[] args) {
        Fila fila = new Fila();
        
        fila.adicionar("Jão");
        fila.adicionar("Zé");
        fila.adicionar("Juca");
        fila.adicionar("Maria");
        fila.adicionar("Ano");
        
        System.out.println("Primeiro da fila:" + fila.get());
        fila.remover();
        System.out.println("Novo Primeiro da fila:" + fila.get());
        fila.remover();
        System.out.println("Novo Primeiro da fila:" + fila.get());
        
    }
    
}
