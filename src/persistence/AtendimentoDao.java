package persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Atendente;
import model.Atendimento;

public class AtendimentoDao implements IObjDao, IAtendimentoDao {

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

	@Override
	public Atendimento selectOne(Atendimento atend) {
		// Consulta de um atendimento (Query nativa com Join de 3 tabelas e Where da PK de Cliente, de Atendente e a data como parâmetros)
		Atendimento atendimento = new Atendimento();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT a.nome, c.nome_cliente, at.data_hora_atendimento ");
		buffer.append("FROM atendente a, cliente c, atendimento at, funcionario f ");
		buffer.append("WHERE c.cpf_cliente = at.cpf_cliente AND a.id = f.id AND a.id = at.id_atendente AND  ");
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		List<Object[]> res = query.getResultList();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		for (Object[] obj : res) {
			atendimento.setAtendente(obj[0]);
			atendimento.setCliente(obj[1]);
			LocalDate data = LocalDate.parse(obj[2].toString(), formatter);
			atendimento.setDataHora(data);
		}
		
		return atendimento;
	}

	@Override
	public List<Atendimento> selectOneCliente(Atendimento atend) {
		// Consulta de atendimentos por cliente(Query nativa com Join de 3 tabelas e Where da PK de Cliente, como parâmetro)
		return null;
	}

	@Override
	public List<Atendimento> selectOneAtendente(Atendimento atend) {
		// Consulta de atendimentos por atendente(Query nativa com Join de 3 tabelas e Where da PK de Atendente, como parâmetro)
		return null;
	}

	@Override
	public List<Atendimento> selectAll() {
		// Consulta de todos os atendimentos realizados(Query nativa do Join de 3 tabelas sem WHere)
		return null;
	}

	@Override
	public void insere(Object t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifica(Object t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Object t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object busca(Object t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List lista() {
		// TODO Auto-generated method stub
		return null;
	}



}
