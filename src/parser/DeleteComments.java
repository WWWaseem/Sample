import java.io.*;

/**
 * 删除文件中的各种注释，包含//、/* * /等
 * 同时删除函数声明
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
            MyError.ShowErrMsg(2, "目标文件不存在!");
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