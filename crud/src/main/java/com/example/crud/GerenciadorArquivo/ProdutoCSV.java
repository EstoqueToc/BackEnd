package com.example.crud.GerenciadorArquivo;

import com.example.crud.Helpers.ListaObj;
import com.example.crud.Model.Produto;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ProdutoCSV {
    //metodo para gravar o arquivo
    public static void gravaArquivoCsv(ListaObj<Produto> lista, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;

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

    //metodo para ler o arquivo
    public static void lerArquivoCsv(String nomeArq) {
        FileReader arq = null;
        Scanner entrada = null;
        Boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para ler o arquivo
        try {
            //cabeçalho
            System.out.println("Nome;Preço de Venda;Preço de Compra;Data de Entrada;Unidade de Medida;Descrição;Categoria;Fornecedor;Quantidade em Estoque;Data de Validade");

            while (entrada.hasNext()) {
                String linha = entrada.nextLine();
                String[] campos = linha.split(";");
//                System.out.println("ID: " + campos[0]);
                System.out.println("Nome: " + campos[1]);
                System.out.println("Preço de Venda: " + campos[2]);
                System.out.println("Preço de Compra: " + campos[3]);
                System.out.println("Data de Entrada: " + campos[4]);
                System.out.println("Unidade de Medida: " + campos[5]);
                System.out.println("Descrição: " + campos[6]);
                System.out.println("Categoria: " + campos[7]);
                System.out.println("Fornecedor: " + campos[8]);
                System.out.println("Quantidade em Estoque: " + campos[9]);
                System.out.println("Data de Validade: " + campos[10]);
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
    }
}
