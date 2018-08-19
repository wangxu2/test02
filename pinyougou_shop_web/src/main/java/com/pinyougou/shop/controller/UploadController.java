package com.pinyougou.shop.controller;

import com.pinyougou.util.FastDFSClient;
import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Value("http://192.168.25.133/")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){
        //1.创建一个FastDFS的客户端
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fastdfs_client.conf");
        //2.字节数组
            byte[] bytes = file.getBytes();
            //3.文件的扩展名
            String originalFilename = file.getOriginalFilename();
            //4.获取文件的格式（不带点）
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //5.执行上传处理
            String path = fastDFSClient.uploadFile(bytes, extName);
            //6.真实路径
            String realPath = IMAGE_SERVER_URL + path;
            return new Result(true,realPath);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"文件上传失败");
        }
    }
}
