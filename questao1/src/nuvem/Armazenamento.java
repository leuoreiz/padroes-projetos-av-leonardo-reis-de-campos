package nuvem;

// Abstract Factory - PRODUTO ABSTRATO (armazenamento de objetos)
public interface Armazenamento {

    String getId();

    int getGb();

    double getCustoPorGb();
}
