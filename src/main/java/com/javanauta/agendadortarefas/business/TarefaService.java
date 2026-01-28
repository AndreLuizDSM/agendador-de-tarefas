package com.javanauta.agendadortarefas.business;

import com.javanauta.agendadortarefas.business.dto.TarefaDTO;
import com.javanauta.agendadortarefas.business.mapper.TarefaConverter;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefaEntity;
import com.javanauta.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.javanauta.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    public final TarefasRepository repository;
    public final TarefaConverter tarefaConverter;
    public final JwtUtil jwtUtil;

    public TarefaDTO gravarTarefa (TarefaDTO tarefaDTO, String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        tarefaDTO.setEmailUsuario(email);

        TarefaEntity tarefaEntity = tarefaConverter.paraTarefaEntity(tarefaDTO);
            return tarefaConverter.paraTarefaDTO(repository.save(tarefaEntity));
    }

    public List<TarefaDTO> buscarTarefaGravadaPorPerido(LocalDateTime dataInicial, LocalDateTime dataFinal){

        return tarefaConverter.paraListaTarefaDto(
                repository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefaDTO> buscarTarefaGravadaPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefaEntity> lista = repository.findByEmailUsuario(email);

        return tarefaConverter.paraListaTarefaDto(lista);
    }
}
