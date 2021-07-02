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