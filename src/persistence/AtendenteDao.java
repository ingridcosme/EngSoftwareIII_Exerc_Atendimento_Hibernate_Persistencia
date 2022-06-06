package persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Atendente;

public class AtendenteDao implements IObjDao<Atendente> {

	private SessionFactory sf;

	public AtendenteDao(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public void insere(Atendente at) {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(at);
		transaction.commit();
	}

	@Override
	public void modifica(Atendente at) {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(at);
		transaction.commit();

	}

	@Override
	public void remove(Atendente at) {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(at);
		transaction.commit();

	}

	@Override
	public Atendente busca(Atendente at) {
		EntityManager entityManager = sf.createEntityManager();
		at = entityManager.find(Atendente.class, at.getNome());
		return at;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atendente> lista() {
		List<Atendente> atendentes = new ArrayList<Atendente>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT id, nome, data_nascimento, salario, telefone, hora_entrada, hora_saida, email ");
		buffer.append("FROM funcionario f, atendente a ");
		buffer.append("WHERE f.id = a.id ");
		buffer.append("ORDER BY nome");
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		List<Object[]> lista = query.getResultList();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		for (Object[] obj : lista) {
			Atendente at = new Atendente();
			at.setId(Integer.parseInt(obj[0].toString()));
			at.setNome(obj[1].toString());
			LocalDate data = LocalDate.parse(obj[2].toString(), formatter);
			at.setDataNascimento(data);
			at.setSalario(Float.parseFloat(obj[3].toString()));
			at.setTelefone(obj[4].toString());
			at.setHoraEntrada(Integer.parseInt(obj[5].toString()));
			at.setHoraSaida(Integer.parseInt(obj[6].toString()));
			at.setEmail(obj[7].toString());

			atendentes.add(at);
		}

		return atendentes;
	}

}