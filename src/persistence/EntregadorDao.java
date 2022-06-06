package persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Entregador;

public class EntregadorDao implements IObjDao<Entregador> {

	private SessionFactory sf;

	public EntregadorDao(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public void insere(Entregador t) {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(t);
		transaction.commit();

	}

	@Override
	public void modifica(Entregador t) {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(t);
		transaction.commit();

	}

	@Override
	public void remove(Entregador t) {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(t);
		transaction.commit();

	}

	@Override
	public Entregador busca(Entregador t) {
		EntityManager entityManager = sf.createEntityManager();
		t = entityManager.find(Entregador.class, t.getNome());
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entregador> lista() {
		List<Entregador> entregadores = new ArrayList<Entregador>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT id, nome, data_nascimento, salario, telefone, num_cnh, categoria_cnh ");
		buffer.append("FROM funcionario f, entregador e ");
		buffer.append("WHERE f.id = e.id ");
		buffer.append("ORDER BY nome");
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		List<Object[]> lista = query.getResultList();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		for (Object[] obj : lista) {
			Entregador ent = new Entregador();
			ent.setId(Integer.parseInt(obj[0].toString()));
			ent.setNome(obj[1].toString());
			LocalDate data = LocalDate.parse(obj[2].toString(), formatter);
			ent.setDataNascimento(data);
			ent.setSalario(Float.parseFloat(obj[3].toString()));
			ent.setTelefone(obj[4].toString());
			ent.setNumCnh(obj[5].toString());
			ent.setCategoriaCnh(obj[6].toString());

			entregadores.add(ent);
		}

		return entregadores;
	}

}
