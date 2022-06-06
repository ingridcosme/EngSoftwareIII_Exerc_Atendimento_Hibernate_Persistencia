# Tarefa - Engenharia de Software III

## ORM Hibernate DDL e Persistência 

Partindo do projeto https://github.com/lecolevati/ESIIIExemploDDLPersistencia
Criar uma nova interface na camada de Persistência, chamada IAtendimentoDao com as assinaturas:

- public void insere (Atendimento atend);
- public Atendimento selectOne(Atendimento atend);
- public List<Atendimento> selectOneCliente(Atendimento atend);
- public List<Atendimento> selectOneAtendente(Atendimento atend);
- public List<Atendimento> selectAll();

Criar uma classe AtendimentoDao que implemente a interface acima e faça:

- Inserção de um Atendimento
- Consulta de um atendimento (Query nativa com Join de 3 tabelas e Where da PK de Cliente, de Atendente e a data como parâmetros)
- Consulta de atendimentos por cliente(Query nativa com Join de 3 tabelas e Where da PK de Cliente, como parâmetro)
- Consulta de atendimentos por atendente(Query nativa com Join de 3 tabelas e Where da PK de Atendente, como parâmetro) 
- Consulta de todos os atendimentos realizados(Query nativa do Join de 3 tabelas sem WHere)