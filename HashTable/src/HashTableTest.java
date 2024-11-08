import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Classe abstrata que define a estrutura de uma Tabela Hash
abstract class HashTable {
    // Atributos protegidos para o tamanho da tabela, número de colisões e a tabela em si
    protected int tamanho;
    protected int numColisao = 0;
    protected ArrayList<String>[] tabela;

    // Construtor da Tabela Hash
    public HashTable(int size) {
        this.tamanho = size;
        this.tabela = new ArrayList[size];
        // Inicializa cada posição da tabela com uma lista vazia
        for (int i = 0; i < size; i++) {
            this.tabela[i] = new ArrayList<>();
        }
    }

    // Método abstrato que define a função hash (deve ser implementada nas subclasses)
    protected abstract int hashFunction(String key);

    // Método para inserir uma chave na tabela hash
    public void insert(String key) {
        int hash = hashFunction(key); // Calcula o índice usando a função hash
        if (tabela[hash].isEmpty()) { // Se a posição estiver vazia, insere diretamente
            tabela[hash].add(key);
        } else { // Caso contrário, ocorre uma colisão
            numColisao++;
            tabela[hash].add(key);
        }
    }

    // Método para pesquisar uma chave na tabela hash
    public boolean pesquisa(String key) {
        int hash = hashFunction(key); // Calcula o índice usando a função hash
        return tabela[hash].contains(key); // Verifica se a chave está presente na lista
    }

    // Retorna o número total de colisões ocorridas
    public int getnumColisao() {
        return numColisao;
    }

    // Retorna a distribuição de chaves por posição na tabela hash
    public int[] getDistribution() {
        int[] distribution = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            distribution[i] = tabela[i].size(); // Conta quantas chaves estão em cada posição
        }
        return distribution;
    }
}

// Implementação da primeira função hash (usa o hashCode padrão do Java)
class Funcao1 extends HashTable {
    public Funcao1(int size) {
        super(size);
    }

    @Override
    protected int hashFunction(String key) {
        return Math.abs(key.hashCode()) % tamanho;
    }
}

// Implementação da segunda função hash (divide o hashCode por uma constante)
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

// Classe principal para testar a tabela hash
public class HashTableTest {
    public static void main(String[] args) {
        int tableSize = 5000; // Define o tamanho da tabela hash
        HashTable Funcao1 = new Funcao1(tableSize);
        HashTable Funcao2 = new Funcao2(tableSize);

        // Caminho para o arquivo de nomes a serem lidos
        String filePath = "src\\female_names.txt";

        // Leitura do arquivo e inserção nas tabelas hash
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String name;
            while ((name = br.readLine()) != null) {
                Funcao1.insert(name);
                Funcao2.insert(name);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Relatório de comparação entre as duas funções hash
        compareHashTables(Funcao1, Funcao2);

        // Medição de desempenho para inserção e busca
        String[] keysToSearch = {"Alice", "Emma", "Olivia"}; // Exemplo de chaves para busca
        System.out.println("\n### Medição de Desempenho - Tabela 1 ###");
        performance(Funcao1, keysToSearch);
        System.out.println("\n### Medição de Desempenho - Tabela 2 ###");
        performance(Funcao2, keysToSearch);
    }

    // Função que compara o número de colisões e a distribuição de chaves nas duas tabelas hash
    public static void compareHashTables(HashTable Funcao1, HashTable Funcao2) {
        System.out.println("\n### Relatório de Comparação ###");

        // Exibe o número total de colisões para cada tabela
        System.out.println("Colisões Totais - Tabela 1: " + Funcao1.getnumColisao());
        System.out.println("Colisões Totais - Tabela 2: " + Funcao2.getnumColisao());

        // Exibe a distribuição completa para cada tabela com destaque para colisões
        atribuicao(Funcao1.getDistribution(), "Funcao1");
        atribuicao(Funcao2.getDistribution(), "Funcao2");
    }



    // Função para imprimir a distribuição de chaves em cada posição
    public static void atribuicao(int[] distribution, String tableName) {
        System.out.println("\n### Distribuição de Chaves na Tabela " + tableName + " ###");

        for (int i = 0; i < distribution.length; i++) {
            if (distribution[i] > 1) {
                // Destaca posições com colisões
                System.out.println("⚠️  Posição " + i + ": " + distribution[i] + " chaves (COLISÃO)");
            } else if (distribution[i] == 1) {
                // Exibe posições sem colisões (apenas uma chave)
                System.out.println("Posição " + i + ": " + distribution[i] + " chave");
            } else {
                // Exibe posições vazias
                System.out.println("Posição " + i + ": 0 chaves (VAZIO)");
            }
        }
    }



    // Função para medir o desempenho de inserção e busca em uma tabela hash
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
