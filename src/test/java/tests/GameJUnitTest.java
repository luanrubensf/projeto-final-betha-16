package tests;

import com.luanrubensf.projetoBetha.dao.EmprestimoDao;
import com.luanrubensf.projetoBetha.model.Emprestimo;
import com.luanrubensf.projetoBetha.model.Game;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rubens
 */
public class GameJUnitTest {

    public GameJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIdGame() {
        Game game = new Game(23L);
        assertNotNull(game.getId());
    }

    @Test
    public void testIdGameEmprestimo() {
        Game game = new Game(12L);
        Emprestimo emp = new Emprestimo();

        emp.setGame(game);

        assertNotNull("Id de game não pode ser nulo no empréstimo", emp.getGame().getId());
    }

    @Test
    public void validateEmprestimo() {
        Emprestimo emp = new Emprestimo();
        emp.setDestino("destino");
        emp.setGame(new Game(12L));

        EmprestimoDao dao = new EmprestimoDao();

        try {
            dao.validate(emp);
        } catch (Exception e) {
            fail("Erro na validação da criação do emprestimo");
        }
    }
}
