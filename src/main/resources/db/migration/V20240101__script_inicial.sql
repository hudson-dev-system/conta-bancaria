CREATE TABLE agencia(
    id INT NOT NULL IDENTITY,
    cnpj VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    data_cadastro DATETIME NOT NULL,
    data_atualizacao DATETIME NOT NULL,
    PRIMARY KEY(id)
);
CREATE TABLE correntista(
    id INT NOT NULL IDENTITY,
    agencia_id INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    cnpj VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    saldo DECIMAL(19,2) NOT NULL,
    data_cadastro DATETIME NOT NULL,
    data_atualizacao DATETIME NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(agencia_id) REFERENCES agencia(id)
);
CREATE TABLE transacao(
    id INT NOT NULL IDENTITY,
    correntista_id INT NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    valor DECIMAL(19,2) NOT NULL,
    data_cadastro DATETIME NOT NULL,
    data_atualizacao DATETIME NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(correntista_id) REFERENCES correntista(id)
);