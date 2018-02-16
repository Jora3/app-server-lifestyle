package services.mapping;

public abstract class JsonResultats {

    protected abstract Object object();

    private Object object(boolean isVoid, String method, Object... args) throws Exception {
        try {
            Class[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++){
                classes[i] = args[i].getClass();
            }
            if (isVoid) {
                object().getClass().getMethod(method, classes).invoke(object(), args);
                return "OK";
            }
            return object().getClass().getMethod(method, classes).invoke(object(), args);
        } catch (Exception e) {
            throw e;
        }
    }

    protected String jsonFormat(boolean isVoid, String method, Object... args) {
        try {
            return new Resultats(object(isVoid, method, args), false).toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return new Resultats(e.getCause().getMessage(), true).toJson();
        }
    }
}
