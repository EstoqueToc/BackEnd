package com.example.crud.GerenciadorArquivo;

import com.example.crud.Helpers.ListaObj;
import com.example.crud.Model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Scanner;

public class UsuarioCSV {

    public static void gravaArquivoCsv(ListaObj<Usuario> lista, String nomeArq) {
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
            for (int i = 0; i < lista.getTamanho(); i++) {

                //Recupere um elemento da lista e formate aqui:
                Usuario usuario = lista.getElemento(i);
                saida.format("%d;%s;%s;%s;%s;%s;%s;%s\n", usuario.getId(), usuario.getNome(), usuario.getCPF(), usuario.getEmail(), usuario.getSenha(), usuario.getFuncao(), usuario.isAcesso());
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

    public static void lerArquivoCsv(String nomeArq) {
        FileReader arq = null;
        Scanner entrada = null;
        Boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para ler o arquivo
        try {
            //cabeçalho
            System.out.printf("%-5s %-20s %-15s %-30s %-15s %-15s %-15s\n", "ID", "Nome", "CPF", "Email", "Senha", "Tipo", "Status");

            while (entrada.hasNext()) {
                int id = entrada.nextInt();
                String nome = entrada.next();
                String cpf = entrada.next();
                String email = entrada.next();
                String senha = entrada.next();
                String tipo = entrada.next();
                String status = entrada.next();

                System.out.printf("%-5d %-20s %-15s %-30s %-15s %-15s %-15s\n", id, nome, cpf, email, senha, tipo, status);
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
    }
}