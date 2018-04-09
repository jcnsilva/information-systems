import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import play.libs.F.Callback;
import play.test.TestBrowser;

public class IntegrationTest {

    @Test
    public void testaLoginPage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333/login");
                assertThat(browser.pageSource()).contains("Portal do Leite");
                assertThat(browser.pageSource()).contains("Entrar");
            }
        });
    }
    
    @Test
    public void testaRegisterPage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333/register");
                assertThat(browser.pageSource()).contains("Portal do Leite");
                assertThat(browser.pageSource()).contains("Registrar");
            }
        });
    }
    
    @Test
    public void testaHomePage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
            	loginHelper(browser);
            	
            	browser.goTo("http://localhost:3333/home");
                assertThat(browser.pageSource()).contains("Portal do Leite");
                assertThat(browser.pageSource()).contains("Análise e Design");
                assertThat(browser.pageSource()).contains("Javascript");
                assertThat(browser.pageSource()).contains("Projeto");
            }
        });
    }
    
    @Test
    public void testaDetailsPage() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
            	loginHelper(browser);
            	
                browser.goTo("http://localhost:3333/details/1");
                assertThat(browser.pageSource()).contains("Portal do Leite");
                assertThat(browser.pageSource()).contains("Análise e Design");
                assertThat(browser.pageSource()).contains("Adicionar dica");
                assertThat(browser.pageSource()).contains("Dicas");
            }
        });
    }
    
    public void loginHelper(TestBrowser browser){
    	browser.goTo("http://localhost:3333/register");
    	browser.fill("#nome").with("Teste");
    	browser.fill("#username").with("teste");
    	browser.fill("#senha").with("teste");
    	browser.submit("#registrar");
    	
    	browser.goTo("http://localhost:3333/login");
    	browser.fill("#username").with("teste");
    	browser.fill("#senha").with("teste");
    	browser.submit("#entrar");
    }
    	
  

}
