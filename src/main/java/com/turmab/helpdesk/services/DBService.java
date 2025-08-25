package com.turmab.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turmab.helpdesk.domain.Chamado;
import com.turmab.helpdesk.domain.Cliente;
import com.turmab.helpdesk.domain.Tecnico;
import com.turmab.helpdesk.domain.enums.Perfil;
import com.turmab.helpdesk.domain.enums.Prioridade;
import com.turmab.helpdesk.domain.enums.Status;
import com.turmab.helpdesk.repositories.ChamadoRepository;
import com.turmab.helpdesk.repositories.ClienteRepository;
import com.turmab.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    public void instanciaDB() {
        // Instanciando Técnico [cite: 661]
        Tecnico tec1 = new Tecnico(null, "Bill Gates", "70045777093", "bill@mail.com", "123");
        tec1.addPerfil(Perfil.ADMIN);

        // Instanciando Cliente [cite: 662]
        Cliente cli1 = new Cliente(null, "Linus Torvalds", "70511744013", "linus@mail.com", "123");

        // Instanciando Chamado [cite: 663]
        Chamado cha1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);

        // Salvando no banco de dados [cite: 664, 665, 666]
        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(cha1));
    }
}