package com.example.crud.dto.mapper;

import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.ProdutoConsultaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import lombok.experimental.UtilityClass;

@UtilityClass // indicar que essa classe é uma classe de utilitário e deve ter um construtor privado
public class ProdutoMapper {

    public static Produto toEntity(ProdutoCriacaoDto produtoCriacaoDto) {
        Produto produto = new Produto();
        produto.setNome(produtoCriacaoDto.getNome());
        produto.setPrecoDeVenda(produtoCriacaoDto.getPrecoDeVenda());
        produto.setPrecoDeCompra(produtoCriacaoDto.getPrecoDeCompra());
        produto.setDataDeEntrada(produtoCriacaoDto.getDataDeEntrada());
        produto.setUnidadeDeMedida(produtoCriacaoDto.getUnidadeDeMedida());
        produto.setDescricao(produtoCriacaoDto.getDescricao());
        produto.setCategoria(produtoCriacaoDto.getCategoria());
        produto.setFornecedor(produtoCriacaoDto.getFornecedor());
        produto.setQtdEstoque(produtoCriacaoDto.getQtdEstoque());
        produto.setDataDeValidade(produtoCriacaoDto.getDataDeValidade());
        return produto;
    }

    public static ProdutoConsultaDto toConsultaDto(Produto produto) {
        return new ProdutoConsultaDto(produto);
    }
}
