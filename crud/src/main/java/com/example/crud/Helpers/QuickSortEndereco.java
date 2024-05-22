package com.example.crud.Helpers;

import com.example.crud.dto.EnderecoDto;

import java.util.Collections;
import java.util.List;

public class QuickSortEndereco {

    public void quickSort(List<EnderecoDto> enderecos) {
        if (enderecos == null || enderecos.isEmpty()) {
            return;
        }
        quickSortHelper(enderecos, 0, enderecos.size() - 1);
    }

    private void quickSortHelper(List<EnderecoDto> enderecos, int indInicio, int indFim) {
        int i = indInicio;
        int j = indFim;
        String pivo = enderecos.get((indInicio + indFim) / 2).getRua(); // Usando o nome da rua como pivo

        while (i <= j) {
            while (enderecos.get(i).getRua().compareTo(pivo) < 0) {
                i++;
            }
            while (enderecos.get(j).getRua().compareTo(pivo) > 0) {
                j--;
            }
            if (i <= j) {
                Collections.swap(enderecos, i, j);
                i++;
                j--;
            }
        }
        if (indInicio < j) {
            quickSortHelper(enderecos, indInicio, j);
        }
        if (i < indFim) {
            quickSortHelper(enderecos, i, indFim);
        }
    }
}
