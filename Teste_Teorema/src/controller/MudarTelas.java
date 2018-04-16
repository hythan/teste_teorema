package controller;

import java.util.ArrayList;

public class MudarTelas {

    private static ArrayList<trocaDeTela> listiner = new ArrayList<>();

    public static void trocarTela(String tela){
        trocarTela(tela,null);
    }

    public static void trocarTela(String tela, Object dadosTelas){

        switch (tela){
            case Telas.TELA_INICIAL:  Main.stage.setScene(Main.telaInicial);
            notificaTelas(Telas.TELA_INICIAL,dadosTelas);
                break;
            case Telas.TELA_CADASTRO: Main.stage.setScene(Main.telaCadastro);
                notificaTelas(Telas.TELA_CADASTRO,dadosTelas);
                break;
        }
    }

    public static interface trocaDeTela{
        void trocaDeTela(String novaTela, Object dadosTela);
    }

    public static void addNaTrocaDeTelaListiner(trocaDeTela novaTela){
        listiner.add(novaTela);
    }

    private static void notificaTelas(String novaTela, Object dadosTela){
        for (trocaDeTela l:listiner) {
            l.trocaDeTela(novaTela, dadosTela);
        }
    }


}
