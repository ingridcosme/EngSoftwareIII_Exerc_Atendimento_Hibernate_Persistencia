package view;

import java.util.List;

import org.hibernate.SessionFactory;

import model.Cliente;
import persistence.ClienteDao;
import util.HibernateUtil;

public class Principal {

	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		
		Cliente cli = new Cliente();
		cli.setCpf("12345678911");
		cli.setNome("Fulano");
		cli.setEmail("fulano@internet.com");
		cli.setCelular("11911115555");
		cli.setPronomeTratamento("Sr.");
		
		ClienteDao cDao = new ClienteDao(sf);
//		cDao.remove(cli);
//		Cliente cliente = cDao.busca(cli);
//		System.out.println(cliente);
		
		List<Cliente> lista = cDao.lista();
		lista.forEach(cliente -> System.out.println(cliente));
		
	}

}
