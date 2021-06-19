package com.gustavo.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class PmsUploadUtil {
    private static TrackerClient trackerClient;

    private static String tracker = PmsUploadUtil.class.getResource("/fdfs_client.conf").getFile();// 获得配置文件的路径
    static {
        // 上传图片到服务器
        // 配置fdfs的全局链接地址
        try {
            ClientGlobal.init(tracker);
            trackerClient = new TrackerClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

    }



    public static String uploadImage(MultipartFile multipartFile) {
          String imgUrl = "http://172.20.10.5";

        // 获得一个trackerServer的实例
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过tracker获得一个Storage链接客户端
        StorageClient storageClient = new StorageClient(trackerServer, null);

        try {

            byte[] bytes = multipartFile.getBytes();// 获得上传的二进制对象

            // 获得文件后缀名
            String originalFilename = multipartFile.getOriginalFilename();// a.jpg
            System.out.println(originalFilename);
            int i = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(i + 1);

            String[] uploadInfos = storageClient.upload_file(bytes, extName, null);

            for (String uploadInfo : uploadInfos) {
                imgUrl += "/" + uploadInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeTrackerServer(trackerServer);
        }
        return imgUrl;
    }

    /**
     * 从FastDFS删除文件
     * @param imgUrl
     */
    public static void deleteFile(String imgUrl) {
        // 获得一个trackerServer的实例
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过tracker获得一个Storage链接客户端
        StorageClient storageClient = new StorageClient(trackerServer, null);
        String  remoteFilename = imgUrl.split("/group1/")[1];
        System.out.println(remoteFilename);
        try {
            int i = storageClient.delete_file("group1",remoteFilename);
            if (i==0){
                System.out.println("文件删除成功！");
            }else{
                System.out.println("文件删除失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            closeTrackerServer(trackerServer);
        }


    }

    private static void closeTrackerServer(TrackerServer trackerServer) {
        try {
            if (trackerServer != null) {

                trackerServer.close();
            }
        }catch (IOException e) {
                    e.printStackTrace();
                }


    }
}




