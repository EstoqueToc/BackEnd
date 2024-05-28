package com.example.crud.service.usuario;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaResposta.ProdutoRespostaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import com.example.crud.repository.AlertaRepository;
import com.example.crud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final AlertaRepository alertaRepository;
    private final ModelMapper mapper;


    public ProdutoRespostaDto criarProduto(ProdutoCriacaoDto novoProdutoDto) {
        Produto novoProduto = mapper.map(novoProdutoDto, Produto.class);

        // Configurar e salvar os alertas
        if (novoProduto.getAlertaEstoque() != null) {
            for (Alerta alerta : novoProduto.getAlertaEstoque()) {
                alerta.setProduto(novoProduto);
            }
        }

        // Salvar o produto (e os alertas devido ao CascadeType.ALL)
        Produto produtoSalvo = produtoRepository.save(novoProduto);

        // Mapear a entidade salva para o DTO de resposta
        return mapper.map(produtoSalvo, ProdutoRespostaDto.class);
    }

}
