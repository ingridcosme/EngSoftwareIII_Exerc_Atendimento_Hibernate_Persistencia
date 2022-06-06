# Tarefa - Engenharia de Software III

## ORM Hibernate DDL e Persist�ncia 

Partindo do projeto https://github.com/lecolevati/ESIIIExemploDDLPersistencia
Criar uma nova interface na camada de Persist�ncia, chamada IAtendimentoDao com as assinaturas:

- public void insere (Atendimento atend);
- public Atendimento selectOne(Atendimento atend);
- public List<Atendimento> selectOneCliente(Atendimento atend);
- public List<Atendimento> selectOneAtendente(Atendimento atend);
- public List<Atendimento> selectAll();

Criar uma classe AtendimentoDao que implemente a interface acima e fa�a:

- Inser��o de um Atendimento
- Consulta de um atendimento (Query nativa com Join de 3 tabelas e Where da PK de Cliente, de Atendente e a data como par�metros)
- Consulta de atendimentos por cliente(Query nativa com Join de 3 tabelas e Where da PK de Cliente, como par�metro)
- Consulta de atendimentos por atendente(Query nativa com Join de 3 tabelas e Where da PK de Atendente, como par�metro) 
- Consulta de todos os atendimentos realizados(Query nativa do Join de 3 tabelas sem WHere)