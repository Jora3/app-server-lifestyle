package wsclient.sservices;

public class InvokeClassClient {
    private String methodName;
    private Class  clientClass;

    InvokeClassClient(String className, String methodName) throws Exception {
        setClientClass(className);
        setMethodName(methodName);
    }

    private void setMethodName(String methodName) throws Exception {
        if (methodName.equals("")) throw new Exception("Impossible d'utiliser une methode vide");
        this.methodName = methodName;
    }

    private void setClientClass(String clientClass) throws Exception {
        String classLocation = String.format("wsclient.%s", clientClass);
        try {
            this.clientClass = Class.forName(classLocation);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Impossible de trouver la class principale : " + classLocation);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String invoke(Object... args) throws Exception {
        try {
            Class[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++)
                classes[i] = String.class;

            return String.valueOf(this.clientClass.newInstance().getClass()
                    .getMethod(methodName, classes)
                    .invoke(clientClass.newInstance(), args));

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public String invoke() throws Exception {
        try {
            return String.valueOf(this.clientClass.newInstance().getClass()
                    .getMethod(methodName)
                    .invoke(clientClass.newInstance()));

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
