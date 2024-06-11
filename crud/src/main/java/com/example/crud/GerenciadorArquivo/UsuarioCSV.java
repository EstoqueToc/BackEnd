package com.example.crud.GerenciadorArquivo;

import com.example.crud.Helpers.ListaObj;
import com.example.crud.Model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UsuarioCSV {

    // Método para gravar o arquivo
    public static void gravaArquivoCsv(ListaObj<Usuario> lista, String nomeArq) {
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
            // Cabeçalho
            saida.format("%-5S;%-20S;%-15S;%-30S;%-15S;%-15S;%-15S\n",
                    "ID", "Nome", "CPF", "Email", "Senha", "Função", "Acesso");

            for (int i = 0; i < lista.getTamanho(); i++) {
                // Recupera um elemento da lista e formata aqui:
                Usuario usuario = lista.getElemento(i);
                saida.format("%-5d;%-20s;%-15s;%-30s;%-15s;%-15s;%-15s\n",
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getCpf(),
                        usuario.getEmail(),
                        usuario.getSenha(),
                        usuario.getFuncao(),
                        usuario.getAcesso());
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
            entrada = new Scanner(arq).useDelimiter(";|\\n");
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
        } catch (Exception erro) {
            System.out.println("Erro ao ler o arquivo");
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
        System.out.printf("%-5s %-20s %-15s %-30s %-15s %-15s %-15s\n",
                "ID", "Nome", "CPF", "Email", "Senha", "Tipo", "Status");
        for (String[] linha : matrizDados) {
            System.out.printf("%-5s %-20s %-15s %-30s %-15s %-15s %-15s\n",
                    linha[0], linha[1], linha[2], linha[3], linha[4], linha[5], linha[6]);
        }
    }
}