
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.rmi.*;


@Consumes("application/json")
@Produces("application/json")
public class ServerRest {
 
	@POST
	@Path("/ListarConsulta")
	public String listarConsultaREST(String data) {
	    System.out.println("Debug: Endpoint '/ListarConsulta' reached.");

	    try {
	        int idUser = Integer.parseInt(data.trim());
	        System.out.println("Debug: Parsed idUser: " + idUser);

	        System.out.println("Debug: a chamar rmiServer().listarConsulta(): " + idUser);
	        String result = rmiServer().listarConsulta(idUser);

	        // Log the result and return it
	        System.out.println("Debug: Result from listarConsulta: " + result);
	        return result;
	        
	    } catch (NumberFormatException e) {
	        System.out.println("Debug: Invalid user ID format: " + data);
	        return "Erro: Formato inválido para o ID do usuário.";
	    } catch (RemoteException e) {
	        System.out.println("Debug: RemoteException occurred: " + e.getMessage());
	        e.printStackTrace();
	        return "Erro ao chamar a função listarConsulta.";
	    } catch (Exception e) {
	        System.out.println("Debug: General exception occurred: " + e.getMessage());
	        e.printStackTrace();
	        return "Erro inesperado.";
	    }
	}
	
	@POST
	@Path("/Autenticar")	
	public String autenticar(String data) {
	    System.out.println("Debug: Endpoint '/Autenticar' reached.");
	    System.out.println("Debug: Received data: " + data);

	    String[] info = data.split(";");
	    if (info.length < 2) {
	        System.out.println("Debug: Malformed input. Expected format: 'username;password'.");
	        return "Erro: Input malformado. Esperado 'username;password'.";
	    }

	    String username = info[0].trim();
	    String pw = info[1].trim();

	    System.out.println("Debug: Extracted username: " + username);
	    System.out.println("Debug: Extracted password: " + pw);

	    try {
	        System.out.println("Debug: Calling rmiServer().autenticar() with username: " + username);
	        int id = rmiServer().autenticar(username, pw);

	        System.out.println("Debug: Authentication id for username '" + username + "': " + id);

	        return String.valueOf(id);
	        
	    } catch (Exception e) {
	        System.out.println("Debug: Exception occurred during RMI call: " + e.getMessage());
	        e.printStackTrace();
	        return "Erro ao chamar a função RMI.";
	    }
	}

	@POST
	@Path("/RegistarUtilizador")	
	public String registarUtilizador(String data) {
	    System.out.println("Debug: Endpoint '/RegistarUtilizador' reached.");

	    // Logging the raw input data
	    System.out.println("Debug: Received data: " + data);

	    String[] info = data.split(";");
	    if (info.length < 2) {
	        System.out.println("Debug: Malformed input. Expected format: 'username;password'.");
	        return "Erro: Input malformado. Esperado 'username;password'.";
	    }

	    String username = info[0].trim();
	    String pw = info[1].trim();

	    // Logging extracted information
	    System.out.println("Debug: Extracted username: " + username);
	    System.out.println("Debug: Extracted password: " + pw);

	    try {
	        // Attempt to call the RMI method
	        System.out.println("Debug: Calling rmiServer().registarUtilizador() with username: " + username);
	        int result = rmiServer().registarUtilizador(username, pw);

	        // Log the result of the RMI method call
	        System.out.println("Debug: UserID from rmiServer().registarUtilizador(): " + result);

	        // Return appropriate response
	        return String.valueOf(result);
	    } catch (Exception e) {
	        // Log the exception details
	        System.out.println("Debug: Exception occurred during RMI call: " + e.getMessage());
	        e.printStackTrace(); // Print full stack trace for detailed debugging
	        return "Erro ao chamar a função RMI.";
	    }
	}
	
	@POST
	@Path("/add")	
    public String add(String data) {
		System.out.println("endpoint reached");
		System.out.println(data);
		  Integer number1=0, number2=0, number3=0;
	      String[] info = data.split(",");
	      number1=Integer.valueOf(info[0]);
	      number2=Integer.valueOf(info[1]);
	      //number3=number1+number2;
	      //String result = ""+number3;
	      int result = 0;
	      
	      try {
		      result = rmiServer().teste(number1);
	          System.out.println("Resultado do método teste: " + result);
		      return "" + result;

	      }
	      catch(Exception e){
	            System.out.println("Exception: " + e);
            	return "erro a chamar a funcao rmi";
	      }

    	
    }
	
	@POST
	@Path("/ListarEspecialidades")
	public String listarEspecialidadesREST() {
	    System.out.println("Debug: Endpoint '/ListarEspecialidades' reached.");

	    try {
	        System.out.println("Debug: Calling rmiServer().listarEspecialidades()");

	        // Chama a função RMI para listar as especialidades
	        String result = rmiServer().listarEspecialidades();

	        // Log the result and return it as a JSON response
	        System.out.println("Debug: Result from listarEspecialidades: " + result);
	        return result;

	    } catch (RemoteException e) {
	        System.out.println("Debug: RemoteException occurred: " + e.getMessage());
	        e.printStackTrace();
	        return "{\"error\":\"Erro ao chamar a função listarEspecialidades no servidor RMI.\"}";
	    } catch (Exception e) {
	        System.out.println("Debug: General exception occurred: " + e.getMessage());
	        e.printStackTrace();
	        return "{\"error\":\"Erro inesperado.\"}";
	    }
	}

	
	private IServerActions rmiServer() {
		try {
            String serverUrl = "rmi://192.168.56.101/rmiServer";
            IServerActions serverActionsInterface = (IServerActions) Naming.lookup(serverUrl);
            
            return serverActionsInterface;
            
        } catch (Exception e) {
            System.out.println("Exception funcao rmiServer(): " + e);
            e.printStackTrace();
            return null;
        }
      
	}
}