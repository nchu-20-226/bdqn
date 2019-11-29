package 爬虫;

import java.io.*;
import java.net.URL;

public class Demo1 {
    public static void main(String[] args) throws IOException {
        URL url=new URL("http://www.baidu.com/");
        /*InputStream inputStream = url.openStream();
        FileOutputStream fos=new FileOutputStream(new File("baidu.txt"));
        int len;
        byte[] bytes=new byte[1024];
        while((len=inputStream.read(bytes))!=-1){
            fos.write(bytes,0,len);
        }
        inputStream.close();
        fos.close();*/
        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("baidu.html")));
        String flag;
        while((flag=br.readLine())!=null){
            bw.append(flag);
            bw.newLine();
        }
        bw.close();
        br.close();
    }
}
