import org.junit.*;
import play.mvc.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class AuthenticationTest extends AbstractTest{
	
	/*@Test
	public void testeAutenticacao() {
	    Result result = callAction(
	        controllers.routes.ref.Application.index(),
	        fakeRequest().withSession("username", "teste")
	    );
	    
	    assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
	    assertThat(redirectLocation(result)).isEqualTo("/home");
	    
	    
	    Result result2 = callAction(
		        controllers.routes.ref.Application.index(),
		        fakeRequest());
		    
		    assertThat(status(result2)).isEqualTo(Http.Status.SEE_OTHER);
		    assertThat(redirectLocation(result2)).isEqualTo("/login");
	}*/

}
