import com.fastcgi.FCGIInterface;
import validation.Validate;
import check.Checker;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Objects;

class Server {
    public static void main (String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();
        Validate v = new Validate();
        Checker checker = new Checker();

        while(fcgiInterface.FCGIaccept() >= 0) {
            String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
            if (method.equals("GET")) {
                long time = System.nanoTime();
                String req = FCGIInterface.request.params.getProperty("QUERY_STRING");
                if (!Objects.equals(req, "")) {
                    LinkedHashMap<String, String> m = getValues(req);
                    boolean isShot;
                    boolean isValid;
                    try {
                        isValid = v.check(Integer.parseInt(m.get("x")), Float.parseFloat(m.get("y")), Integer.parseInt(m.get("r")));
                        isShot = checker.hit(Integer.parseInt(m.get("x")), Float.parseFloat(m.get("y")), Integer.parseInt(m.get("r")));
                    } catch (Exception e){
                        System.out.println(err("Invalid data"));
                        continue;
                    }
                    if (isValid) {
                        System.out.println(resp(isShot, m.get("x"), m.get("y"), m.get("r"), time));
                    }
                    else
                        System.out.println(err(v.getErr()));
                }
                else
                   System.out.println("fill");
            }
            else
                System.out.println(err("method"));
        }
    }
    private static LinkedHashMap<String, String> getValues(String inpString){
        String[] args = inpString.split("&");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (String s : args) {
            String[] arg = s.split("=");
            map.put(arg[0], arg[1]);
        }
        return map;
    }
    private static String resp(boolean isShoot, String x, String y, String r, long wt) {
        String content = """
                {"result":"%s","x":"%s","y":"%s","r":"%s","time":"%s","workTime":"%s","error":"all ok"}
                """.formatted(isShoot, x, y, r, (double)(System.nanoTime() - wt) / 10000000, LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return """
                Content-Type: application/json; charset=utf-8
                Content-Length: %d
                
                
                %s
                """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);
    }

    private static String err(String msg) {
        String content = """
                {"error":"%s"}
                """.formatted(msg);
        return """
                Content-Type: application/json charset=utf-8
                Content-Length: %d
                
                
                %s
                """.formatted(content.getBytes(StandardCharsets.UTF_8).length, content);
    }
}
