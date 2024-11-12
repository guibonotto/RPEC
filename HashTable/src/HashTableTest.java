import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

abstract class HashTable {
   
    protected int tamanho;
    protected int numColisao = 0;
    protected ArrayList<String>[] tabela;

    public HashTable(int size) {
        this.tamanho = size;
        this.tabela = new ArrayList[size];
      
        for (int i = 0; i < size; i++) {
            this.tabela[i] = new ArrayList<>();
        }
    }

    protected abstract int hashFunction(String key);

    public void insert(String key) {
        int hash = hashFunction(key); 
        if (tabela[hash].isEmpty()) { 
            tabela[hash].add(key);
        } else {
            numColisao++;
            tabela[hash].add(key);
        }
    }

    public boolean pesquisa(String key) {
        int hash = hashFunction(key); 
        return tabela[hash].contains(key); 
    }

    // Retorna o número total de colisões ocorridas
    public int getnumColisao() {
        return numColisao;
    }

    
    public int[] getDistribution() {
        int[] distribution = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            distribution[i] = tabela[i].size(); 
        }
        return distribution;
    }
}


class Funcao1 extends HashTable {
    public Funcao1(int size) {
        super(size);
    }

    @Override
    protected int hashFunction(String key) {
        return Math.abs(key.hashCode()) % tamanho;
    }
}


class Funcao2 extends HashTable {
    private final int primeConstant = 31;

    public Funcao2(int size) {
        super(size);
    }

    @Override
    protected int hashFunction(String key) {
        return Math.abs((key.hashCode() / primeConstant)) % tamanho;
    }
}


public class HashTableTest {
    public static void main(String[] args) {
        int tableSize = 5000; 
        HashTable Funcao1 = new Funcao1(tableSize);
        HashTable Funcao2 = new Funcao2(tableSize);

       
        String filePath = "src\\female_names.txt";

       
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String name;
            while ((name = br.readLine()) != null) {
                Funcao1.insert(name);
                Funcao2.insert(name);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

      
        compareHashTables(Funcao1, Funcao2);

        
        String[] keysToSearch = {"Alice", "Emma", "Olivia"}; 
        System.out.println("\n### Medição de Desempenho - Tabela 1 ###");
        performance(Funcao1, keysToSearch);
        System.out.println("\n### Medição de Desempenho - Tabela 2 ###");
        performance(Funcao2, keysToSearch);
    }

   
    public static void compareHashTables(HashTable Funcao1, HashTable Funcao2) {
        System.out.println("\n### Relatório de Comparação ###");

       
        System.out.println("Colisões Totais - Tabela 1: " + Funcao1.getnumColisao());
        System.out.println("Colisões Totais - Tabela 2: " + Funcao2.getnumColisao());

        atribuicao(Funcao1.getDistribution(), "Funcao1");
        atribuicao(Funcao2.getDistribution(), "Funcao2");
    }



    public static void atribuicao(int[] distribution, String tableName) {
        System.out.println("\n### Distribuição de Chaves na Tabela " + tableName + " ###");

        for (int i = 0; i < distribution.length; i++) {
            if (distribution[i] > 1) {
                System.out.println("⚠️  Posição " + i + ": " + distribution[i] + " chaves (COLISÃO)");
            } else if (distribution[i] == 1) {
                System.out.println("Posição " + i + ": " + distribution[i] + " chave");
            } else {
                System.out.println("Posição " + i + ": 0 chaves (VAZIO)");
            }
        }
    }

    public static void performance(HashTable hashTable, String[] keysToSearch) {
        long startTime = System.nanoTime();
        for (String key : keysToSearch) {
            hashTable.insert(key);
        }
        long insertionTime = System.nanoTime() - startTime;
        System.out.println("Tempo de inserção: " + insertionTime + " nanosegundos");

        startTime = System.nanoTime();
        for (String key : keysToSearch) {
            hashTable.pesquisa(key);
        }
        long searchTime = System.nanoTime() - startTime;
        System.out.println("Tempo de busca: " + searchTime + " nanosegundos");
    }
}
