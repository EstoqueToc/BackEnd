package com.example.crud.service.usuario;

import com.example.crud.Model.PerdaEstoque;
import com.example.crud.dto.consultaDto.PerdaEstoqueConsultaDto;
import com.example.crud.dto.consultaResposta.PerdaEstoqueRespostaDto;
import com.example.crud.dto.criacaoDto.PerdaEstoqueCriacaoDto;
import com.example.crud.repository.PerdaEstoqueRepository;
import com.example.crud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerdaEstoqueService {
    private final PerdaEstoqueRepository perdaEstoqueRepository;
    private final ModelMapper modelMapper;

    public PerdaEstoqueRespostaDto criarPerdaEstoque(PerdaEstoqueCriacaoDto perdaEstoqueCriacaoDto) {
        PerdaEstoque perdaEstoque = modelMapper.map(perdaEstoqueCriacaoDto, PerdaEstoque.class);
        PerdaEstoque salvarPerdaEstoque = perdaEstoqueRepository.save(perdaEstoque);
        return modelMapper.map(salvarPerdaEstoque, PerdaEstoqueRespostaDto.class);
    }

    public List<PerdaEstoqueConsultaDto> getPerdasEstoque() {
        List<PerdaEstoque> perdasEstoque = perdaEstoqueRepository.findAll();
        return perdasEstoque.stream()
                .map(pe -> modelMapper.map(pe, PerdaEstoqueConsultaDto.class))
                .collect(Collectors.toList());
    }

    public Optional<PerdaEstoqueConsultaDto> listarPerdaEstoquePorId(Long id) {
        return perdaEstoqueRepository.findById(id)
                .map(pe -> modelMapper.map(pe, PerdaEstoqueConsultaDto.class));
    }
}