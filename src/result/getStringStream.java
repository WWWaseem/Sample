import java.io.ByteArrayInputStream;
import java.io.InputStream;
/*
 * ���ַ���תΪ������
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