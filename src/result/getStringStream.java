import java.io.ByteArrayInputStream;
import java.io.InputStream;
/*
 * 将字符串转为输入流
 * */
public class getStringStream {

    public static InputStream getStringStream2(String sInputString) {

        if (sInputString != null && !sInputString.trim().equals("")) {

            try {

                ByteArrayInputStream InputStringStream = new ByteArrayInputStream(sInputString.getBytes());

                return InputStringStream;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}