import java.io.*;

/**
 * ɾ���ļ��еĸ���ע�ͣ�����//��/* * /��
 * ͬʱɾ����������
 */
public class DeleteComments {

    public static String clearComment(File file, String charset) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            StringBuffer content = new StringBuffer();
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                content.append(tmp);
                content.append("\n");
            }
            reader.close();
            String target = content.toString();
            String s = target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
            s = s.replaceAll("\\w{1,}\\s{1,}\\w{1,}\\s*\\([\\s\\w\\*,]*\\);", "");
            return s;
        }catch (IOException e) {
            MyError.ShowErrMsg(2, "Ŀ���ļ�������!");
        }
        return null;
    }

    public static String clearComment(String filePath, String charset) {
        return  clearComment(new File(filePath), charset);
    }

    public static String clearComment(String filePath) {
        return clearComment(new File(filePath), "UTF-8");
    }

    public static String clearComment(File file) {
        return  clearComment(file, "UTF-8");
    }

    public static void main(String[] args) {
    }

}