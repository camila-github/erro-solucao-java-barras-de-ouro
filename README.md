## Exercicio (Java): Barras de ouro

O exercicio publicado é referente ao treinamento do Bootcamp Java - Solução de Problemas com Java 
(https://digitalinnovation.one)


#### Descrição do Desafio:

O feudo da Mesopotâmia é rico e o povo é cordial e alegre. Mas quando o assunto são impostos, é praticamente um roubo. Todo final de ano, cada feudo do país deve pagar uma determinada quantidade de quilos de ouro em impostos. Quando é chegado o momento de coletar os impostos, o Rei envia sua carruagem real para recolher o ouro devido, usando as estradas do reino.

Cada estrada liga dois feudos diferentes e podem ser percorridos em duas direções. Com as estradas é possível ir de um feudo a outro, possivelmente passando por feudos intermediários. Mas há apenas um caminho entre dois feudos diferentes.

Em cada feudo há um cofre real, utilizado para armazenamento do ouro de impostos. Os cofres reais são imensos, de forma que cada cofre tem capacidade de armazenar todo o ouro devido por todo o reino. A carruagem sai do feudo principal, percorrendo as estradas do reino, visitando os feudos para recolher o ouro devido, podendo usar qualquer cofre real para armazenar temporariamente uma parte do imposto recolhido, se necessário. Ao final da coleta, todo o ouro devido por todas os feudos devem estar armazenados no cofre real do feudo principal.

José como é o Rei, contratou o seu time para, dados a quantidade de ouro a ser recolhido em cada feudo (em kg), a lista das estradas do reino, com os respectivos comprimentos (em km) e a capacidade de carga da carruagem real (em kg), determine qual é a mínima distância que a carruagem deve percorrer para recolher todo o ouro devido.

#### Entrada: 

A primeira linha contém dois inteiros N e C indicando respectivamente o número de cidades e a capacidade de carga da carruagem (2 ≤ N ≤ 104 e 1 ≤ C ≤ 100). O feudo principal é identificado pelo número 1 e os outros feudos são identificadas por inteiros de 2 a N . A segunda linha contém N inteiros Ei representando a quantidade de imposto devido por cada feudo i (0 ≤ Ei ≤ 100 para 1 ≤ i ≤ N ). Cada uma das N-1 linhas seguintes contém três inteiros A , B e C , indicando que uma estrada liga o feudo A e o feudo B (1 ≤ A, B ≤ N ) e tem comprimento C (1 ≤ C ≤ 100).

#### Saída: 

Seu programa deve produzir uma única linha com um inteiro representando a menor distância que a carruagem real deve percorrer para recolher todo o imposto devido, em km.

Exemplos de Entrada  | Exemplos de Saída
------------- | -------------
6 10 | 44
0 10 10 10 10 10 |
1 4 7 |
5 1 2 |
3 5 3 |
2 5 2 |
6 5 2 |

Exemplos de Entrada  | Exemplos de Saída
------------- | -------------
5 9 | 10
5 2 6 3 6 |
1 2 1 |
2 3 1 |
2 4 1 |
2 5 1 |


#### Java　

```java
//SOLUCAO 1

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BarrasDeOuro {
  static int capacidadeDeCarga;
  
  class Trajeto {
    Feudo destinoFeudo;
    int peso;

    Trajeto(Feudo destinoFeudo, int peso) {
      this.destinoFeudo = destinoFeudo;
      this.peso = peso;
    }
  }

  class Feudo {
    double impostoDevido;
    List<Trajeto> listaTrajetos;

    public Feudo(int impostoDevido) {
      this.impostoDevido = impostoDevido;
      this.listaTrajetos = new ArrayList<>();
    }

    public void adicionarTrajeto(Feudo destinoFeudo, int peso) {
      listaTrajetos.add(new Trajeto(destinoFeudo, peso));
    }

    public int calcularTotalDistancia(Feudo cidadeAnterior) {
      int totalDistancia = 0;
      int numeroDeViagens = 0;

      for (var trajeto : listaTrajetos) {
        if (trajeto.destinoFeudo == cidadeAnterior) continue;

        totalDistancia += trajeto.destinoFeudo.calcularTotalDistancia(this);
        
        numeroDeViagens = (int) Math.ceil(
            trajeto.destinoFeudo.impostoDevido / BarrasDeOuro.capacidadeDeCarga) * 2;
        
        totalDistancia += numeroDeViagens * trajeto.peso;
        
        this.impostoDevido += trajeto.destinoFeudo.impostoDevido;
      }
      return totalDistancia;
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    String[] linhasDeEntrada = scanner.nextLine().split(" ");
    int numeroDeCidades = Integer.parseInt(linhasDeEntrada[0]);
    BarrasDeOuro.capacidadeDeCarga = Integer.parseInt(linhasDeEntrada[1]);
    
    List<Feudo> cidades = new ArrayList<>();
    List<Integer> totalImpostoDevido = new ArrayList<>();
    
    Arrays.asList(scanner.nextLine().split(" ")).forEach(auxImpostoDevido -> {
      totalImpostoDevido.add(Integer.parseInt(auxImpostoDevido));
    });
    
    BarrasDeOuro barrasDeOuro = new BarrasDeOuro();

    for (int i = 0; i < numeroDeCidades; i++) {
      Feudo cidade = barrasDeOuro.new Feudo(totalImpostoDevido.get(i));
      cidades.add(cidade);
    }
    
    List<String> informacoesTrajeto;
    int infoA, infoB, infoC;

    for (int i = 0; i < totalImpostoDevido.size() - 1; ++i) {
      informacoesTrajeto = Arrays.asList(scanner.nextLine().split(" "));
      infoA = Integer.parseInt(informacoesTrajeto.get(0)) - 1;
      infoB = Integer.parseInt(informacoesTrajeto.get(1)) - 1;
      infoC = Integer.parseInt(informacoesTrajeto.get(2));

      cidades.get(infoA).adicionarTrajeto(cidades.get(infoB), infoC);
      cidades.get(infoB).adicionarTrajeto(cidades.get(infoA), infoC);
    }
    System.out.println(cidades.get(0).calcularTotalDistancia(null));
    scanner.close();
  }
}
```

