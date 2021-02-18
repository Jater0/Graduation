package cn.jater.graduation.forum.utils.oss;

import cn.jater.graduation.forum.utils.constant.OSSConstant;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.net.URL;
import java.util.Random;

@Component
public class PhotoHandler {
    public String downloadPicture(String urlLink, String fileDir) {
        URL url = null;
        try {
            url = new URL(urlLink);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String photoName = getRandomString(10) + ".png";
            String fileName = fileDir + photoName;
            OSS ossClient = new OSSClientBuilder().build(OSSConstant.inside, OSSConstant.accessKeyId, OSSConstant.accessKeySecret);
            PutObjectRequest putObjectRequest = new PutObjectRequest(OSSConstant.buckName, fileName, dataInputStream);
            ossClient.putObject(putObjectRequest);
            dataInputStream.close();
            ossClient.shutdown();
            return "https://" + OSSConstant.selfOSS + fileDir + photoName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getRandomString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}