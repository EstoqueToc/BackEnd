package com.example.crud.GerenciadorArquivo;

import com.example.crud.Helpers.ListaObj;
import com.example.crud.Model.Produto;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ProdutoCSV {

    // Método para gravar o arquivo
    public static void gravaArquivoCsv(ListaObj<Produto> lista, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para gravar o arquivo
        try {
            saida.format("%-5S;%-20S;%-20S;%-20S;%-20S;%-20S;%-20S;%-20S;%-20S;%-20S;%-20S\n",
                    "ID", "Nome", "Preço de Venda", "Preço de Compra", "Data de Entrada", "Unidade de Medida", "Descrição", "Categoria", "Fornecedor", "Quantidade em Estoque", "Data de Validade");
            for (int i = 0; i < lista.getTamanho(); i++) {
                Produto produto = lista.getElemento(i);
                saida.format("%05d;%-20s;%20.2f;%20.2f;%-20s;%-20s;%-20s;%-20s;%-20s;%20d;%-20s\n",
                        produto.getId(),
                        produto.getNome(),
                        produto.getPrecoDeVenda(),
                        produto.getPrecoDeCompra(),
                        produto.getDataDeEntrada(),
                        produto.getUnidadeDeMedida(),
                        produto.getDescricao(),
                        produto.getCategoria().getNome(),
                        produto.getFornecedor().getNome(),
                        produto.getQtdEstoque(),
                        produto.getDataDeValidade());
            }
        } catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }

    // Método para ler o arquivo
    public static void lerArquivoCsv(String nomeArq) {
        FileReader arq = null;
        Scanner entrada = null;
        boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Lista para armazenar as linhas do arquivo
        List<String[]> dados = new ArrayList<>();

        // Bloco try-catch para ler o arquivo
        try {
            // Cabeçalho
            if (entrada.hasNext()) {
                entrada.nextLine(); // Ignora a primeira linha (cabeçalho)
            }

            // Leitura dos dados
            while (entrada.hasNext()) {
                String linha = entrada.nextLine();
                String[] campos = linha.split(";");
                dados.add(campos);
            }
        } catch (NoSuchElementException erro) {
            System.out.println("Arquivo com problemas");
            deuRuim = true;
        } catch (IllegalStateException erro) {
            System.out.println("Erro na leitura do arquivo");
            deuRuim = true;
        } finally {
            entrada.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }

        // Conversão da lista para uma matriz
        String[][] matrizDados = new String[dados.size()][];
        for (int i = 0; i < dados.size(); i++) {
            matrizDados[i] = dados.get(i);
        }

        // Exibição dos dados em formato tabular
        System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "ID", "Nome", "Preço de Venda", "Preço de Compra", "Data de Entrada", "Unidade de Medida", "Descrição", "Categoria", "Fornecedor", "Qtd. Estoque", "Data de Validade");
        for (String[] linha : matrizDados) {
            System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                    linha[0], linha[1], linha[2], linha[3], linha[4], linha[5], linha[6], linha[7], linha[8], linha[9], linha[10]);
        }
    }
}