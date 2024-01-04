package com.contabancaria.api.services;

import java.util.Optional;

import com.contabancaria.api.entitys.Agencia;

public interface AgenciaService {

	Optional<Agencia> buscarPorCnpj(String cnpj);
}
