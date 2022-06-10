package persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Atendente;
import model.Atendimento;
import model.Cliente;

public class AtendimentoDao implements IAtendimentoDao {

	private SessionFactory sf;

	public AtendimentoDao(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	public void insere(Atendimento atend) {
		// Inserção de um Atendimento
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(atend);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Atendimento selectOne(Atendimento atend) {
		// Consulta de um atendimento (Query nativa com Join de 3 tabelas e Where da PK de Cliente, de Atendente e a data como parâmetros)
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT c.cpf_cliente, c.nome_cliente, c.email_cliente, c.celular_cliente, c.pronome_tratamento, ");
		buffer.append("f.id, f.nome, f.data_nascimento, f.salario, f.telefone ");
		buffer.append("a.horario_entrada, a.horario_saida, a.email, ");
		buffer.append("at.data_hora_atendimento ");
		buffer.append("FROM atendente a, cliente c, atendimento at, funcionario f ");
		buffer.append("WHERE c.cpf  = at.cpf_cliente ");
		buffer.append("AND a.id = f.id ");
		buffer.append("AND a.id = at.id_atendente ");
		buffer.append("AND a.id = ?1 ");
		buffer.append("AND c.cpf = ?2 ");
		buffer.append("AND at.data_hora_atendimento = ?3 ");
		
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter(1, atend.getAtendente().getId());
		query.setParameter(2, atend.getCliente().getCpf());
		query.setParameter(3, atend.getDataHora());
		
		List<Object[]> atendimentosResultSet = query.getResultList();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		for (Object[] obj : atendimentosResultSet) {
			Cliente cliente = new Cliente();
			cliente.setCpf(obj[0].toString());
			cliente.setNome(obj[1].toString());
			cliente.setEmail(obj[2].toString());
			cliente.setCelular(obj[3].toString());
			cliente.setPronomeTratamento(obj[4].toString());
			
			Atendente atendente = new Atendente();
			atendente.setId(Integer.parseInt(obj[5].toString()));
			atendente.setNome(obj[6].toString());
			atendente.setDataNascimento(LocalDate.parse(obj[7].toString()));
			atendente.setSalario(Float.parseFloat(obj[8].toString()));
			atendente.setTelefone(obj[9].toString());
			atendente.setHoraEntrada(Integer.parseInt(obj[10].toString()));
			atendente.setHoraSaida(Integer.parseInt(obj[11].toString()));
			atendente.setEmail(obj[12].toString());
			
			
			atend = new Atendimento();
			atend.setCliente(cliente);
			atend.setAtendente(atendente);
			atend.setDataHora(LocalDateTime.parse(obj[13].toString(), formatter));
		}
		
		return atend;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atendimento> selectOneCliente(Atendimento atend) {
		// Consulta de atendimentos por cliente(Query nativa com Join de 3 tabelas e Where da PK de Cliente, como parâmetro)
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT c.cpf_cliente, c.nome_cliente, c.email_cliente, c.celular_cliente, c.pronome_tratamento, ");
		buffer.append("f.id, f.nome, f.data_nascimento, f.salario, f.telefone ");
		buffer.append("a.horario_entrada, a.horario_saida, a.email, ");
		buffer.append("at.data_hora_atendimento ");
		buffer.append("FROM atendente a, cliente c, atendimento at, funcionario f ");
		buffer.append("WHERE c.cpf  = at.cpf_cliente ");
		buffer.append("AND a.id = f.id ");
		buffer.append("AND a.id = at.id_atendente ");
		buffer.append("AND c.cpf = ?1 ");
		
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter(1, atend.getCliente().getCpf());
		
		List<Object[]> atendimentosResultSet = query.getResultList();
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		for (Object[] obj : atendimentosResultSet) {
			Cliente cliente = new Cliente();
			cliente.setCpf(obj[0].toString());
			cliente.setNome(obj[1].toString());
			cliente.setEmail(obj[2].toString());
			cliente.setCelular(obj[3].toString());
			cliente.setPronomeTratamento(obj[4].toString());
			
			Atendente atendente = new Atendente();
			atendente.setId(Integer.parseInt(obj[5].toString()));
			atendente.setNome(obj[6].toString());
			atendente.setDataNascimento(LocalDate.parse(obj[7].toString()));
			atendente.setSalario(Float.parseFloat(obj[8].toString()));
			atendente.setTelefone(obj[9].toString());
			atendente.setHoraEntrada(Integer.parseInt(obj[10].toString()));
			atendente.setHoraSaida(Integer.parseInt(obj[11].toString()));
			atendente.setEmail(obj[12].toString());
			
			
			atend = new Atendimento();
			atend.setCliente(cliente);
			atend.setAtendente(atendente);
			atend.setDataHora(LocalDateTime.parse(obj[13].toString(), formatter));
			
			atendimentos.add(atend);
		}
		
		return atendimentos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atendimento> selectOneAtendente(Atendimento atend) {
		// Consulta de atendimentos por atendente(Query nativa com Join de 3 tabelas e Where da PK de Atendente, como parâmetro)
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT c.cpf_cliente, c.nome_cliente, c.email_cliente, c.celular_cliente, c.pronome_tratamento, ");
		buffer.append("f.id, f.nome, f.data_nascimento, f.salario, f.telefone ");
		buffer.append("a.horario_entrada, a.horario_saida, a.email, ");
		buffer.append("at.data_hora_atendimento ");
		buffer.append("FROM atendente a, cliente c, atendimento at, funcionario f ");
		buffer.append("WHERE c.cpf  = at.cpf_cliente ");
		buffer.append("AND a.id = f.id ");
		buffer.append("AND a.id = at.id_atendente ");
		buffer.append("AND a.id = ?1 ");
		
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		query.setParameter(1, atend.getAtendente().getId());
		
		List<Object[]> atendimentosResultSet = query.getResultList();
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		for (Object[] obj : atendimentosResultSet) {
			Cliente cliente = new Cliente();
			cliente.setCpf(obj[0].toString());
			cliente.setNome(obj[1].toString());
			cliente.setEmail(obj[2].toString());
			cliente.setCelular(obj[3].toString());
			cliente.setPronomeTratamento(obj[4].toString());
			
			Atendente atendente = new Atendente();
			atendente.setId(Integer.parseInt(obj[5].toString()));
			atendente.setNome(obj[6].toString());
			atendente.setDataNascimento(LocalDate.parse(obj[7].toString()));
			atendente.setSalario(Float.parseFloat(obj[8].toString()));
			atendente.setTelefone(obj[9].toString());
			atendente.setHoraEntrada(Integer.parseInt(obj[10].toString()));
			atendente.setHoraSaida(Integer.parseInt(obj[11].toString()));
			atendente.setEmail(obj[12].toString());
			
			
			atend = new Atendimento();
			atend.setCliente(cliente);
			atend.setAtendente(atendente);
			atend.setDataHora(LocalDateTime.parse(obj[13].toString(), formatter));
			
			atendimentos.add(atend);
		}
		
		return atendimentos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atendimento> selectAll() {
		// Consulta de todos os atendimentos realizados(Query nativa do Join de 3 tabelas sem WHere)
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT c.cpf_cliente, c.nome_cliente, c.email_cliente, c.celular_cliente, c.pronome_tratamento, ");
		buffer.append("f.id, f.nome, f.data_nascimento, f.salario, f.telefone ");
		buffer.append("a.horario_entrada, a.horario_saida, a.email, ");
		buffer.append("at.data_hora_atendimento ");
		buffer.append("FROM atendente a, cliente c, atendimento at, funcionario f ");
		buffer.append("WHERE c.cpf  = at.cpf_cliente ");
		buffer.append("AND a.id = f.id ");
		buffer.append("AND a.id = at.id_atendente ");
		
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		
		List<Object[]> atendimentosResultSet = query.getResultList();
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		for (Object[] obj : atendimentosResultSet) {
			Cliente cliente = new Cliente();
			cliente.setCpf(obj[0].toString());
			cliente.setNome(obj[1].toString());
			cliente.setEmail(obj[2].toString());
			cliente.setCelular(obj[3].toString());
			cliente.setPronomeTratamento(obj[4].toString());
			
			Atendente atendente = new Atendente();
			atendente.setId(Integer.parseInt(obj[5].toString()));
			atendente.setNome(obj[6].toString());
			atendente.setDataNascimento(LocalDate.parse(obj[7].toString()));
			atendente.setSalario(Float.parseFloat(obj[8].toString()));
			atendente.setTelefone(obj[9].toString());
			atendente.setHoraEntrada(Integer.parseInt(obj[10].toString()));
			atendente.setHoraSaida(Integer.parseInt(obj[11].toString()));
			atendente.setEmail(obj[12].toString());
			
			
			Atendimento atend = new Atendimento();
			atend.setCliente(cliente);
			atend.setAtendente(atendente);
			atend.setDataHora(LocalDateTime.parse(obj[13].toString(), formatter));
			
			atendimentos.add(atend);
		}
		
		return atendimentos;
	}

	


}
