package wsclient.sservices;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

public class ServicesClient extends HttpServlet {
    private String json = "";

    private Object[] arguments(String[] tableau) {
        if (tableau.length == 0 || tableau.length == 1) return null;
        else {
            Object[] objects = new Object[tableau.length - 1];
            reglerEspace(tableau);
            System.arraycopy(tableau, 1, objects, 0, tableau.length - 1);
            return objects;
        }
    }

    private void reglerEspace(String[] tab) {
        int t = tab.length;
        for(int i = 0; i < t; i++)
            tab[i] = tab[i].replace("%20", " ");
    }


    private void setResponse(HttpServletResponse response) throws IOException {
        PrintWriter out = null;
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
            response.setContentType("text/html");
            out = response.getWriter();
            out.println(json);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } finally {
            if (out != null) out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws IOException {
        try {
            String uri = request.getRequestURI();
            String[] tabUri = uri.split("/");
            String service = tabUri[tabUri.length-1];
            String fonction = request.getQueryString();
            Map<String, String[]> params = request.getParameterMap();
            if(params.isEmpty())
                json = new InvokeClassClient(service, fonction).invoke();
            else {
                json = new InvokeClassClient(service, fonction).invoke(arguments(params));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            json = new Gson().toJson(new DataResponse(true, "Impossible de traiter votre requettes"));
        }
        setResponse(response);
    }

    private Object[] arguments(Map<String, String[]> params) throws Exception {
        Object[] args = new Object[params.size()-1];
        int j = 0, i = 0;
        for (String nomParam : params.keySet()) {
            String[] values = params.get(nomParam);
            if (values.length != 1)
                throw new Exception("Erreur de données : les données passées sont perdues ou n'existent pas");
            if(j > 0) {
                args[i] = values[0];
                i++;
            }
            j++;
        }
        return  args;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uriString   = request.getRequestURI();
        String queryString = request.getQueryString();

        try {
            if (queryString == null || uriString == null) {
                throw new Exception("Impossible de contacter le serveur. Cause : URI invalide");
            }
            String[] tableau0 = queryString.split("/");
            String[] tableau1 = uriString.replace("/", " ").split(" ");

            if (tableau0.length == 0) {
                throw new Exception("Impossible d'effectuer l'operation methode vide");
            } else if (tableau0.length == 1) {
                json = new InvokeClassClient(tableau1[tableau1.length - 1], tableau0[0]).invoke();
            } else {
                json = new InvokeClassClient(tableau1[tableau1.length - 1], tableau0[0]).invoke(arguments(tableau0));
            }
        }catch (Exception e) {
            e.printStackTrace();
            json = new Gson().toJson(new DataResponse(true, "Impossible de traiter votre requettes"));
        }
        setResponse(response);
    }
}
